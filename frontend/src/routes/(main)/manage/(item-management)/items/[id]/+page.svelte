<script>
    import {Input, Select, Textarea} from "flowbite-svelte";
    import {m} from '$lib/paraglide/messages.js';
    import {Items} from "$lib/api";
    import EntityPage from "$lib/components/EntityPage.svelte";

    let {data} = $props();
    let item = $derived(data.item);

    const categoryItems = $derived(data.categories.map((category) => ({
        value: category.id,
        name: category.name
    })));

    const lockerItems = $derived(data.lockers.map((locker) => ({
        value: locker.id,
        name: locker.name
    })))

    const items = $derived([{
        label: m.manage_items_label_id(),
        field: "id",
        component: Input,
        props: {
            value: item.id
        }
    }, {
        label: m.manage_items_label_name(),
        field: "name",
        editable: true,
        component: Input,
        props: {
            value: item.name
        }
    }, {
        label: m.manage_items_label_category(),
        field: "categoryId",
        editable: true,
        component: Select,
        props: {
            value: item.categoryId,
            items: categoryItems
        }
    }, {
        label: m.manage_items_label_locker(),
        field: "lockerId",
        editable: true,
        component: Select,
        props: {
            value: item.lockerId,
            items: lockerItems
        }
    }, {
        label: m.manage_items_label_description(),
        field: "description",
        editable: true,
        component: Textarea,
        props: {
            value: item.description
        }
    }, {
        label: m.manage_items_label_needs_confirmation(),
        field: "needsConfirmation",
        editable: true,
        component: Input,
        props: {
            value: item.needsConfirmation
        }
    }, {
        label: m.manage_items_label_acquired_at(),
        field: "acquiredAt",
        editable: true,
        component: Input,
        props: {
            value: item.acquiredAt
        }
    }, [{
        label: m.manage_items_label_created_at(),
        field: "createdAt",
        component: Input,
        props: {
            value: item.createdAt
        }
    }, {
        label: m.manage_items_label_updated_at(),
        field: "updatedAt",
        component: Input,
        props: {
            value: item.updatedAt
        }
    }]]);
</script>

<EntityPage items={items} onPatch={(payload) => Items.patch(item.id, payload)}/>