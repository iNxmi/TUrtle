<script>
    import {m} from "$lib/paraglide/messages.js";
    import {Button, Hr, Input} from "flowbite-svelte";
    import Card from "$lib/components/Card.svelte";
    import UnlockLockerModal from "$lib/components/modal/UnlockLockerModal.svelte";

    let {data} = $props();
    let booking = $derived(data.booking);
    let locker = $derived(data.locker);

    let modal = $state(false);
</script>

<Card>
    <form class="flex flex-col gap-5">
        <div>
            <div>{m.manage_item_bookings_label_id()}</div>
            <Input type="text" value={booking.id} disabled/>
        </div>

        <div>
            <div>{m.manage_item_bookings_label_item_id()}</div>
            <Input type="text" value={booking.itemId} disabled/>
        </div>

        <div>
            <div>{m.manage_item_bookings_label_user_id()}</div>
            <Input type="text" value={booking.userId} disabled/>
        </div>

        <div class="flex gap-5">
            <div class="flex-1">
                <div>{m.manage_item_bookings_label_start()}</div>
                <Input type="text" value={(new Date(booking.start)).toLocaleString()} disabled/>
            </div>
            <div class="flex-1">
                <div>{m.manage_item_bookings_label_end()}</div>
                <Input type="text" value={(new Date(booking.end)).toLocaleString()} disabled/>
            </div>
        </div>

        <div>
            <div>{m.manage_item_bookings_label_status()}</div>
            <Input type="text" value={booking.status} disabled/>
        </div>

        <div class="flex gap-5">
            <div class="flex-1">
                <div>{m.manage_item_bookings_label_created_at()}</div>
                <Input type="text" value={(new Date(booking.createdAt)).toLocaleString()} disabled/>
            </div>
            <div class="flex-1">
                <div>{m.manage_item_bookings_label_updated_at()}</div>
                <Input type="text" value={(new Date(booking.updatedAt)).toLocaleString()} disabled/>
            </div>
        </div>

        <Hr class="m-0 p-0"/>

        <div class="flex gap-3 justify-end">
            <Button disabled={data.isLocalNetwork} onclick={() => modal = true}>_unlock_locker_</Button>
        </div>
    </form>
</Card>

<UnlockLockerModal bind:open={modal} locker={locker}/>