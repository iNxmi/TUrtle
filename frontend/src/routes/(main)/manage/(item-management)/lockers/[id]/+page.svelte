<script>
    import {m} from "$lib/paraglide/messages.js";
    import {Input} from "flowbite-svelte";
    import EntityPage from "$lib/components/EntityPage.svelte";
    import {Lockers} from "$lib/api";

    let {data} = $props();
    let locker = $derived(data.locker);

    const items = $derived([{
        label: m.manage_lockers_label_id(),
        field: "id",
        component: Input,
        props: {
            value: locker.id
        }
    }, {
        label: m.manage_lockers_label_name(),
        field: "name",
        editable: true,
        component: Input,
        props: {
            value: locker.name
        }
    }, {
        label: m.manage_lockers_label_index(),
        field: "index",
        editable: true,
        component: Input,
        props: {
            value: locker.index
        }
    }, [{
        label: m.manage_lockers_label_software_unlockable(),
        field: "softwareUnlockable",
        editable: true,
        component: Input,
        props: {
            value: locker.softwareUnlockable
        }
    }, {
        label: m.manage_lockers_label_locked(),
        field: "locked",
        editable: true,
        component: Input,
        props: {
            value: locker.locked
        }
    }], [{
        label: m.manage_item_categories_label_created_at(),
        field: "createdAt",
        component: Input,
        props: {
            value: locker.createdAt
        }
    }, {
        label: m.manage_item_categories_label_updated_at(),
        field: "updatedAt",
        component: Input,
        props: {
            value: locker.updatedAt
        }
    }]]);
</script>

<EntityPage items={items} onPatch={(payload) => Lockers.patch(locker.id, payload)}/>