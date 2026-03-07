<script>
    import TableView from "$lib/components/TableView.svelte";
    import {m} from "$lib/paraglide/messages.js";
    import CreateItemBookingModal from "$lib/components/modal/user/CreateItemBookingModal.svelte";
    import {goto} from "$app/navigation";
    import Card from "$lib/components/Card.svelte";
    import Calendar from "$lib/components/Calendar.svelte";
    import _ from "lodash";
    import {resolve} from "$app/paths";

    const {data} = $props();

    let itemMap = $derived(_.keyBy(data.items, "id"));

    const columns = [{
        field: "id", label: m.user_item_bookings_label_id(), enabled: false
    }, {
        field: "itemId", label: m.user_item_bookings_label_item(),
        transform: (itemId) => `${itemMap[itemId].name} (${itemId})`
    }, {
        field: "start", label: m.user_item_bookings_label_start(),
        transform: (iso) => new Date(iso).toLocaleString()
    }, {
        field: "end", label: m.user_item_bookings_label_end(),
        transform: (iso) => new Date(iso).toLocaleString()
    }, {
        field: "updatedAt",
        label: m.user_item_bookings_label_updated_at(),
        transform: (iso) => new Date(iso).toLocaleString(),
        enabled: false
    }, {
        field: "createdAt",
        label: m.user_item_bookings_label_created_at(),
        transform: (iso) => new Date(iso).toLocaleString(),
        enabled: false
    }];

    let bookings = $derived(data.bookings);
    let events = $derived(bookings.map((booking) => ({
        title: data.items.find((item) => item.id === booking.itemId).name,
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

<div class="flex-1 flex flex-col-reverse 2xl:flex-row gap-5">
    <Card class="flex-1">
        <Calendar bind:sources={sources} onEventClicked={(info) => goto(resolve(info.event.extendedProps.href))}/>
    </Card>

    <div class="flex-1 flex">
        <TableView columns={columns}
                   contentPage={data.page}
                   onCreate={() => modal = true}
                   onItemClicked={(item) => goto(resolve(`/user/item-bookings/${item.id}`))}
        />
    </div>
</div>

{#if modal}
    <CreateItemBookingModal bind:open={modal} categoryList={data.categories} itemList={data.items}/>
{/if}