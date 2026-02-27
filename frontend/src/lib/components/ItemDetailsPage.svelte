<script>
    import {page} from '$app/state';
    import { Button, Input} from 'flowbite-svelte';
    import { PenSolid } from 'flowbite-svelte-icons';
    import {ItemBookings} from '$lib/api';

    /**
     * title: Name of the item
     * content: array of objects of the form {name,value}, where name is the name of the property and value is the value of the property
     */
    let {title, entity} = $props();


    let formattedEntity = $derived(entity.forEach((property) => {
        if(Array.isArray(property)){ 
            property.forEach((singleProperty) =>{ return {...singleProperty, editMode:false}});
        } else {
            return {...property, editMode:false}
        }
    }));
    let endpoint = $derived(entity.endpoint);
    let editMode = $state(false);

    async function saveEntity(event, key){

        const element = event.target;

        let payload = {};

        payload[key] = element.value;

        ItemBookings.patch(page.params.id, payload);
    }
</script>

{#snippet textWithLabel(property)}
    <div class="flex-1">
        <span class="font-bold text-muted">{property.name}</span>
        {#if property.endpoint}
        <div class="w-full h-11 p-2.5 border border-gray-600/70 bg-gray-700/70 rounded-lg items-center text-text">
            <a href={`/manage${endpoint}`} class="text-orange-400">{property.content}</a></div>
        {:else if Array.isArray(property.content)}
            {#each property.content as item}
                <p>{item}</p>
            {/each}
        {:else}
            {#if property.isEditable}
                <Input type="text" onclick={(e) => saveEntity(e, property.key)}>
                {#snippet left()}
                    <Button><PenSolid/></Button>
                {/snippet}
                </Input>
            {:else}
                <div class="w-full h-11 p-2.5 border opacity-70 border-gray-600 bg-gray-700 rounded-lg items-center text-text">{property.content}</div>
            {/if}
        {/if}
    </div>
{/snippet}

<div class="flex flex-col justify-center items-center">
    <div class="w-full flex flex-col bg-white border rounded-lg max-w-2xl border-gray-200 dark:bg-gray-800 dark:border-gray-700 shadow-md  p-5 grow"
         size="lg">
        <div class="flex flex-row justify-between">
        <span class="font-bold text-2xl text-text text-center">{`${title} ID: ${page.params.id}`}</span>
        <Button><PenSolid/></Button></div>
        <div class="flex flex-col gap-5">
            {#each entity.content as property}
                {#if Array.isArray(property)}
                <div class="flex gap-5">
                    {#each property as singleProperty}
                    {@render textWithLabel(singleProperty.name, singleProperty.value, singleProperty.endpoint)}
                    {/each} 
                </div>
                {:else}
                    {@render textWithLabel(property)}
                {/if}
            {/each}
        </div>
    </div>
</div>