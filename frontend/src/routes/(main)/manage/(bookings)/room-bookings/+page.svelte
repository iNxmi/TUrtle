<script>
    import {m} from '$lib/paraglide/messages.js';
    import TableView from "$lib/components/TableView.svelte"
    import CreateRoomBookingModal from "$lib/components/modal/manage/CreateRoomBookingModal.svelte";
    import {goto} from "$app/navigation";

    const {data} = $props();

    const columns = [
        {field: "id", label: m.manage_room_bookings_label_id(), enabled: false},
        {field: "userId", label: m.manage_room_bookings_label_user_id()},
        {field: "title", label: m.manage_room_bookings_label_title()},
        {field: "status", label: m.manage_room_bookings_label_status()},
        {field: "accessibility", label: m.manage_room_bookings_label_accessibility()},
        {
            field: "start", label: m.manage_room_bookings_label_start(),
            transform: (value) => new Date(value).toLocaleString()
        },
        {
            field: "end", label: m.manage_room_bookings_label_end(),
            transform: (value) => new Date(value).toLocaleString()
        },
        {
            field: "updatedAt",
            label: m.manage_room_bookings_label_updated_at(),
            transform: (value) => new Date(value).toLocaleString(),
            enabled: false
        },
        {
            field: "createdAt",
            label: m.manage_room_bookings_label_created_at(),
            transform: (value) => new Date(value).toLocaleString(),
            enabled: false
        }
    ];

    let modal = $state(false);
</script>

<TableView columns={columns}
           contentPage={data.page}
           onCreate={() => modal = true}
           onItemClicked={(item) => goto(`/manage/room-bookings/${item.id}`)}
/>

<CreateRoomBookingModal bind:open={modal} accessList={data.access} statusList={data.status}/>