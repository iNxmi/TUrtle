<script>
    import {Button, Heading, Hr, Modal} from "flowbite-svelte";
    import request from "$lib/api/api.js";
    import {goto, invalidateAll} from "$app/navigation";
    import {m} from "$lib/paraglide/messages.js";

    let {
        open = $bindable(false)
    } = $props()

    async function logout(event) {
        event.preventDefault()

        const response = await request("/auth/logout", {method: "POST"})
        if (response.status !== 204)
            return;

        await invalidateAll();
        await goto("/");
        open = false;
    }
</script>

<Modal form bind:open={open} size="xs">
    <div class="flex flex-col gap-5 align-center">
        <Heading tag="h3" class="text-center">
            {m.modal_logout_title()}
        </Heading>

        <Hr class="m-0 p-0"/>

        <h3 class="text-lg text-center">
            {m.modal_logout_content()}
        </h3>

        <div class="flex gap-2 justify-center">
            <Button name="button_logout" color="red" onclick={logout}>
                {m.modal_logout_button_logout()}
            </Button>
            <Button name="button_cancel" color="alternative" onclick={() => open = false}>
                {m.modal_logout_button_cancel()}
            </Button>
        </div>
    </div>
</Modal>