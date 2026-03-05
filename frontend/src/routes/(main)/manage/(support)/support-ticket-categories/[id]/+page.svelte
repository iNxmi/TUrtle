<script>
    import {Input} from "flowbite-svelte";
    import {m} from '$lib/paraglide/messages.js';
    import EntityPage from "$lib/components/EntityPage.svelte";
    import {SupportTicketCategories} from "$lib/api";

    let {data} = $props();
    let category = $derived(data.category);

    const items = $derived([{
        label: m.manage_support_ticket_categories_label_id(),
        field: "id",
        component: Input,
        props: {
            value: category.id
        }
    }, {
        label: m.manage_support_ticket_categories_label_name(),
        field: "name",
        editable: true,
        component: Input,
        props: {
            value: category.name
        }
    }, [{
        label: m.manage_support_ticket_categories_label_created_at(),
        field: "createdAt",
        component: Input,
        props: {
            value: category.createdAt
        }
    }, {
        label: m.manage_support_ticket_categories_label_updated_at(),
        field: "updatedAt",
        component: Input,
        props: {
            value: category.updatedAt
        }
    }]]);
</script>

<EntityPage items={items} onPatch={(payload) => SupportTicketCategories.patch(category.id, payload)}/>