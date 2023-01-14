<script>
    import questionIndex from "../questionIndex.json";
    import { getQuestions } from "../QuestionSelector.svelte";
    import LocalStorage from "../LocalStorage.svelte";
    import Percent from "../Percent.svelte";
    import QuizQuestion from "../QuizQuestion.svelte";

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
    let showCorrect = false;
    let selectionCorrect;

    function nextQuestion() {
        if (!showCorrect) {
            showCorrect = true;
            return;
        }
        showCorrect = false;

        if (selectionCorrect) {
            promote(questionId, activeTray);
        } else {
            demote(questionId, activeTray);
        }

        questionId = randomQuestion(activeTray);
    }
</script>

<LocalStorage key="trays" bind:val={trays} />

<h1>Lernfächer</h1>

<p>So gehts:</p>

{#if activeTray != -1}
    <div>
        <h3>Fach {activeTray + 1}</h3>
        <p>
            {trays[activeTray].length} Frage{trays[activeTray].length != 1 ? "n" : ""} (<Percent
                total={allQuestions.length}
                actual={trays[activeTray].length}
            />)
        </p>
    </div>
    {#if questionId}
        <!-- undefined if no question in tray -->
        <QuizQuestion question={questions[questionId]} bind:correct={selectionCorrect} {showCorrect} />
        <button on:click={() => nextQuestion()}>Weiter</button>
    {/if}
    <br />
    <button class="linkLike" on:click={() => setTray(-1)}>Zurück zur Lernfächerauswahl</button>
{:else}
    <div class="trays">
        {#each trays as tray, ti}
            <div class="tray">
                <div>
                    <h3>Fach {ti + 1}</h3>
                    <p>{tray.length} Frage{tray.length != 1 ? "n" : ""} (<Percent total={allQuestions.length} actual={tray.length} />)</p>
                </div>
                <div class="start">
                    <button class="linkLike" on:click={() => setTray(ti)}>Fragen üben</button>
                </div>
            </div>
        {/each}
    </div>
{/if}

<style>
    .trays {
        display: flex;
        flex-direction: column-reverse;
        gap: 20px;
        width: 600px; /* fix for mobile */
    }

    .tray {
        display: grid;
        grid-template-columns: 1fr 1fr;
        padding: 10px;
        background-color: whitesmoke;
        box-shadow: 0 0 2px gray;
    }

    .start {
        display: flex;
    }
</style>
