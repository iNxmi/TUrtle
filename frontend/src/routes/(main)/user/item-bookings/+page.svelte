<script>
    import TableView from "$lib/components/TableView.svelte";
    import {m} from "$lib/paraglide/messages.js";
    import CreateItemBookingModal from "$lib/components/modal/user/CreateItemBookingModal.svelte";
    import DevicebookingTable from "$lib/components/DevicebookingTable.svelte";
	import { DatabaseOutline } from "flowbite-svelte-icons";
    import { Button, Tabs, TabItem } from 'flowbite-svelte';
	import { goto } from "$app/navigation";

    let {data} = $props();

    let reservations = $derived(data.itemBookings);
    let items = $derived(data.items);
    const categories = $derived(data.categories.map(category => ({
        value: category.id,
        name: category.name
    })));

    let newBookingItems = $derived(data.items.map((item) => ({
        value: item.id,
        name: item.name
    }))
    );

    /* const items = $derived(data.items.map(item => ({
        value: item.id,
        name: item.name
    }))); */

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

<div>
    <Tabs classes={{content: "flex flex-col gap-3 min-h-[calc(100svh-300px)]"}} tabStyle="underline">
        <TabItem classes={{button: "cursor-pointer"}} title='_Current Bookings_' open>
            <div class="flex justify-end">
                <Button class="cursor-pointer" onclick={() => modal = true}>_New Booking_</Button>
            </div>
            <DevicebookingTable {reservations} {items}/>
        </TabItem>
        <TabItem classes={{button: "cursor-pointer"}} title="_All Bookings_" onclick={() => goto('/user/item-bookings/all', {invalidate: ['/user/item-bookings/all']})}/>
    </Tabs>
</div>
<!-- <TableView columns={columns}
           contentPage={data.page}
           onCreate={() => modal = true}
/> -->

{#if modal}
    <CreateItemBookingModal bind:open={modal} categoryList={data.categories} itemList={data.items}/>
{/if}