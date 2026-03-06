<script>
    import {Button, Heading, Hr, Input, Modal, MultiSelect, Select, Spinner, Toggle} from "flowbite-svelte";
    import {m} from "$lib/paraglide/messages.js";
    import {Lockers} from "$lib/api";
    import {invalidateAll} from "$app/navigation";

    let {
        open = $bindable(false),
    } = $props();

    let input = $state({
        index: 0,
        name: "",
        isSoftwareUnlockable: false,
        locked: false
    })

    let loading = $state(false);
    let error = $state("");

    async function submit(event) {
        event.preventDefault()
        error = "";

        const payload = $state.snapshot(input);

        loading = true;
        const response = await Lockers.create(payload);
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
            {m.modal_manage_create_locker_title()}
        </Heading>

        <Hr class="m-0 p-0"/>

        <!--TODO remove arrows in input field -->
        <div>
            <div>{m.modal_manage_create_locker_label_index()}</div>
            <Input name="index" type="number" bind:value={input.index} required/>
        </div>

        <div>
            <div>{m.modal_manage_create_locker_label_name()}</div>
            <Input name="name" type="text" bind:value={input.name} required/>
        </div>

        <div>
            <div>{m.modal_manage_create_locker_label_isSoftwareUnlockable()}</div>
            <Toggle name="isSoftwareUnlockable" bind:checked={input.isSoftwareUnlockable}/>
        </div>

        <div>
            <div>{m.modal_manage_create_locker_label_locked()}</div>
            <Toggle name="locked" bind:checked={input.locked}/>
        </div>

        {#if error?.trim()}
            <div class="text-red-400 text-justify">{error}</div>
        {/if}

        <Button name="button_submit" type="submit">
            {#if loading === true}
                <Spinner size="5"/>
            {:else}
                {m.modal_manage_create_locker_button()}
            {/if}
        </Button>
    </form>
</Modal>