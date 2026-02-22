<script>
    import {Button, Modal} from "flowbite-svelte";
    import {QuestionCircleSolid} from "flowbite-svelte-icons";
    import request from "$lib/api/api.js";
    import {invalidateAll} from "$app/navigation";
    import {m} from "$lib/paraglide/messages";

    let {
        open = $bindable(false)
    } = $props()

    async function logout(event) {
        event.preventDefault()

        const response = await request("/auth/logout", {method: "POST"})
        if (response.status !== 204)
            return;

        await invalidateAll();
        open = false;
    }

</script>

<Modal form bind:open={open}>
    <div class="flex flex-col gap-5 align-center">
        <div class="flex gap-5 justify-center">
            <QuestionCircleSolid class="h-12 w-12"/>
            <div class="flex flex-col justify-center">
                <h1 class="text-lg font-bold">{m.modal_logout_title()}</h1>
            </div>
        </div>

        <h3 class="text-lg text-center">
            {m.modal_logout_content()}
        </h3>

        <div class="flex gap-2 justify-center">
            <Button color="red" onclick={logout}>
                {m.modal_logout_button_logout()}
            </Button>
            <Button color="alternative" onclick={() => open = false}>
                {m.modal_logout_button_cancel()}
            </Button>
        </div>
    </div>
</Modal>