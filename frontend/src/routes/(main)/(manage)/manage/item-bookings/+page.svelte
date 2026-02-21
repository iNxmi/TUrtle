<script>
    import {m} from '$lib/paraglide/messages.js';
    import TableView from "$lib/components/TableView.svelte"

    const {data} = $props();

    const headers = [
        {id: "id", display: m.manage_item_bookings_label_id()},
        {id: "createdAt", display: m.manage_item_bookings_label_created_at()}
    ];
</script>

<TableView endpoint="/manage/item-bookings"
           headers={headers}
           contentPage={data.page}
/>