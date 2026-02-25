<script>
    import TableView from "$lib/components/TableView.svelte";
    import {m} from "$lib/paraglide/messages.js";
    import CreateRoomBookingModal from "$lib/components/modal/user/CreateRoomBookingModal.svelte";
    import { TabItem} from 'flowbite-svelte';
    import Tab from "$lib/components/Tab.svelte";
	import { goto } from "$app/navigation";

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

<div>
    <Tab>
        <TabItem title="_Calendar View_" classes={{button: 'cursor-pointer'}} onclick={() => goto('/user/room-bookings')} />
        <TabItem open title="_All Roombookings_" classes={{button: 'cursor-pointer'}}>
            <TableView columns={columns}
                    contentPage={data.page}
                    onCreate={() => modal = true}
            />
        </TabItem>
    </Tab>
</div>

{#if modal}
    <CreateRoomBookingModal bind:open={modal} accessList={data.access}/>
{/if}
