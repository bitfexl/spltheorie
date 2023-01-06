<script>
    export let src;

    export let alt = "";

    /**
     * Max size in pixel.
     */
    export let size = 450;
</script>

<img {src} {alt} style:max-width="min({size}px, 100%)" style:max-height="min({size}px, 100%)" />
