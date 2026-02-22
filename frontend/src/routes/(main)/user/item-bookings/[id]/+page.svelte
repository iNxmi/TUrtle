<script>
    import OpenLockerModal from "$lib/components/modal/OpenLockerModal.svelte";
    import {Button, Input, Label} from "flowbite-svelte";

    let {data} = $props();
    let booking = data.booking;
    let item = data.item;
    let locker = data.locker;

    let modal = $state(false);
</script>

<form class="flex flex-col gap-5">
    <Label>
        <span>_ID_</span>
        <Input type="text" value={booking.id} disabled/>
    </Label>

    <Label>
        <span>_item_</span>
        <Input type="text" value={item.name} disabled/>
    </Label>

    <div class="flex gap-5">
        <Label class="flex-1">
            <span>_start_</span>
            <Input type="text" value={booking.start} disabled/>
        </Label>

        <Label class="flex-1">
            <span>_end_</span>
            <Input type="text" value={booking.end} disabled/>
        </Label>
    </div>

    <Label>
        <span>_status_</span>
        <Input type="text" value={booking.status} disabled/>
    </Label>

    <Button onclick={() => modal = true}>Unlock Locker</Button>
</form>

<OpenLockerModal bind:open={modal} locker={locker}/>