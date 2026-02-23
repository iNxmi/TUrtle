<script>
    import TableView from "$lib/components/TableView.svelte";
    import {m} from "$lib/paraglide/messages.js";
    import UserCreateRoomBookingModal from "$lib/components/modal/UserCreateRoomBookingModal.svelte";

    const {data} = $props();

    const headers = [
        {id: "id", display: m.user_room_bookings_label_id()},
        {id: "title", display: m.user_room_bookings_label_title()},
        {id: "start", display: m.user_room_bookings_label_start()},
        {id: "end", display: m.user_room_bookings_label_end()},
        {id: "updatedAt", display: m.user_room_bookings_label_updated_at()},
        {id: "createdAt", display: m.user_room_bookings_label_created_at()}
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

<TableView endpoint="/user/room-bookings"
           headers={headers}
           contentPage={data.page}
           onCreate={() => modal = true}
/>

{#if modal}
    <UserCreateRoomBookingModal bind:open={modal}/>
{/if}
