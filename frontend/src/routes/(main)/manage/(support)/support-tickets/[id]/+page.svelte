<script>
    import {Input, Select, Textarea} from "flowbite-svelte";
    import {m} from '$lib/paraglide/messages.js';
    import EntityPage from "$lib/components/EntityPage.svelte";
    import {SupportTickets} from "$lib/api";

    let {data} = $props();
    let entity = $derived(data.entity);

    const statusItems = $derived(data.statuses.map((status) => ({
        value: status,
        name: status
    })));

    const urgencyItems = $derived(data.urgencies.map((urgency) => ({
        value: urgency.id,
        name: urgency.name
    })));

    const categoryItems = $derived(data.categories.map((category) => ({
        value: category.id,
        name: category.name
    })));

    const items = $derived([{
        label: m.manage_support_tickets_label_id(),
        field: "id",
        component: Input,
        props: {
            value: entity.id
        }
    }, [{
        label: m.manage_support_tickets_label_urgency(),
        field: "urgencyId",
        component: Select,
        props: {
            value: entity.urgencyId,
            items: urgencyItems
        }
    }, {
        label: m.manage_support_tickets_label_category(),
        field: "categoryId",
        component: Select,
        props: {
            value: entity.categoryId,
            items: categoryItems
        }
    }], {
        label: m.manage_support_tickets_label_email(),
        field: "email",
        component: Input,
        props: {
            value: entity.email
        }
    }, {
        label: m.manage_support_tickets_label_subject(),
        field: "subject",
        component: Input,
        props: {
            value: entity.subject
        }
    }, {
        label: m.manage_support_tickets_label_content(),
        field: "content",
        component: Textarea,
        props: {
            value: entity.content
        }
    }, {
        label: m.manage_support_tickets_label_status(),
        field: "status",
        editable: true,
        component: Select,
        props: {
            value: entity.status,
            items: statusItems
        }
    }, [{
        label: m.manage_support_tickets_label_created_at(),
        field: "createdAt",
        component: Input,
        props: {
            value: entity.createdAt
        }
    }, {
        label: m.manage_support_tickets_label_updated_at(),
        field: "updatedAt",
        component: Input,
        props: {
            value: entity.updatedAt
        }
    }]]);
</script>

<EntityPage items={items}
            onPatch={(payload) => SupportTickets.patch(entity.id, payload)}
            onDelete={() => SupportTickets.delete(entity.id)}
/>