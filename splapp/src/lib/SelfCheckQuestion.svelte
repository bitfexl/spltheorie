<script>
    import { createEventDispatcher } from "svelte";
    import QuizQuestion from "./QuizQuestion.svelte";

    /**
     * The question json.
     * see: QuestionSelector
     */
    export let question;

    const dispatch = createEventDispatcher();

    let showCorrect = false;

    let correct;
    let selected;

    function next() {
        if (!showCorrect) {
            showCorrect = true;
            return;
        }

        showCorrect = false;

        onFinish();
    }

    function onFinish() {
        dispatch("next", {
            question,
            correct,
        });
    }
</script>

<div class="questionWrapper">
    <div class="question">
        <QuizQuestion {question} bind:selected bind:correct {showCorrect} />
    </div>
</div>
<div class="center">
    <button on:click={next} disabled={!selected}>{showCorrect ? "Weiter" : "Prüfen"}</button>
</div>

<style>
    .center {
        text-align: center;
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

    button {
        margin-top: 20px;
        font-size: 1.06em;
    }
</style>
