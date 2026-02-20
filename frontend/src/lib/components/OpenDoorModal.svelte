<script>
    import {m} from "$lib/paraglide/messages.js";
    import request from "$lib/api/api";
    import {Modal} from "flowbite-svelte";
    import {QuestionCircleSolid} from "flowbite-svelte-icons";
    import {
        Button,
    } from "flowbite-svelte";

    async function handleDoorModal(action) {
        if (action === "yes") {
            request("/hardware/door", {method: "POST"});
        }
    }

    let {
        open = false
    } = $props();

</script>

<Modal form bind:open={open} onaction={({action}) => handleDoorModal(action) }>

    <div class="flex flex-col gap-5 align-center">
        <div class="flex gap-5 justify-center">
            <QuestionCircleSolid class="h-12 w-12"/>
            <div class="flex flex-col justify-center">
                <h1 class="text-lg font-bold">{m.modal_door_title()}</h1>
            </div>
        </div>
        <h3 class="text-lg font-normal">{m.modal_door_content()}</h3>
        <div class="flex gap-2 justify-center">
            <Button type="submit" value="yes">{m.modal_door_button_unlock()}</Button>
            <Button type="submit" value="no">{m.modal_door_button_cancel()}</Button>
        </div>
    </div>
</Modal>