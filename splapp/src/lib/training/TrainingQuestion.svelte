<script>
    import { createEventDispatcher } from "svelte";
    import QuizQuestion from "../QuizQuestion.svelte";

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

<QuizQuestion {question} bind:selected bind:correct {showCorrect} />
<div class="center">
    <button on:click={next} disabled={!selected}>{showCorrect ? "Weiter" : "Prüfen"}</button>
</div>

<style>
    .center {
        text-align: center;
    }

    button {
        margin-top: 20px;
        font-size: 1em;
    }
</style>
