<script>
    import Question from "./Question.svelte";
    import QuestionSelector, { getQuestions } from "./QuestionSelector.svelte";

    let selectedCategories;

    let selectedQuestions = [];

    function startQuiz() {
        loadSelectedQuestions();
        // start event
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
        }
    }
</script>

<div>
    <h1>SPL Lernkartei</h1>
    <br />
    <QuestionSelector bind:selectedCategories />
    <br />
    <br />
    <button on:click={displayQuestions}>Fragen anzeigen</button>
    <button on:click={startQuiz}>Start</button>
    <br />
    <br />
    <div class="explanation">
        <ol>
            <li>Die gewünschten Fragen auswählen</li>
            <li>Start drücken</li>
            <li>Die Fragen werden solange zufällig abgeprüft,<br />bis jede Frage ein mal richtig beantwortet wurde</li>
        </ol>
    </div>
    <br />
    <br />
    <div class="question">
        {#each selectedQuestions as question}
            <Question question={question.question} answers={question.answers} image={question.image} />
            <br />
        {/each}
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
</style>
