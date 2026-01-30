<script>
	import { page } from '$app/state';

    /**
     * title: Name of the item
     * content: array of objects of the form {name,value}, where name is the name of the property and value is the value of the property
     */
    let { title, content} = $props();
</script>
{#snippet textWithLabel(label, content, endpoint)}
    <div class="flex flex-col">
        <span class="font-bold text-muted">{label}</span>
        {#if endpoint}
            <a href={`/admin${endpoint}`} class="text-csw">{content}</a>
        {:else if Array.isArray(content)}
            {#each content as item}
                <p>{item}</p>
            {/each}
        {:else}
           <p>{content}</p>
        {/if}
    </div>
{/snippet}
<div class="flex flex-col justify-center items-center">
    <div class="w-full flex bg-white border rounded-lg max-w-2xl border-gray-200 dark:bg-gray-800 dark:border-gray-700 shadow-md flex-col p-5 grow" size="lg">
        <span class="font-bold text-2xl text-center">{`${title} ID: ${page.params.id}`}</span>
        <div class="flex flex-col gap-5">
            {#each content as property}
                {@render textWithLabel(property.name, property.value, property.endpoint)}
            {/each}
        </div>
    </div>
</div>