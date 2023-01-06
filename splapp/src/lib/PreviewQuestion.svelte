<script>
    import Image from "./Image.svelte";

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

<div class="question">
    <b>
        <p>
            {@html question.replaceAll("\n", "<br/>")}
        </p>
    </b>

    {#if image}
        <Image src="data:image/png;base64,{image}" />
    {/if}

    {#each answers as answer}
        <p class="answer">
            {@html answer.answer.replaceAll("\n", "<br/>")}
            {#if answer.correct}
                <span class="marker" style="color: #009432; font-weight: bolder;">✓</span>
            {:else}
                <span class="marker" style="color: #ff7675;">✗</span>
            {/if}
        </p>
    {/each}
</div>

<style>
    .question {
        padding-top: 0.6em;
    }

    .answer {
        padding-top: 0.5em;
    }

    .marker {
        user-select: none;
    }
</style>
