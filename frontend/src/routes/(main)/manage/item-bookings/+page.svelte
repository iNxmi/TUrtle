<script>
    import {m} from '$lib/paraglide/messages.js';
    import TableView from "$lib/components/TableView.svelte"

    const {data} = $props();

    const columns = [
        {field: "id", label: m.manage_item_bookings_label_id(), enabled: false},
        {field: "itemId", label: m.manage_item_bookings_label_item_id()},
        {field: "userId", label: m.manage_item_bookings_label_user_id()},
        {field: "start", label: m.manage_item_bookings_label_start()},
        {field: "end", label: m.manage_item_bookings_label_end()},
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
/>