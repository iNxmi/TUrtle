<script>
    import {Input} from "flowbite-svelte";
    import {m} from '$lib/paraglide/messages.js';
    import EntityPage from "$lib/components/EntityPage.svelte";
    import {SupportTicketUrgencies} from "$lib/api";

    let {data} = $props();
    let urgency = $derived(data.urgency);

    const items = $derived([{
        label: m.manage_support_ticket_urgencies_label_id(),
        field: "id",
        component: Input,
        props: {
            value: urgency.id
        }
    }, {
        label: m.manage_support_ticket_urgencies_label_name(),
        field: "name",
        editable: true,
        component: Input,
        props: {
            value: urgency.name
        }
    }, [{
        label: m.manage_support_ticket_urgencies_label_created_at(),
        field: "createdAt",
        component: Input,
        props: {
            value: urgency.createdAt
        }
    }, {
        label: m.manage_support_ticket_urgencies_label_updated_at(),
        field: "updatedAt",
        component: Input,
        props: {
            value: urgency.updatedAt
        }
    }]]);
</script>

<EntityPage items={items} onPatch={(payload) => SupportTicketUrgencies.patch(urgency.id, payload)}/>