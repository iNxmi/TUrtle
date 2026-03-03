<script>
    import {page} from '$app/state';
    import { Button, Input} from 'flowbite-svelte';
    import { PenSolid, FloppyDiskAltSolid } from 'flowbite-svelte-icons';
    import {ItemBookings} from '$lib/api';
    import WhitelistDropdown from './WhitelistDropdown.svelte';

    let {title, entity} = $props();
    
    let formattedEntity = $derived.by(() => {
        
        let id = 0;
        let tempEntity = [];
        
        entity.content.forEach((property) => {
            
            if(Array.isArray(property)){
                let groupId = 0;
                let group = [];
                for(let i = 0; i < property.length; i++){
                    group.push({...property[i], id: groupId});
                    groupId++;
                }
                
                tempEntity.push({group: group, id: id});
                id++;
            } else {
                tempEntity.push({...property, id: id});
                id++;
            }
        });
        
        return tempEntity;
    });
    
    let finalEntity = $state(formattedEntity);
    
    let endpoint = $derived(entity.endpoint);
    
    async function saveEntity(property){
        
        let value = property.value;
        if(Array.isArray(property.value)){
            value = value[0];
        } else if(property.inputType === 'datetime-local'){
            
            value = new Date(value);
        }
        let payload = {};
        
        payload[property.key] = value;
        
        ItemBookings.patch(page.params.id, payload);
    }
    
    function toggleEditMode(property){
        
        if(property.editMode){
            saveEntity(property);
        }
        property.editMode = !property.editMode;
    }
</script>
<!--
 * @component
 * A component for displaying database entries.
 * 
 * ### Usage
 * **title:** the display name of the entity (has to be localized)
 * **entity:** An object that contains information about the properties of the entity
 * The object has the following structure:
 * ```{endpoint, content}```
 * **endpoint** The backend endpoint for the entity
 * **content:** an Array that has the following syntax:
 * ```{name, value, isEditable (optional), inputType (optional), key (optional), enum (optional)}```
 * **name:** The display name of the property
 * **value:** The value of the property 
 * **isEditable (optional):** true, if value should be updatable by the user
 * **inputType (optional):** a HTML input type value e.g. "text", "email". For dates use "datetime-local"
 * **key (optional):** The name of the property in the database, needed if isEditable is set to *true*
 * **enum (optional):** An array containing all possible values for this property
 * 
 * If you want to display multiple properties in one row, you can wrap the objects into an array e.g
 *
 * ```[{prop1}, {prop2}, [{prop3}, {prop4}], {prop5}]```
 -->
{#snippet textWithLabel(property, i,j)}
<div class="flex-1">
    <span class="font-bold text-muted">{property.name}</span>
    {#if property.endpoint}
        <div class="w-full h-11 p-2.5 border border-gray-600/70 bg-gray-700/70 rounded-lg items-center text-text">
            <a href={`${property.endpoint}`} class="text-orange-400">{property.value}</a>
        </div>
    {:else if Array.isArray(property.value)}
        {#each property.value as item}
        <p>{item}</p>
            {/each}
        {:else}
            {#if property.isEditable}
                {#if property.editMode}
                    {#if property.enum}
                        <WhitelistDropdown users={property.enum} bind:value={property.value} single>
                            <FloppyDiskAltSolid onclick={() => toggleEditMode(property)} class="hover:text-csw cursor-pointer"/>
                        </WhitelistDropdown>     
                    {:else}
                        <Input bind:value={property.value} type={property.inputType || "text"} onclick={() => saveEntity(property.key)}>
                            {#snippet right()}
                                <FloppyDiskAltSolid onclick={() => toggleEditMode(property)} class="hover:text-csw cursor-pointer"/>
                            {/snippet}
                        </Input>
                    {/if}
                {:else}
                    <div class="block relative w-full h-11 rtl:text-right opacity-70 px-2.5 py-2.5 border border-gray-300 dark:border-gray-600  bg-gray-50  dark:bg-gray-700 rounded-lg text-text">{property.value}
                        <div class="flex absolute inset-y-0 items-center text-gray-500 dark:text-gray-400 end-0 p-2.5">
                            <PenSolid onclick={() => toggleEditMode(property)} class="hover:text-csw cursor-pointer"/>
                        </div>
                    </div>
                {/if}
            {:else}
                <div class="w-full h-11 p-2.5 border opacity-70 border-gray-600 bg-gray-700 rounded-lg items-center text-text">
                        {property.value}
                </div>
            {/if}
        {/if}
    </div>
{/snippet}

<div class="flex flex-col justify-center items-center">
    <div class="w-full flex flex-col bg-white border rounded-lg max-w-2xl border-gray-200 dark:bg-gray-800 dark:border-gray-700 shadow-md  p-5 grow"
         size="lg">
        <div class="flex flex-row justify-center">
            <span class="font-bold text-2xl text-text text-center">{`${title} ID: ${page.params.id}`}</span>
        </div>
        <div class="flex flex-col gap-5">
            {#each finalEntity as property, i (property.id)}
                {#if property.group}
                    <div class="flex gap-5">
                        {#each property.group as singleProperty, j (singleProperty.id)}
                            {@render textWithLabel(singleProperty, i, j)}
                        {/each} 
                    </div>
                {:else}
                    {@render textWithLabel(property, i)}
                {/if}
            {/each}
        </div>
    </div>
</div>