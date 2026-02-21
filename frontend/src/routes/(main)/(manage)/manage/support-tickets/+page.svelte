<script>
    import {m} from '$lib/paraglide/messages.js';
    import TableView from "$lib/components/TableView.svelte"

    const {data} = $props();

    const headers = [
        {id: "id", display: m.manage_support_tickets_label_id()},
        {id: "urgency", display: m.manage_support_tickets_label_urgency()},
        {id: "category", display: m.manage_support_tickets_label_category()},
        {id: "subject", display: m.manage_support_tickets_label_subject()},
        {id: "email", display: m.manage_support_tickets_label_email()},
        {id: "createdAt", display: m.manage_support_tickets_label_created_at()}
    ]
</script>

<TableView
        endpoint="/manage/support-tickets"
        headers={headers}
        contentPage={data.page}
/>
