<script context="module">
    import questionIndex from "./questionIndex.json";

    let questions = {};

    for (let category of questionIndex.categories) {
        fetch(questionIndex.urlTemplate.replace("{id}", category.id)).then((data) => {
            data.json().then((qs) => {
                questions[category.id] = qs;
            });
        });
    }

    export function getQuestions(categories) {
        let qs = [];

        for (let category of categories) {
            if (questions[category]) {
                qs.push(...questions[category]);
            } else {
                return false;
            }
        }

        return qs;
    }
</script>

<script>
    export let selectedCategories = [];
    export let selectedCategoriesSum = 0;

    // calculate selectedCategoriesSum
    $: {
        let sum = 0;
        for (let cat of questionIndex.categories) {
            if (selectedCategories.includes(cat.id)) {
                sum += cat.count;
            }
        }
        selectedCategoriesSum = sum;
    }

    // overall sum
    let questionSum = 0;
    for (let cat of questionIndex.categories) {
        questionSum += cat.count;
    }

    function handleSelectAll(e) {
        let selected = [];
        if (e.target.checked) {
            for (let cat of questionIndex.categories) {
                selected.push(cat.id);
            }
        }
        selectedCategories = selected;
    }
</script>

<div class="wrapper">
    <input
        type="checkbox"
        checked={selectedCategories.length == questionIndex.categories.length}
        on:click={handleSelectAll}
        id="selectAll"
    />
    <label for="selectAll">Alle auswählen <span class="count">({questionSum} Fragen)</span></label>

    {#each questionIndex.categories as cat}
        <div class="category">
            <input type="checkbox" bind:group={selectedCategories} value={cat.id} id="category_{cat.id}" />
            <label for="category_{cat.id}">
                <span class="name">{cat.name}</span>
                <span class="count">({cat.count} Fragen)</span>
            </label>
        </div>
    {/each}

    {#if selectedCategoriesSum > 0}
        <p class="selectedCategories">
            {selectedCategories} <span class="count">({selectedCategoriesSum} Fragen)</span>
        </p>
    {/if}
</div>

<style>
    .wrapper {
        display: inline-block;
        user-select: none;
    }

    .category {
        font-size: 1.2em;
        display: grid;
        grid-template-columns: min-content auto;
    }

    input[type="checkbox"] {
        width: 2em;
        height: 1.2em;
        position: relative;
        top: 0.08em;
    }

    .category input[type="checkbox"] {
        top: 0.24em;
    }

    .count,
    .selectedCategories {
        color: gray;
    }

    .selectedCategories {
        padding-left: 2em;
    }
</style>
