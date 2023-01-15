<script>
    import { createEventDispatcher } from "svelte";
    import SelfCheckQuestion from "./SelfCheckQuestion.svelte";

    export let questions;

    $: {
        questions; // listen for update
        update();
    }

    const dispatch = createEventDispatcher();

    let showResult;

    let openQuestions = [];
    let currentQuestion;
    let initialCount;
    let wrongAnswered;

    function update() {
        openQuestions = [...questions];
        initialCount = openQuestions.length;
        wrongAnswered = 0;
        showResult = false;
        currentQuestion = randomQuestion();
    }

    function randomQuestion() {
        let i = Math.floor(Math.random() * openQuestions.length);
        let question = openQuestions[i];
        openQuestions.splice(i, 1);
        openQuestions = [...openQuestions]; // update
        return question;
    }

    function nextQuestion(e) {
        if (!e.detail.correct) {
            // openQuestions.push(currentQuestion); // endless mode (every question must be answered correctly)
            wrongAnswered++;
        }

        if (openQuestions.length == 0) {
            showResult = true;
            return;
        }

        currentQuestion = randomQuestion();
    }
</script>

<div class="wrapper">
    {#if showResult}
        <h2>Ergebnis</h2>
        <br />
        <p>{initialCount - wrongAnswered}/{initialCount} Fragen wurden richtig beantwortet.</p>
        <p>Dies entspricht {Math.round(((initialCount - wrongAnswered) / initialCount) * 10000) / 100}%.</p>
        <p>Bei einer Prüfung werden mindestens 75% in jedem Teilbereich benötigt.</p>
        <br />
        <button on:click={() => dispatch("end")}>Beenden</button>
    {:else}
        <p class="note">Frage {initialCount - openQuestions.length}/{initialCount}</p>
        <div class="question">
            <SelfCheckQuestion question={currentQuestion} on:next={nextQuestion} />
        </div>
    {/if}
</div>

<style>
    .question {
        margin-top: 20px;
    }

    .note {
        color: gray;
    }
</style>
