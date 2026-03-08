<script>
    import {Input, MultiSelect, Select} from "flowbite-svelte";
    import {m} from '$lib/paraglide/messages.js';
    import EntityPage from "$lib/components/EntityPage.svelte";
    import {Users} from "$lib/api";

    let {data} = $props();
    let user = $derived(data.user);

    let roleItems = $derived(data.roles.map((role) => ({
        value: role.id,
        name: role.name
    })));

    let statusItems = $derived(data.statuses.map((status) => ({
        value: status,
        name: status
    })));

    const items = $derived([{
        label: m.manage_users_label_id(),
        field: "id",
        component: Input,
        props: {
            value: user.id
        }
    }, {
        label: m.manage_users_label_username(),
        field: "username",
        editable: true,
        component: Input,
        props: {
            value: user.username
        }
    }, [{
        label: m.manage_users_label_first_name(),
        field: "firstName",
        editable: true,
        component: Input,
        props: {
            value: user.firstName
        }
    }, {
        label: m.manage_users_label_last_name(),
        field: "lastName",
        editable: true,
        component: Input,
        props: {
            value: user.lastName
        }
    },], {
        label: m.manage_users_label_email(),
        field: "email",
        editable: true,
        component: Input,
        props: {
            value: user.email,
            type: "email"
        }
    }, {
        label: m.manage_users_label_emojis(),
        field: "emojis",
        editable: true,
        component: Input,
        props: {
            value: user.emojis
        }
    }, {
        label: m.manage_users_label_roles(),
        field: "roleIds",
        editable: true,
        component: MultiSelect,
        props: {
            value: user.roleIds,
            items: roleItems
        }
    }, {
        label: m.manage_users_label_status(),
        field: "status",
        editable: true,
        component: Select,
        props: {
            value: user.status,
            items: statusItems
        }
    }, [{
        label: m.manage_users_label_created_at(),
        field: "createdAt",
        component: Input,
        props: {
            value: user.createdAt
        }
    }, {
        label: m.manage_users_label_updated_at(),
        field: "updatedAt",
        component: Input,
        props: {
            value: user.updatedAt
        }
    }]]);
</script>

<EntityPage items={items} onPatch={(payload) => Users.patch(user.id, payload)}/>