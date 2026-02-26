<script>
    import TableView from "$lib/components/TableView.svelte";
    import {m} from "$lib/paraglide/messages.js";
    import CreateRoomBookingModal from "$lib/components/modal/user/CreateRoomBookingModal.svelte";
    import {goto} from "$app/navigation";
    import Calendar from "$lib/components/Calendar.svelte";
    import Card from "$lib/components/Card.svelte";

    const {data} = $props();

    const columns = [
        {
            field: "id", label: m.user_room_bookings_label_id(),
            enabled: false
        },
        {field: "title", label: m.user_room_bookings_label_title()},
        {
            field: "start", label: m.user_room_bookings_label_start(),
            transform: (item) => new Date(item).toLocaleString()
        },
        {
            field: "end", label: m.user_room_bookings_label_end(),
            transform: (item) => new Date(item).toLocaleString()
        },
        {
            field: "updatedAt", label: m.user_room_bookings_label_updated_at(),
            transform: (item) => new Date(item).toLocaleString(),
            enabled: false
        },
        {
            field: "createdAt", label: m.user_room_bookings_label_created_at(),
            transform: (item) => new Date(item).toLocaleString(),
            enabled: false
        }
    ];

    let bookings = $derived(data.bookings);
    let events = $derived(bookings.map((booking) => ({
        title: booking.title,
        start: booking.start,
        end: booking.end
    })));

    let sources = $derived([{
        events: events,
        color: "orange"
    }]);

    let modal = $state(false);
</script>

<div class="flex-1 flex flex-col xl:flex-row gap-5">
    <Card class="flex-1">
        <Calendar bind:sources={sources}/>
    </Card>

    <div class="flex-1 flex">
        <TableView columns={columns}
                   contentPage={data.page}
                   onCreate={() => modal = true}
                   onItemClicked={(item) => goto(`/user/room-bookings/${item.id}`)}
        />
    </div>
</div>

{#if modal}
    <CreateRoomBookingModal bind:open={modal} accessList={data.access}/>
{/if}
