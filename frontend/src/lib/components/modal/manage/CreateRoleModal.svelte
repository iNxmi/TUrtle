<script>
    import {Button, Heading, Hr, Input, Modal, Select, MultiSelect, Spinner} from "flowbite-svelte";
    import {m} from "$lib/paraglide/messages.js";
    import {Roles} from "$lib/api";
    import {invalidateAll} from "$app/navigation";

    let {
        open = $bindable(false),
        typeList = [],
        permissionList = []
    } = $props();

    let typeItems = $derived([
        {value: "", name: "-"},
        ...typeList.map((type) => ({
            value: type,
            name: type
        }))
    ]);

    let permissionItems = $derived(permissionList.map((permission) => ({
        value: permission,
        name: permission
    })));

    let name = $state("");
    let permissions = $state([]);
    let type = $state("");

    let loading = $state(false);
    let error = $state("");

    async function submit(event) {
        event.preventDefault();
        error = "";

        const payload = {
            name: name,
            permissions: $state.snapshot(permissions),
            type: type || null
        };

        loading = true;
        const response = await Roles.create(payload);
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
    <form onsubmit={submit} class="flex flex-col gap-5">
        <Heading tag="h3" class="text-center">
            {m.modal_manage_create_role_title()}
        </Heading>

        <Hr class="m-0 p-0"/>

        <div>
            <div>{m.modal_manage_create_role_label_name()}</div>
            <Input bind:value={name} required/>
        </div>

        <div>
            <div>{m.modal_manage_create_role_label_permissions()}</div>
            <MultiSelect items={permissionItems} bind:value={permissions} required/>
        </div>

        <div>
            <div>{m.modal_manage_create_role_label_type()}</div>
            <Select items={typeItems} bind:value={type}/>
        </div>

        {#if error?.trim()}
            <div class="text-red-400 text-justify">{error}</div>
        {/if}

        <Button type="submit">
            {#if loading === true}
                <Spinner size="5"/>
            {:else}
                {m.modal_manage_create_role_button()}
            {/if}
        </Button>
    </form>
</Modal>
