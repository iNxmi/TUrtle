<script>
    import {m} from '$lib/paraglide/messages.js';
    import TableView from "$lib/components/TableView.svelte"

    const {data} = $props();

    const headers = [
        {id: "id", display: m.admin_support__id_label()},
        {id: "urgency", display: m.admin_support__urgency_label()},
        {id: "category", display: m.admin_support__category_label()},
        {id: "subject", display: m.admin_support__subject_label()},
        {id: "email", display: m.admin_support__email_label()},
        {id: "createdAt", display: m.admin_support__created_at_label()}
    ]
</script>

<TableView
        endpoint="/manage/support-tickets"
        headers={headers}
        contentPage={data.page}
/>
