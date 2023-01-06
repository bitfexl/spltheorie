<script>
    /**
     * The question json.
     * see: QuestionSelector
     */
    export let question;

    /**
     * answerIndex of the currently selected answer or null;
     */
    export let selected = null;

    /**
     * If the selection is correct.
     */
    export let correct = false;

    /**
     * Show the correct answer.
     */
    export let showCorrect = false;

    $: {
        correct = question.answers.find((answer) => answer.answerIndex == selected)?.correct ?? false;
    }

    $: {
        question; // listen for update
        update();
    }

    function update() {
        selected = null;
    }
</script>

<div class="question">
    <b>
        <p>
            {@html question.question.replaceAll("\n", "<br/>")}
        </p>
    </b>

    {#if question.image}
        <img src="data:image/png;base64,{question.image}" alt="" />
    {/if}

    {#each question.answers as answer}
        <p class="answer">
            <input type="radio" bind:group={selected} value={answer.answerIndex} id="answer_{answer.answerIndex}" disabled={showCorrect} />
            <label for="answer_{answer.answerIndex}">
                {@html answer.answer.replaceAll("\n", "<br/>")}

                {#if answer.correct && showCorrect}
                    <span class="marker right" style="font-weight: bolder;">✓</span>
                {:else if showCorrect}
                    <span class="marker wrong">✗</span>
                {/if}
            </label>
        </p>
    {/each}

    <br />

    {#if showCorrect && correct}
        <p class="right">Richtig</p>
    {:else if showCorrect}
        <p class="wrong">Falsch</p>
    {/if}
</div>

<style>
    .question {
        padding-top: 0.6em;
    }

    .answer {
        padding-top: 0.5em;
        display: grid;
        grid-template-columns: min-content auto;
        gap: 10px;
    }

    .marker {
        user-select: none;
    }

    .right {
        color: #009432;
    }

    .wrong {
        color: #ff7675;
    }

    br {
        user-select: none;
    }
</style>
