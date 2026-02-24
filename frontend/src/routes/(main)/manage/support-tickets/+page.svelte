<script>
    import {m} from '$lib/paraglide/messages.js';
    import TableView from "$lib/components/TableView.svelte"
    import {goto} from "$app/navigation";

    const {data} = $props();

    const columns = [
        {field: "id", label: m.manage_support_tickets_label_id(), enabled: false},
        {field: "status", label: m.manage_support_tickets_label_status()},
        {field: "urgency", label: m.manage_support_tickets_label_urgency()},
        {field: "category", label: m.manage_support_tickets_label_category()},
        {field: "subject", label: m.manage_support_tickets_label_subject()},
        {field: "email", label: m.manage_support_tickets_label_email()},
        {
            field: "updatedAt",
            label: m.manage_users_label_updated_at(),
            transform: (value) => new Date(value).toLocaleString(),
            enabled: false
        },
        {
            field: "createdAt",
            label: m.manage_users_label_created_at(),
            transform: (value) => new Date(value).toLocaleString(),
            enabled: false
        }
    ];
</script>

<TableView columns={columns}
           contentPage={data.page}
           onItemClicked={item => goto(`/manage/support-tickets/${item.id}`)}
/>
