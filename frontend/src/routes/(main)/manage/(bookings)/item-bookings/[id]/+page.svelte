<script>
    import {m} from "$lib/paraglide/messages.js";
    import {Input} from "flowbite-svelte";
    import Card from "$lib/components/Card.svelte";

    let {data} = $props();
    let booking = $derived(data.booking);
</script>

<Card>
    <form class="flex flex-col gap-5">
        <div>
            <span>{m.manage_item_bookings_label_id()}</span>
            <Input type="text" value={booking.id} disabled/>
        </div>

        <div>
            <span>{m.manage_item_bookings_label_item_id()}</span>
            <Input type="text" value={booking.itemId} disabled/>
        </div>

        <div>
            <span>{m.manage_item_bookings_label_user_id()}</span>
            <Input type="text" value={booking.userId} disabled/>
        </div>

        <div class="flex gap-5">
            <div class="flex-1">
                <span>{m.manage_item_bookings_label_start()}</span>
                <Input type="text" value={(new Date(booking.start)).toLocaleString()} disabled/>
            </div>
            <div class="flex-1">
                <span>{m.manage_item_bookings_label_end()}</span>
                <Input type="text" value={(new Date(booking.end)).toLocaleString()} disabled/>
            </div>
        </div>

        <div>
            <span>{m.manage_item_bookings_label_status()}</span>
            <Input type="text" value={booking.status} disabled/>
        </div>

        <div class="flex gap-5">
            <div class="flex-1">
                <span>{m.manage_item_bookings_label_created_at()}</span>
                <Input type="text" value={(new Date(booking.createdAt)).toLocaleString()} disabled/>
            </div>
            <div class="flex-1">
                <span>{m.manage_item_bookings_label_updated_at()}</span>
                <Input type="text" value={(new Date(booking.updatedAt)).toLocaleString()} disabled/>
            </div>
        </div>
    </form>
</Card>