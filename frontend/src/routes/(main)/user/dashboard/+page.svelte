<script>
    import Calendar from "$lib/components/Calendar.svelte";
    import DevicebookingTable from "$lib/components/DevicebookingTable.svelte";
    import { Card, Label } from 'flowbite-svelte';

    let {data} = $props();
    let items = $derived(data.items);
    let reservations = $derived(data.itemBookings);
</script>
<div class="flex flex-row gap-5 w-full h-[calc(100svh-300px)]">
    <div class="flex flex-col gap-2">
       <span class="text-text">_Events_</span> 
        <Card id="events" class="grow" size="xl">
            <Calendar sources={['/api/room-bookings']}/>
        </Card>
    </div>
    <div class="flex flex-col gap-2">
        <span class="text-text">_Room Bookings_</span>
        <Card size="xl" class="grow" >
            <DevicebookingTable {items} {reservations} />
        </Card>
    </div>
    <div class="flex flex-col gap-2">
        <span class="text-text">_News_</span>
        <Card size="xl" class="grow" >
        _CONTENT_
        </Card>
    </div>
</div>