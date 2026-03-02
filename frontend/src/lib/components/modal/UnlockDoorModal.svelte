<script>
    import {m} from "$lib/paraglide/messages.js";
    import {Hardware} from "$lib/api";
    import {Button, Modal, Tooltip, Spinner} from "flowbite-svelte";
    import {QuestionCircleSolid} from "flowbite-svelte-icons";

    let {
        open = $bindable(false)
    } = $props();

    let loading = $state(false);
    let error = $state("");

    async function unlock(event) {
        event.preventDefault();
        error = "";

        loading = true;
        const response = await Hardware.doorOpen();
        loading = false;

        if (!response.ok) {
            const json = await response.json();
            error = json.message;
            return;
        }

        open = false;
    }
</script>

<Modal form bind:open={open} size="sm">
    <div class="flex flex-col gap-5 align-center">
        <div class="flex gap-5 justify-center">
            <QuestionCircleSolid class="h-12 w-12"/>
            <div class="flex flex-col justify-center">
                <h1 class="text-lg font-bold">{m.modal_door_title()}</h1>
            </div>
        </div>

        <h3 class="text-lg text-center">
            {m.modal_door_content()}
        </h3>

        {#if error?.trim()}
            <div class="text-red-400 text-justify">{error}</div>
        {/if}

        <div class="flex gap-2 justify-center">
            <Button color="red" onclick={unlock}>
                {#if loading === true}
                    <Spinner size="5"/>
                {:else}
                    {m.modal_door_button_unlock()}
                {/if}
            </Button>
            <Tooltip>_Opens the door if connected to CSW WiFi_</Tooltip>
            <Button color="alternative" onclick={() => open = false}>
                {m.modal_door_button_cancel()}
            </Button>
        </div>
    </div>
</Modal>