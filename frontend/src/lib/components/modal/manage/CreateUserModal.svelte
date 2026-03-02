<script>
    import {Button, Heading, Hr, Input, Modal, MultiSelect, Select, Spinner} from "flowbite-svelte";
    import PasswordInput from "$lib/components/PasswordInput.svelte";
    import {m} from "$lib/paraglide/messages.js";
    import {Roles} from "$lib/api";
    import {onMount} from "svelte";
    import {invalidateAll} from "$app/navigation";
    import {Users} from "$lib/api";

    let {
        open = $bindable(false),
        statusList = []
    } = $props();

    let roleItems = $state([]);
    onMount(async () => {
        const roles = await getRoles();
        roleItems = roles.map((role) => ({value: role.id, name: role.name}));
    });

    async function getRoles() {
        const response = await Roles.getCollection();
        return await response.json();
    }

    let statusItems = $derived(statusList.map((status) => ({
        value: status,
        name: status
    })))
    let status = $state("PENDING_VERIFICATION");

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

    let loading = $state(false);
    let error = $state("");

    async function submit(event) {
        event.preventDefault()
        error = "";

        const payload = $state.snapshot(input);

        loading = true;
        const response = await Users.create(payload);
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
            {m.modal_manage_create_user_title()}
        </Heading>

        <Hr class="m-0 p-0"/>

        <div>
            <div>{m.modal_manage_create_user_label_username()}</div>
            <Input name="input_username" type="text" bind:value={input.username} required/>
        </div>

        <div class="flex gap-5">
            <div class="flex-1">
                <div>{m.modal_manage_create_user_label_first_name()}</div>
                <Input name="input_first_name" type="text" bind:value={input.firstName} required/>
            </div>
            <div class="flex-1">
                <div>{m.modal_manage_create_user_label_last_name()}</div>
                <Input name="input_last_name" type="text" bind:value={input.lastName} required/>
            </div>
        </div>

        <div>
            <div>{m.modal_manage_create_user_label_email()}</div>
            <Input name="input_email" type="email" bind:value={input.email} required/>
        </div>

        <div class="flex gap-5">
            <div class="flex-1">
                <div>{m.modal_manage_create_user_label_password()}</div>
                <PasswordInput name="input_password" type="password" bind:value={input.password} required/>
            </div>
            <div class="flex-1">
                <div>{m.modal_manage_create_user_label_password_repeat()}</div>
                <PasswordInput name="input_password_repeat" type="password" bind:value={passwordRepeat} required/>
            </div>
        </div>

        <div>
            <div>{m.modal_manage_create_user_label_roles()}</div>
            <MultiSelect name="input_roles" items={roleItems} bind:value={input.roleIds} required/>
        </div>

        <div>
            <div>{m.modal_manage_create_user_label_status()}</div>
            <Select name="input_status" items={statusItems} bind:value={input.status} required/>
        </div>

        {#if error?.trim()}
            <div class="text-red-400 text-justify">{error}</div>
        {/if}

        <Button name="button_submit" type="submit">
            {#if loading === true}
                <Spinner size="5"/>
            {:else}
                {m.modal_manage_create_user_button()}
            {/if}
        </Button>
    </form>
</Modal>