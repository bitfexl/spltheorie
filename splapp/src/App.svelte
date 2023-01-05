<script>
    import githubSvg from "./assets/github-mark.svg";
    import Question from "./lib/Question.svelte";
    import QuestionSelector, { getQuestions } from "./lib/QuestionSelector.svelte";

    let selectedCategories;

    let displayedQuestions = [];

    $: questionsText = JSON.stringify(displayedQuestions);

    function displayQuestions() {
        let questions = getQuestions(selectedCategories);
        if (questions) {
            displayedQuestions = questions;
        } else {
            alert("loading...");
        }
    }
</script>

<main>
    <h1>SPL Lernkartei</h1>
    <br />
    <QuestionSelector bind:selectedCategories />
    <br />
    <br />
    <button on:click={displayQuestions}>Fragen anzeigen</button>
    <button on:click={displayQuestions}>Start</button>
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
        {#each displayedQuestions as question}
            <Question question={question.question} answers={question.answers} image={question.image} />
            <br />
        {/each}
    </div>
</main>

<footer>
    <a href="https://github.com/bitfexl/spltheorie"><img class="icon" src={githubSvg} alt="" /> GitHub</a>
</footer>

<style>
    * {
        text-align: center;
    }

    main {
        position: relative;
        top: 40px;
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

    footer {
        position: absolute;
        bottom: 40px;
        width: 100%;
    }

    .icon {
        height: 1em;
        vertical-align: text-top;
    }
</style>
