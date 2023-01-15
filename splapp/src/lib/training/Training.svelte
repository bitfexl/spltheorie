<script>
    import questionIndex from "../questionIndex.json";
    import { getQuestions } from "../QuestionSelector.svelte";
    import LocalStorage from "../LocalStorage.svelte";
    import Percent from "../Percent.svelte";
    import QuizQuestion from "../QuizQuestion.svelte";
    import TrainingQuestion from "./TrainingQuestion.svelte";

    const allCategories = questionIndex.categories.map((c) => c.id);

    let allQuestions = getQuestions(allCategories);

    let questions = {};

    $: {
        allQuestions;

        if (allQuestions) {
            for (let question of allQuestions) {
                questions[question.id] = question;
            }
        }
    }

    // trays ///////////////////////////////////////////////////////////////////

    let activeTray = -1; // no tray selected

    let trays = [allQuestions.map((q) => q.id), [], [], [], []];

    function randomQuestion(i) {
        let tray = trays[i];
        return tray[Math.floor(Math.random() * tray.length)];
    }

    function promote(questionId, i) {
        if (i < trays.length - 1) {
            changeTray(questionId, i, 1);
        }
    }

    function demote(questionId, i) {
        if (i > 0) {
            changeTray(questionId, i, -1);
        }
    }

    function changeTray(questionId, i, dir) {
        trays[i].splice(
            trays[i].findIndex((q) => q == questionId),
            1
        );
        trays[i + dir].push(questionId);
        trays = [...trays]; // update (svelte)
    }

    function setTray(tray) {
        activeTray = tray;
        if (tray != -1) {
            questionId = randomQuestion(activeTray);
        }
    }

    // quiz question ///////////////////////////////////////////////////////////

    let questionId; // active question

    function nextQuestion(e) {
        if (e.detail.correct) {
            promote(e.detail.question.id, activeTray);
        } else {
            demote(e.detail.question.id, activeTray);
        }

        questionId = randomQuestion(activeTray);
    }
</script>

<LocalStorage key="trays" bind:val={trays} />

<div class="content">
    {#if activeTray != -1}
        <div class="spacing">
            <h2>Fach {activeTray + 1}</h2>
            <p>
                {trays[activeTray].length} Frage{trays[activeTray].length != 1 ? "n" : ""} (<Percent
                    total={allQuestions.length}
                    actual={trays[activeTray].length}
                />)
            </p>
            <button class="linkLike" on:click={() => setTray(-1)}>Zurück zur Lernfächerauswahl</button>
        </div>
        {#if questionId}
            <div class="questionWrapper">
                <!-- undefined if no question in tray -->
                <div class="question">
                    <TrainingQuestion question={questions[questionId]} on:next={nextQuestion} />
                </div>
            </div>
        {/if}
    {:else}
        <div class="spacing">
            <h1>Lernfächer</h1>
            <div class="spacing" />
            <h3>Erklärung</h3>
            <p>
                Ziel ist es, dass sich alle Fragen in Fach 5 befinden. Wird eine Frage richtig beantwortet wandert sie ein Fach nach oben.
                Wird eine Frage falsch beantwortet wandert sie ein Fach nach unten. Zu Beginn befinden sich alle Fragen in Fach 1.
            </p>
        </div>
        <div class="trays">
            {#each trays as tray, ti}
                <div class="tray">
                    <div>
                        <h3>Fach {ti + 1}</h3>
                        <p>
                            {tray.length} Frage{tray.length != 1 ? "n" : ""} (<Percent total={allQuestions.length} actual={tray.length} />)
                        </p>
                    </div>
                    <div class="start">
                        <button class="linkLike" on:click={() => setTray(ti)}>Fragen üben</button>
                    </div>
                </div>
            {/each}
        </div>
    {/if}
</div>

<style>
    .content {
        text-align: center;
    }

    .trays {
        display: flex;
        flex-direction: column-reverse;
        gap: 20px;
        width: 100%;
    }

    .tray {
        display: grid;
        grid-template-columns: 1fr 1fr;
        padding: 10px;
        background-color: whitesmoke;
        box-shadow: 0 0 2px gray;
    }

    .questionWrapper {
        min-height: 300px;
        text-align: center;
        background-color: #fcfcfc;
        box-shadow: 0 0 2px #e0e0e0;
        padding: 10px;
        border-radius: 2px;
    }

    .question {
        display: inline-block;
    }

    .start {
        display: flex;
    }

    .spacing {
        margin-bottom: 40px;
    }
</style>
