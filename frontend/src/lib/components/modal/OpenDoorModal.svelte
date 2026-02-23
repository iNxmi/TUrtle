<script>
    import {m} from "$lib/paraglide/messages.js";
    import request from "$lib/api/api.js";
    import {Button, Modal} from "flowbite-svelte";
    import {QuestionCircleSolid} from "flowbite-svelte-icons";

    let {
        open = $bindable(false)
    } = $props();

    async function unlock(event) {
        event.preventDefault();

        const response = await request("/api/hardware/door/open", {method: "POST"});
        if (!response.ok)
            return;

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

        <div class="flex gap-2 justify-center">
            <Button color="red" onclick={unlock}>
                {m.modal_door_button_unlock()}
            </Button>
            <Button color="alternative" onclick={() => open = false}>
                {m.modal_door_button_cancel()}
            </Button>
        </div>
    </div>
</Modal>