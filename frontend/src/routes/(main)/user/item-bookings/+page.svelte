<script>
    import TableView from "$lib/components/TableView.svelte";
    import {m} from "$lib/paraglide/messages.js";
    import CreateItemBookingModal from "$lib/components/modal/CreateItemBookingModal.svelte";

    const {data} = $props();

    const headers = [
        {id: "id", display: m.user_item_bookings_label_id()},
        {id: "itemId", display: m.user_item_bookings_label_item_id()},
        {id: "start", display: m.user_item_bookings_label_start()},
        {id: "end", display: m.user_item_bookings_label_end()},
        {id: "updatedAt", display: m.user_item_bookings_label_updated_at()},
        {id: "createdAt", display: m.user_item_bookings_label_created_at()},
    ];

    const categories = [
        {name: "0", value: "0"},
        {name: "1", value: "1"},
        {name: "2", value: "2"}
    ];

    const items = [
        {name: "0", value: "0"},
        {name: "1", value: "1"},
        {name: "2", value: "2"}
    ];

    let modal = $state(false);
</script>

<TableView endpoint="/user/item-bookings"
           headers={headers}
           contentPage={data.page}
           onCreate={() => modal = true}
/>

<CreateItemBookingModal bind:open={modal} {categories} {items}/>