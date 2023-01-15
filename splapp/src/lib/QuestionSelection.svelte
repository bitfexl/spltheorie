<script>
    import { createEventDispatcher } from "svelte";
    import Question from "./PreviewQuestion.svelte";
    import QuestionSelector, { getQuestions } from "./QuestionSelector.svelte";

    const dispatch = createEventDispatcher();

    let selectedCategories;

    let selectedQuestions = [];

    function sendStartEvent() {
        if (loadSelectedQuestions() && selectedQuestions.length > 0) {
            dispatch("start", selectedQuestions);
        }
    }

    function sendTrainingEvent() {
        dispatch("training");
    }

    function displayQuestions() {
        loadSelectedQuestions();
    }

    function loadSelectedQuestions() {
        let questions = getQuestions(selectedCategories);

        if (questions) {
            selectedQuestions = questions;
        } else {
            alert("loading...");
            // todo: fix loading display
            return false;
        }

        return true;
    }
</script>

<div>
    <h1>SPL Lernkartei</h1>

    <p class="note">Stand 1. September 2022</p>

    <div class="spacing" />

    <QuestionSelector bind:selectedCategories />

    <div class="spacing" />

    <button class="linkLike" on:click={sendTrainingEvent}>Lernfächer</button>

    <div class="spacing-sm" />

    <button on:click={displayQuestions}>Fragen anzeigen</button>
    <button on:click={sendStartEvent}>Quiz starten</button>

    <div class="spacing" />

    <div class="explanation">
        <ol>
            <li>Die gewünschten Fragen auswählen</li>
            <li>Start drücken</li>
            <li>Die Fragen werden solange zufällig abgeprüft,<br />bis jede Frage ein mal beantwortet wurde</li>
        </ol>
    </div>

    <div class="spacing" />

    <div class="question">
        <div class="spacing separator" />
        <div class="spacing" />

        {#each selectedQuestions as question}
            <Question question={question.question} answers={question.answers} image={question.image} id={question.id} />
            <br />
        {/each}

        <div class="spacing separator" />
    </div>
</div>

<style>
    * {
        text-align: center;
    }

    .explanation {
        display: inline-block;
        text-align: left;
    }

    .explanation * {
        text-align: left;
    }

    .question {
        display: inline-block;
        max-width: 600px;
    }

    .spacing {
        padding-bottom: 34px;
    }

    .spacing-sm {
        padding-bottom: 17px;
    }

    .separator {
        border: 0;
        border-bottom: 2px solid darkgray;
    }

    .note {
        color: gray;
    }
</style>
