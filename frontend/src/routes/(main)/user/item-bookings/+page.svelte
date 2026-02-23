<script>
    import TableView from "$lib/components/TableView.svelte";
    import {m} from "$lib/paraglide/messages.js";
    import UserCreateItemBookingModal from "$lib/components/modal/UserCreateItemBookingModal.svelte";

    const {data} = $props();

    const columns = [
        {field: "id", label: m.user_item_bookings_label_id()},
        {field: "itemId", label: m.user_item_bookings_label_item_id()},
        {
            field: "start", label: m.user_item_bookings_label_start(),
            transform: (item) => new Date(item).toLocaleString()
        },
        {
            field: "end", label: m.user_item_bookings_label_end(),
            transform: (item) => new Date(item).toLocaleString()
        },
        {
            field: "updatedAt",
            label: m.user_item_bookings_label_updated_at(),
            transform: (item) => new Date(item).toLocaleString()
        },
        {
            field: "createdAt",
            label: m.user_item_bookings_label_created_at(),
            transform: (item) => new Date(item).toLocaleString()
        },
    ];

    const categories = $derived(data.categories.map(category => ({
        value: category.id,
        name: category.name
    })));

    const items = $derived(data.items.map(item => ({
        value: item.id,
        name: item.name
    })));

    let modal = $state(false);
</script>

<TableView columns={columns}
           contentPage={data.page}
           onCreate={() => modal = true}
/>

<UserCreateItemBookingModal bind:open={modal} {categories} {items}/>