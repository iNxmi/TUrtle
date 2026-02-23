<script>
    import {m} from '$lib/paraglide/messages.js';
    import TableView from "$lib/components/TableView.svelte"

    const {data} = $props();

    const columns = [
        {field: "id",           label: m.manage_item_bookings_label_id()},
        {field: "createdAt",    label: m.manage_item_bookings_label_created_at(), transform: (item) => new Date(item).toLocaleString()}
    ];
</script>

<TableView columns={columns}
           contentPage={data.page}
/>