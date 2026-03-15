<script>
    import {m} from "$lib/paraglide/messages.js";
    import {Input, MultiSelect} from "flowbite-svelte";
    import EntityPage from "$lib/components/EntityPage.svelte";
    import {Roles, Users} from "$lib/api";

    let {data} = $props();
    let entity = $derived(data.entity);

    const permissionItems = $derived(data.permissions.map((permission) => ({
        value: permission,
        name: permission
    })));

    const items = $derived([{
        label: m.manage_roles_label_id(),
        field: "id",
        component: Input,
        props: {
            value: entity.id
        }
    }, {
        label: m.manage_roles_label_name(),
        field: "name",
        editable: true,
        component: Input,
        props: {
            value: entity.name
        }
    }, {
        label: m.manage_roles_label_type(),
        field: "type",
        editable: true,
        component: Input,
        props: {
            value: entity.type
        }
    }, {
        label: m.manage_roles_label_permissions(),
        field: "permissions",
        editable: true,
        component: MultiSelect,
        props: {
            value: entity.permissions,
            items: permissionItems
        }
    }, [{
        label: m.manage_roles_label_created_at(),
        field: "createdAt",
        component: Input,
        props: {
            value: entity.createdAt
        }
    }, {
        label: m.manage_roles_label_updated_at(),
        field: "updatedAt",
        component: Input,
        props: {
            value: entity.updatedAt
        }
    }]]);
</script>

<EntityPage items={items}
            onPatch={(payload) => Roles.patch(entity.id, payload)}
            onDelete={() => Roles.delete(entity.id)}
/>