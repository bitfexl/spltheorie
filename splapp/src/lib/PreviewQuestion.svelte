<script>
    /**
     * The question string.
     */
    export let question;

    /**
     * The answers:
     * {
     *   answer: string,
     *   correct: boolean
     * }
     */
    export let answers;

    /**
     * A image encoded as base64 png or null.
     */
    export let image = null;
</script>

<div>
    <b>
        <p>
            {@html question.replaceAll("\n", "<br/>")}
        </p>
    </b>

    {#if image}
        <img src="data:image/png;base64,{image}" alt="" />
    {/if}

    {#each answers as answer}
        <p class="answer">
            {@html answer.answer.replaceAll("\n", "<br/>")}
            {#if answer.correct}
                <span style="color: #009432; font-weight: bolder;">✓</span>
            {:else}
                <span style="color: #ff7675;">✗</span>
            {/if}
        </p>
    {/each}
</div>

<style>
    .answer {
        padding-top: 0.5em;
    }
</style>
