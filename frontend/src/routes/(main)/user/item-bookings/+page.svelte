<script>
    import TableView from "$lib/components/TableView.svelte";
    import {m} from "$lib/paraglide/messages.js";
    import CreateItemBookingModal from "$lib/components/modal/user/CreateItemBookingModal.svelte";
    import {goto} from "$app/navigation";
    import Card from "$lib/components/Card.svelte";
    import Calendar from "$lib/components/Calendar.svelte";
    import _ from "lodash";

    const {data} = $props();

    const categories = $derived(data.categories.map(category => ({
        value: category.id,
        name: category.name
    })));

    let itemMap = $derived(_.keyBy(data.items, "id"));
    const items = $derived(data.items.map(item => ({
        value: item.id,
        name: item.name
    })));

    const columns = [
        {field: "id", label: m.user_item_bookings_label_id(), enabled: false},
        {
            field: "itemId", label: m.user_item_bookings_label_item(),
            transform: (itemId) => `${itemMap[itemId].name} (${itemId})`
        },
        {
            field: "start", label: m.user_item_bookings_label_start(),
            transform: (iso) => new Date(iso).toLocaleString()
        },
        {
            field: "end", label: m.user_item_bookings_label_end(),
            transform: (iso) => new Date(iso).toLocaleString()
        },
        {
            field: "updatedAt",
            label: m.user_item_bookings_label_updated_at(),
            transform: (iso) => new Date(iso).toLocaleString(),
            enabled: false
        },
        {
            field: "createdAt",
            label: m.user_item_bookings_label_created_at(),
            transform: (iso) => new Date(iso).toLocaleString(),
            enabled: false
        }
    ];

    let bookings = $derived(data.bookings);
    let events = $derived(bookings.map((booking) => ({
        title: booking.itemId,
        start: booking.start,
        end: booking.end,
        href: `/user/item-bookings/${booking.id}`
    })));

    let sources = $derived([{
        events: events,
        color: "orange"
    }]);

    let modal = $state(false);
</script>

<div class="flex-1 flex flex-col xl:flex-row gap-5">
    <Card class="flex-1">
        <Calendar bind:sources={sources} onEventClicked={(info) => goto(info.event.extendedProps.href)}/>
    </Card>

    <div class="flex-1 flex">
        <TableView columns={columns}
                   contentPage={data.page}
                   onCreate={() => modal = true}
                   onItemClicked={(item) => goto(`/user/item-bookings/${item.id}`)}
        />
    </div>
</div>

{#if modal}
    <CreateItemBookingModal bind:open={modal} categoryList={data.categories} itemList={data.items}/>
{/if}