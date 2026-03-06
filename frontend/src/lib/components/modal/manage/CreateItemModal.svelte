<script>
    import {Button, Datepicker, Heading, Hr, Modal, Select, Spinner, Input, Textarea} from "flowbite-svelte";
    import {m} from "$lib/paraglide/messages.js";
    import TimePicker from "$lib/components/TimePicker.svelte";
    import {Items} from "$lib/api";
    import {invalidateAll} from "$app/navigation";

    let {
        open = $bindable(false),
        categoryList = [],
        lockerList = []
    } = $props();

    let categoryItems = $derived(categoryList.map((category) => ({
        value: category.id,
        name: category.name
    })));
    let categoryId = $state(null);

    let lockerItems = $derived(lockerList.map((locker) => ({
        value: locker.id,
        name: locker.name
    })));
    let lockerId = $state(null);

    let name = $state();
    let description = $state();
    let needsConfirmation = $state(false);
    let acquiredAt = $state(new Date());

    let loading = $state(false);
    let error = $state("");

    async function submit(event) {
        event.preventDefault();
        error = "";

        const payload = {
            name: name,
            categoryId: categoryId,
            lockerId: lockerId,
            description: description,
            needsConfirmation: needsConfirmation,
            acquiredAt: acquiredAt.toISOString()
        };

        loading = true;
        const response = await Items.create(payload);
        loading = false;

        if (!response.ok) {
            const json = await response.json();
            error = json.message;
            return;
        }
        await invalidateAll();
        open = false;
    }
</script>

<Modal form bind:open={open} outsideclose={false}>
    <div class="flex flex-col gap-5">
        <Heading tag="h3" class="text-center">
            {m.modal_manage_create_item_title()}
        </Heading>

        <Hr class="m-0 p-0"/>

        <form class="shrink flex flex-col gap-5" onsubmit={submit}>
            <div>
                <div>{m.modal_manage_create_item_label_name()}</div>
                <Input bind:value={name} required/>
            </div>

            <div>
                <div>{m.modal_manage_create_item_label_category()}</div>
                <Select bind:value={categoryId} items={categoryItems} required/>
            </div>

            <div>
                <div>{m.modal_manage_create_item_label_locker()}</div>
                <Select bind:value={lockerId} items={lockerItems} required/>
            </div>

            <div>
                <div>{m.modal_manage_create_item_label_description()}</div>
                <Textarea class="w-full" bind:value={description} required/>
            </div>

            <div>
                <div>{m.modal_manage_create_item_label_needs_confirmation()}</div>
                <Input bind:value={needsConfirmation} required/>
            </div>

            <div>
                <div>{m.modal_manage_create_item_label_acquired_at()}</div>
                <div class="flex flex-col gap-1">
                    <Datepicker bind:value={acquiredAt}/>
                    <TimePicker bind:value={acquiredAt}/>
                </div>
            </div>

            {#if error?.trim()}
                <div class="text-red-400 text-justify">{error}</div>
            {/if}

            <div class="grow flex flex-col justify-end">
                <Button class="w-full" type="submit">
                    {#if loading === true}
                        <Spinner size="5"/>
                    {:else}
                        {m.modal_manage_create_item_button()}
                    {/if}
                </Button>
            </div>
        </form>
    </div>
</Modal>