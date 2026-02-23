<script>
    import OpenLockerModal from "$lib/components/modal/OpenLockerModal.svelte";
    import {Button, Input, div} from "flowbite-svelte";

    let {data} = $props();
    let booking = data.booking;
    let item = data.item;
    let locker = data.locker;

    let modal = $state(false);
</script>

<form class="flex flex-col gap-5">
    <div>
        <span>_ID_</span>
        <Input type="text" value={booking.id} disabled/>
    </div>

    <div>
        <span>_title_</span>
        <Input type="text" value={booking.title} disabled/>
    </div>

    <div class="flex gap-5">
        <div class="flex-1">
            <span>_start_</span>
            <Input type="text" value={booking.start} disabled/>
        </div>

        <div class="flex-1">
            <span>_end_</span>
            <Input type="text" value={booking.end} disabled/>
        </div>
    </div>

    <div>
        <span>_status_</span>
        <Input type="text" value={booking.status} disabled/>
    </div>

    <Button onclick={() => modal = true}>Unlock Locker</Button>
</form>

<OpenLockerModal bind:open={modal} locker={locker}/>