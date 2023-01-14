<script>
    import questionIndex from "../questionIndex.json";
    import { getQuestions } from "../QuestionSelector.svelte";
    import LocalStorage from "../LocalStorage.svelte";

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

    let trays = [allQuestions.map((q) => q.id), [], [], [], []];

    function randomQuestion(i) {
        let tray = trays[i];
        return tray[Math.random() * tray.length];
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
</script>

<h1>Lernfächer</h1>

<LocalStorage key="trays" bind:val={trays} />

{#each trays as tray, ti}
    <div class="questions">
        {#each tray as question}
            <div>
                {question}
                <button on:click={() => promote(question, ti)}>Promote</button>
                <button on:click={() => demote(question, ti)}>Demote</button>
            </div>
        {/each}
    </div>
{/each}

{JSON.stringify(trays, null, 2)}

<style>
    h1 {
        color: red;
    }

    .questions {
        display: grid;
        grid-template-columns: repeat(7, 1fr);
        padding-bottom: 40px;
    }
</style>
