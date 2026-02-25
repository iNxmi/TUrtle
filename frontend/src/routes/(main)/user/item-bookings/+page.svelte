<script>
    import TableView from "$lib/components/TableView.svelte";
    import {m} from "$lib/paraglide/messages.js";
    import CreateItemBookingModal from "$lib/components/modal/user/CreateItemBookingModal.svelte";

    const {data} = $props();

    const categories = $derived(data.categories.map(category => ({
        value: category.id,
        name: category.name
    })));

    const items = $derived(data.items.map(item => ({
        value: item.id,
        name: item.name
    })));

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
        }
    ];

    let modal = $state(false);
</script>

<TableView columns={columns}
           contentPage={data.page}
           onCreate={() => modal = true}
/>

{#if modal}
    <CreateItemBookingModal bind:open={modal} categoryList={data.categories} itemList={data.items}/>
{/if}