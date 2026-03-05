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
            _CreateLocker_
        </Heading>

        <Hr class="m-0 p-0"/>

        <div>
            <div>_CreateLockerIndex_</div>
            <Input name="index" type="number" bind:value={input.Index} required/>
        </div>

        <div>
            <div>_CreateLockerName_</div>
            <Input name="name" type="text" bind:value={input.name} required/>
        </div>

        <div>
            <div>_CreateLockerIsSoftwareUnlockable_</div>
            <Toggle name="isSoftwareUnlockable" bind:checked={input.isSoftwareUnlockable}/>
        </div>

        <div>
            <div>_CreateLockerLocked_</div>
            <Toggle name="locked" bind:checked={input.locked}/>
        </div>

        {#if error?.trim()}
            <div class="text-red-400 text-justify">{error}</div>
        {/if}

        <Button name="button_submit" type="submit">
            {#if loading === true}
                <Spinner size="5"/>
            {:else}
                _CreateLocker_
            {/if}
        </Button>
    </form>
</Modal>