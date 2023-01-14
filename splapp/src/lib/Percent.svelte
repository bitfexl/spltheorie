<script>
    export let total;
    export let actual;
</script>

{Math.round((actual / total) * 10000) / 100}%
