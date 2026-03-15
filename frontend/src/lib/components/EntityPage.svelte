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
    import {goto, invalidateAll} from "$app/navigation";

    let {
        items = [],
        controls = [],
        onPatch,
        onDelete
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

        loadingEdit = true;
        const payload = difference(updatedValues, initialValues);
        const response = await onPatch?.(payload);
        loadingEdit = false;

        if (response.ok !== true) {
            alert(`Error: ${JSON.stringify(response, null, 2)}`);
            return
        }

        await invalidateAll();
        edit = false;
    }

    async function remove(event) {
        event.preventDefault();

        loadingRemove = true;
        const response = await onDelete?.()
        loadingRemove = false;

        if (response.ok !== true) {
            alert(`Error: ${JSON.stringify(response, null, 2)}`);
            return
        }

        await invalidateAll();

        const url = window.location.pathname;
        const newPath = url.substring(0, url.lastIndexOf('/'));

        await goto(newPath);
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

    async function redirect(enabled, href) {
        if(!enabled)
            return;

        if(!href)
            return;

        await goto(href);
    }

    let edit = $state(false);
    let loadingEdit = $state(false);
    let loadingRemove = $state(false);
</script>

{#snippet field(property, edit)}
    <div class="flex flex-col">
        <div>{property.label}</div>
        <ButtonGroup>
            {@const enabled = (edit === true && property.editable === true)}
            {@const Component = property.component}
            <Component onclick={() => redirect(property.href)} bind:value={updatedValues[property.field]} disabled={!enabled} {...property.props}/>
            {#if edit === true && property.editable === true}
                {@const isEqual = _.isEqual(updatedValues[property.field], initialValues[property.field])}
                <Button disabled={isEqual}
                        onclick={() => updatedValues[property.field] = initialValues[property.field]}>
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
                        <div class="flex-1">
                            {@render field(subItem, edit)}
                        </div>
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

            {#each controls as control}
                <ButtonGroup>
                    {@const Component = control.component}
                    {@const Icon = control.icon}
                    <Component {...control.props}><Icon/></Component>
                </ButtonGroup>
            {/each}

            {#if onPatch}
                <ButtonGroup>
                    {#if edit === true}
                        <Button color="orange" disabled={loadingEdit === true} onclick={patch}>
                            {#if loadingEdit === true}
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
            {/if}
        </div>

        {#if onDelete}
            <ButtonGroup>
                <Button color="red" class="w-full" onclick={remove} disabled={loadingRemove}>
                    {#if loadingRemove === true}
                        <Spinner size="5"/>
                    {:else}
                        <TrashBinOutline/>
                    {/if}
                </Button>
            </ButtonGroup>
        {/if}
    </Card>
</div>