<script>
    import { createEventDispatcher } from "svelte";
    import QuizQuestion from "./QuizQuestion.svelte";

    export let questions;

    $: {
        questions; // listen for update
        update();
    }

    const dispatch = createEventDispatcher();

    let selected;

    let currentQuestionCorrect;
    let showCorrect;
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
        showCorrect = true; // trigger question update
        nextQuestion();
    }

    function nextQuestion() {
        if (!showCorrect) {
            showCorrect = true;
            return;
        }
        showCorrect = false;

        if (currentQuestion && !currentQuestionCorrect) {
            // openQuestions.push(currentQuestion); // endless mode (every question must be answered correctly)
            wrongAnswered++;
        }

        if (openQuestions.length == 0) {
            showResult = true;
            return;
        }

        let i = Math.floor(Math.random() * openQuestions.length);
        currentQuestion = openQuestions[i];
        openQuestions.splice(i, 1);
        openQuestions = [...openQuestions]; // update
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
            <QuizQuestion question={currentQuestion} bind:correct={currentQuestionCorrect} {showCorrect} bind:selected />
        </div>
        <div class="next">
            <button on:click={nextQuestion} disabled={selected == null}>Weiter</button>
        </div>
    {/if}
</div>

<style>
    .question {
        min-height: 300px;
        padding-bottom: 40px;
        background-color: #fcfcfc;
        box-shadow: 0 0 2px #e0e0e0;
        padding: 10px;
        border-radius: 2px;
        margin: 20px 0;
    }

    .next {
        text-align: center;
        font-size: 1.2em;
    }

    .note {
        color: gray;
    }
</style>
