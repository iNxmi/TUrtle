<script>
    import {m} from '$lib/paraglide/messages.js';
    import TableView from "$lib/components/TableView.svelte"

    const {data} = $props();

    const columns = [
        {field: "id", label: m.manage_support_tickets_label_id()},
        {field: "urgency", label: m.manage_support_tickets_label_urgency()},
        {field: "category", label: m.manage_support_tickets_label_category()},
        {field: "subject", label: m.manage_support_tickets_label_subject()},
        {field: "email", label: m.manage_support_tickets_label_email()},
        {
            field: "createdAt",
            label: m.manage_support_tickets_label_created_at(),
            transform: (item) => new Date(item).toLocaleString()
        }
    ];
</script>

<TableView columns={columns}
           contentPage={data.page}
/>
