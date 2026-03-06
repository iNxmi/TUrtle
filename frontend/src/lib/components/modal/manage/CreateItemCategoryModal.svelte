<script>
    import {Button, Heading, Hr, Input, Modal, MultiSelect, Select, Spinner} from "flowbite-svelte";
    import {m} from "$lib/paraglide/messages.js";
    import {ItemCategories} from "$lib/api";
    import {invalidateAll} from "$app/navigation";

    let {
        open = $bindable(false),
    } = $props();

    let input = $state({
        name: ""
    })

    let loading = $state(false);
    let error = $state("");

    async function submit(event) {
        event.preventDefault()
        error = "";

        const payload = $state.snapshot(input);

        loading = true;
        const response = await ItemCategories.create(payload);
        loading = false;

        if (response.status !== 201) {
            const json = await response.json();
            error = json.message;
            return;
        }

        await invalidateAll();
        open = false;
    }
</script>

<Modal form bind:open={open} outsideclose={false}>
    <form onsubmit={submit} class="flex flex-col gap-5">
        <Heading tag="h3" class="text-center">
            {m.modal_manage_create_item_category_title()}
        </Heading>

        <Hr class="m-0 p-0"/>

        <div>
            <div>{m.modal_manage_create_item_category_label_name()}</div>
            <Input name="name" type="text" bind:value={input.name} required/>
        </div>

        {#if error?.trim()}
            <div class="text-red-400 text-justify">{error}</div>
        {/if}

        <Button name="button_submit" type="submit">
            {#if loading === true}
                <Spinner size="5"/>
            {:else}
                {m.modal_manage_create_item_category_button()}
            {/if}
        </Button>
    </form>
</Modal>