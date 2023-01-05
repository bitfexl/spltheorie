<script>
    import Counter from "./lib/Counter.svelte";
    import githubSvg from "./assets/github-mark.svg";
</script>

<main>
    <h1>SPL Lernkartei</h1>
    <Counter />
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

    .icon {
        height: 1em;
        vertical-align: text-top;
    }
</style>
