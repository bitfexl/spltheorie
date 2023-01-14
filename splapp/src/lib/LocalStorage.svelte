<script>
    import { onMount } from "svelte";

    export let key;
    export let val = undefined;

    let allowChange = false;

    onMount(() => {
        let stored = localStorage.getItem(key);
        if (stored != null) {
            val = JSON.parse(stored);
        }
        allowChange = true;
    });

    $: {
        key;
        val;

        if (allowChange) {
            localStorage.setItem(key, JSON.stringify(val));
        }
    }
</script>
