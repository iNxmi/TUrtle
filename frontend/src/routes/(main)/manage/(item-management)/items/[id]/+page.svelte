<script>
    import {Input, Select, Textarea} from "flowbite-svelte";
    import {m} from '$lib/paraglide/messages.js';
    import {Items} from "$lib/api";
    import EntityPage from "$lib/components/EntityPage.svelte";

    let {data} = $props();
    let entity = $derived(data.entity);

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
            value: entity.id
        }
    }, {
        label: m.manage_items_label_name(),
        field: "name",
        editable: true,
        component: Input,
        props: {
            value: entity.name
        }
    }, {
        label: m.manage_items_label_category(),
        field: "categoryId",
        editable: true,
        component: Select,
        props: {
            value: entity.categoryId,
            items: categoryItems
        }
    }, {
        label: m.manage_items_label_locker(),
        field: "lockerId",
        editable: true,
        component: Select,
        props: {
            value: entity.lockerId,
            items: lockerItems
        }
    }, {
        label: m.manage_items_label_description(),
        field: "description",
        editable: true,
        component: Textarea,
        props: {
            value: entity.description
        }
    }, {
        label: m.manage_items_label_needs_confirmation(),
        field: "needsConfirmation",
        editable: true,
        component: Input,
        props: {
            value: entity.needsConfirmation
        }
    }, {
        label: m.manage_items_label_acquired_at(),
        field: "acquiredAt",
        editable: true,
        component: Input,
        props: {
            value: entity.acquiredAt
        }
    }, [{
        label: m.manage_items_label_created_at(),
        field: "createdAt",
        component: Input,
        props: {
            value: entity.createdAt
        }
    }, {
        label: m.manage_items_label_updated_at(),
        field: "updatedAt",
        component: Input,
        props: {
            value: entity.updatedAt
        }
    }]]);
</script>

<EntityPage items={items}
            onPatch={(payload) => Items.patch(entity.id, payload)}
            onDelete={() => Items.delete(entity.id)}
/>