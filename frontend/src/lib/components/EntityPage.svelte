<script>
    import Card from "$lib/components/Card.svelte";
    import _ from "lodash";
    import {Button, ButtonGroup, Spinner, Tooltip} from 'flowbite-svelte';
    import {
        CloseOutline,
        EditOutline,
        FloppyDiskAltOutline,
        ShareNodesOutline,
        TrashBinOutline,
        UndoOutline
    } from 'flowbite-svelte-icons';
    import {invalidateAll} from "$app/navigation";

    let {
        title,
        items,
        onPatch
    } = $props();

    const flatItems = $derived(_.flattenDeep(items));

    const initialValues = _.reduce(flatItems, (acc, item) => {
        if (item.field && item.props?.value !== undefined)
            acc[item.field] = item.props.value;

        return acc;
    }, {});

    let updatedValues = $state(structuredClone(initialValues));

    function difference(dominant, submissive) {
        const result = {};

        for (const key in submissive)
            if (!_.isEqual(submissive[key], dominant[key]))
                result[key] = dominant[key];

        return result;
    }

    async function patch(event) {
        event.preventDefault();

        loading = true;
        const payload = difference(updatedValues, initialValues);
        const response = await onPatch(payload);
        loading = false;

        if (response.ok !== true)
            alert(JSON.stringify(response, null, 2));

        await invalidateAll();
        edit = false;
    }

    async function cancel(event) {
        event.preventDefault();

        await invalidateAll();
        updatedValues = structuredClone(initialValues);
        edit = false;
    }

    async function clipboard(event) {
        event.preventDefault();

        const url = window.location.href;
        await navigator.clipboard.writeText(url);
    }

    let edit = $state(false);
    let loading = $state(false);
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

{#snippet field(property, edit)}
    <div class="flex-1 flex flex-col">
        <div>{property.label}</div>
        <ButtonGroup class="flex-1">
            {@const enabled = (edit === true && property.editable === true)}
            {@const Component = property.component}
            <Component bind:value={updatedValues[property.field]} disabled={!enabled} {...property.props} />
            {#if edit === true && property.editable === true}
                {@const isEqual = _.isEqual(updatedValues[property.field], initialValues[property.field])}
                <Button disabled={isEqual} onclick={() => updatedValues[property.field] = initialValues[property.field]}>
                    <UndoOutline/>
                </Button>
            {/if}
        </ButtonGroup>
    </div>
{/snippet}

<div class="flex flex-col lg:flex-row gap-5">
    <Card class="grow flex flex-col gap-5">
        {#each items as item}
            {#if Array.isArray(item)}
                <div class="flex gap-5">
                    {#each item as subItem}
                        {@render field(subItem, edit)}
                    {/each}
                </div>
            {:else}
                {@render field(item, edit)}
            {/if}
        {/each}
    </Card>

    <Card class="flex lg:flex-col gap-5 justify-between">
        <div class="flex lg:flex-col gap-5">
            <ButtonGroup>
                <Button color="alternative" class="w-full" onclick={clipboard}>
                    <Tooltip trigger="click">
                        _copied_
                    </Tooltip>
                    <ShareNodesOutline/>
                </Button>
            </ButtonGroup>

            <ButtonGroup>
                {#if edit === true}
                    <Button color="orange" disabled={loading === true} onclick={patch}>
                        {#if loading === true}
                            <Spinner size="5"/>
                        {:else}
                            <FloppyDiskAltOutline/>
                        {/if}
                    </Button>
                    <Button onclick={cancel}>
                        <CloseOutline/>
                    </Button>
                {:else}
                    <Button color="orange" onclick={() => edit = true}>
                        <EditOutline/>
                    </Button>
                {/if}
            </ButtonGroup>
        </div>

        <ButtonGroup>
            <Button color="red" class="w-full">
                <TrashBinOutline/>
            </Button>
        </ButtonGroup>
    </Card>
</div>