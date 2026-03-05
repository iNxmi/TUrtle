<script>
    import {m} from "$lib/paraglide/messages.js";
    import {Input, MultiSelect} from "flowbite-svelte";
    import EntityPage from "$lib/components/EntityPage.svelte";
    import {Roles} from "$lib/api";

    let {data} = $props();
    let role = $derived(data.role);

    const permissionItems = $derived(data.permissions.map((permission) => ({
        value: permission,
        name: permission
    })));

    const items = $derived([{
        label: m.manage_roles_label_id(),
        field: "id",
        component: Input,
        props: {
            value: role.id
        }
    }, {
        label: m.manage_roles_label_name(),
        field: "name",
        editable: true,
        component: Input,
        props: {
            value: role.name
        }
    }, {
        label: m.manage_roles_label_type(),
        field: "type",
        editable: true,
        component: Input,
        props: {
            value: role.type
        }
    }, {
        label: m.manage_roles_label_permissions(),
        field: "permissions",
        editable: true,
        component: MultiSelect,
        props: {
            value: role.permissions,
            items: permissionItems
        }
    }, [{
        label: m.manage_roles_label_created_at(),
        field: "createdAt",
        component: Input,
        props: {
            value: role.createdAt
        }
    }, {
        label: m.manage_roles_label_updated_at(),
        field: "updatedAt",
        component: Input,
        props: {
            value: role.updatedAt
        }
    }]]);
</script>

<EntityPage items={items} onPatch={(payload) => Roles.patch(role.id, payload)}/>