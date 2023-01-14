<script>
    import githubSvg from "./assets/github-mark.svg";
    import QuestionSelection from "./lib/QuestionSelection.svelte";
    import Quiz from "./lib/Quiz.svelte";
    import Training from "./lib/training/Training.svelte";

    let questions;

    let training = false;

    function startQuiz(e) {
        questions = e.detail;
    }

    function end() {
        questions = null;
        training = false;
    }
</script>

<main>
    {#if questions || training}
        <div class="wrapper">
            <div class="header">
                <button class="linkLike" on:click={end}>Beenden</button>
            </div>
            {#if training}
                <Training />
            {:else}
                <Quiz {questions} on:end={end} />
            {/if}
        </div>
    {:else}
        <QuestionSelection on:start={startQuiz} on:training={() => (training = true)} />
    {/if}
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

    footer {
        position: absolute;
        bottom: 40px;
        width: 100%;
    }

    .wrapper {
        display: inline-block;
    }

    .header {
        text-align: left;
        padding-bottom: 40px;
    }

    .icon {
        height: 1em;
        vertical-align: text-top;
    }
</style>
