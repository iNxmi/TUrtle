<script>
    import {m} from '$lib/paraglide/messages.js';
    import TableView from "$lib/components/TableView.svelte"
    import {goto} from "$app/navigation";

    const {data} = $props();

    const columns = [
        {field: "id", label: m.manage_support_tickets_label_id(), enabled: false},
        {field: "status", label: m.manage_support_tickets_label_status()},
        {
            field: "urgencyId",
            label: m.manage_support_tickets_label_urgency(),
            transform: (item) => data.urgencies.find((a) => a.id === item).name
        },
        {
            field: "categoryId",
            label: m.manage_support_tickets_label_category(),
            transform: (item) => data.categories.find((a) => a.id === item).name
        },
        {field: "subject", label: m.manage_support_tickets_label_subject()},
        {field: "email", label: m.manage_support_tickets_label_email()},
        {
            field: "updatedAt",
            label: m.manage_support_tickets_label_updated_at(),
            transform: (value) => new Date(value).toLocaleString(),
            enabled: false
        },
        {
            field: "createdAt",
            label: m.manage_support_tickets_label_created_at(),
            transform: (value) => new Date(value).toLocaleString(),
            enabled: false
        }
    ];
</script>

<TableView columns={columns}
           contentPage={data.page}
           onItemClicked={item => goto(`/manage/support-tickets/${item.id}`)}
/>
