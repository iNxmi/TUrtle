<script>
    import {Button, Heading, Hr, Input, Modal, MultiSelect, Select} from "flowbite-svelte";
    import PasswordInput from "$lib/components/PasswordInput.svelte";
    import {m} from "$lib/paraglide/messages.js";
    import request from "$lib/api/api.js";
    import {onMount} from "svelte";
    import {invalidateAll} from "$app/navigation";

    let {
        open = $bindable(false)
    } = $props();

    let roleItems = $state([]);
    onMount(async () => {
        const roles = await getRoles();
        roleItems = roles.map((role) => ({value: role.id, name: role.name}));
    });

    async function getRoles() {
        const response = await request("/api/roles");
        return await response.json();
    }

    let statusItems = [
        {value: "ARCHIVED", name: "Archived"},
        {value: "PENDING_VERIFICATION", name: "Pending Verification"},
        {value: "ACTIVE", name: "Active"},
        {value: "DELETED", name: "Deleted"},
        {value: "PENDING_APPROVAL", name: "Pending Approval"},
        {value: "REJECTED", name: "Rejected"},
        {value: "SUSPENDED", name: "Suspended"},
    ];

    let passwordRepeat = $state("");
    let input = $state({
        username: "",
        firstName: "",
        lastName: "",
        roleIds: [1],
        status: "PENDING_VERIFICATION",
        email: "",
        password: ""
    })

    async function submit(event) {
        event.preventDefault()

        const payload = $state.snapshot(input);
        const response = await request("/api/users", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(payload)
        });

        if (response.status !== 201)
            return;

        await invalidateAll();
        open = false;
    }
</script>

<Modal form bind:open={open} outsideclose={false}>
    <form onsubmit={submit} class="flex flex-col gap-5">
        <Heading tag="h3" class="text-center">
            {m.modal_manage_create_user_title()}
        </Heading>

        <Hr class="m-0 p-0"/>

        <div>
            <span>{m.modal_manage_create_user_label_username()}</span>
            <Input name="input_username" type="text" bind:value={input.username} required/>
        </div>

        <div class="flex gap-5">
            <div class="flex-1">
                <span>{m.modal_manage_create_user_label_first_name()}</span>
                <Input name="input_first_name" type="text" bind:value={input.firstName} required/>
            </div>
            <div class="flex-1">
                <span>{m.modal_manage_create_user_label_last_name()}</span>
                <Input name="input_last_name" type="text" bind:value={input.lastName} required/>
            </div>
        </div>

        <div>
            <span>{m.modal_manage_create_user_label_email()}</span>
            <Input name="input_email" type="email" bind:value={input.email} required/>
        </div>

        <div class="flex gap-5">
            <div class="flex-1">
                <span>{m.modal_manage_create_user_label_password()}</span>
                <PasswordInput name="input_password" type="password" bind:value={input.password} required/>
            </div>
            <div class="flex-1">
                <span>{m.modal_manage_create_user_label_password_repeat()}</span>
                <PasswordInput name="input_password_repeat" type="password" bind:value={passwordRepeat} required/>
            </div>
        </div>

        <div>
            <span>{m.modal_manage_create_user_label_roles()}</span>
            <MultiSelect name="input_roles" items={roleItems} bind:value={input.roleIds} required/>
        </div>

        <div>
            <span>{m.modal_manage_create_user_label_status()}</span>
            <Select name="input_status" items={statusItems} bind:value={input.status} required/>
        </div>

        <Button name="button_submit" type="submit">{m.modal_manage_create_user_button()}</Button>
    </form>
</Modal>