<script>
    import TableView from "$lib/components/TableView.svelte";
    import {m} from "$lib/paraglide/messages.js";
    import CreateRoomBookingModal from "$lib/components/modal/user/CreateRoomBookingModal.svelte";

    const {data} = $props();

    const columns = [
        {field: "id", label: m.user_room_bookings_label_id()},
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
            transform: (item) => new Date(item).toLocaleString()
        },
        {
            field: "createdAt", label: m.user_room_bookings_label_created_at(),
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
    <CreateRoomBookingModal bind:open={modal} accessList={data.access}/>
{/if}
