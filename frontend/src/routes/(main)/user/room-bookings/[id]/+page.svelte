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
            <span>{m.manage_room_bookings_label_id()}</span>
            <Input type="text" value={booking.id} disabled/>
        </div>

        <div>
            <span>{m.manage_room_bookings_label_user()}</span>
            <Input type="text" value={booking.userId} disabled/>
        </div>

        <div>
            <span>{m.manage_room_bookings_label_title()}</span>
            <Input type="text" value={booking.title} disabled/>
        </div>

        <div>
            <span>{m.manage_room_bookings_label_description()}</span>
            <Input type="text" value={booking.description} disabled/>
        </div>

        <div>
            <span>{m.manage_room_bookings_label_access()}</span>
            <Input type="text" value={booking.accessibility} disabled/>
        </div>

        <div>
            <span>{m.manage_room_bookings_label_whitelist()}</span>
            <Input type="text" value={JSON.stringify(booking.whitelistedUserIds)} disabled/>
        </div>

        <div class="flex gap-5">
            <div class="flex-1">
                <span>{m.manage_room_bookings_label_start()}</span>
                <Input type="text" value={(new Date(booking.start)).toLocaleString()} disabled/>
            </div>
            <div class="flex-1">
                <span>{m.manage_room_bookings_label_end()}</span>
                <Input type="text" value={(new Date(booking.end)).toLocaleString()} disabled/>
            </div>
        </div>

        <div>
            <span>{m.manage_room_bookings_label_status()}</span>
            <Input type="text" value={booking.status} disabled/>
        </div>

        <div class="flex gap-5">
            <div class="flex-1">
                <span>{m.manage_room_bookings_label_created_at()}</span>
                <Input type="text" value={(new Date(booking.createdAt)).toLocaleString()} disabled/>
            </div>
            <div class="flex-1">
                <span>{m.manage_room_bookings_label_updated_at()}</span>
                <Input type="text" value={(new Date(booking.updatedAt)).toLocaleString()} disabled/>
            </div>
        </div>
    </form>
</Card>