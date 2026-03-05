<script>
    import {m} from "$lib/paraglide/messages.js";
    import {Input, Select, Textarea, MultiSelect} from "flowbite-svelte";
    import Card from "$lib/components/Card.svelte";
    import Calendar from "$lib/components/Calendar.svelte";
    import EntityPage from "$lib/components/EntityPage.svelte";
    import {RoomBookings} from "$lib/api";

    let {data} = $props();
    let booking = $derived(data.booking);

    let sources = [];

    const userItems = $derived(data.users.map((user) => ({
        value: user.id,
        name: user.username
    })));

    const accessItems = $derived(data.access.map((access) => ({
        value: access,
        name: access
    })));

    const statusItems = $derived(data.status.map((status) => ({
        value: status,
        name: status
    })));

    const items = $derived([{
        label: m.manage_room_bookings_label_id(),
        field: "id",
        component: Input,
        props: {
            value: booking.id
        }
    },{
        label: m.manage_room_bookings_label_user(),
        field: "userId",
        editable: true,
        component: Select,
        props: {
            value: booking.userId,
            items: userItems
        }
    },{
        label: m.manage_room_bookings_label_title(),
        field: "title",
        editable: true,
        component: Input,
        props: {
            value: booking.title
        }
    },{
        label: m.manage_room_bookings_label_description(),
        field: "description",
        editable: true,
        component: Textarea,
        props: {
            value: booking.description
        }
    },{
        label: m.manage_room_bookings_label_access(),
        field: "access",
        editable: true,
        component: Select,
        props: {
            value: booking.access,
            items: accessItems
        }
    },{
        label: m.manage_room_bookings_label_whitelist(),
        field: "whitelistedUserIds",
        editable: true,
        component: MultiSelect,
        props: {
            value: booking.whitelistedUserIds,
            items: userItems
        }
    },[{
        label: m.manage_room_bookings_label_start(),
        field: "start",
        editable: true,
        component: Input,
        props: {
            value: booking.start
        }
    },{
        label: m.manage_room_bookings_label_end(),
        field: "end",
        editable: true,
        component: Input,
        props: {
            value: booking.end
        }
    }],{
        label: m.manage_room_bookings_label_status(),
        field: "status",
        editable: true,
        component: Select,
        props: {
            value: booking.status,
            items: statusItems
        }
    },[{
        label: m.manage_room_bookings_label_created_at(),
        field: "createdAt",
        component: Input,
        props: {
            value: booking.createdAt
        }
    },{
        label: m.manage_room_bookings_label_updated_at(),
        field: "updatedAt",
        component: Input,
        props: {
            value: booking.updatedAt
        }
    }]]);
</script>

<div class="flex-1 flex flex-col 2xl:flex-row gap-5">
    <Card class="flex-1">
        <Calendar sources={sources}/>
    </Card>

    <EntityPage items={items} onPatch={(payload) => RoomBookings.patch(booking.id, payload)}/>
</div>

<!--<Card>-->
<!--    <form class="flex flex-col gap-5">-->

<!--        <div class="flex gap-5">-->
<!--            <div class="flex-1">-->
<!--                <span>{m.manage_room_bookings_label_start()}</span>-->
<!--                <Input type="text" value={(new Date(booking.start)).toLocaleString()} disabled/>-->
<!--            </div>-->
<!--            <div class="flex-1">-->
<!--                <span>{m.manage_room_bookings_label_end()}</span>-->
<!--                <Input type="text" value={(new Date(booking.end)).toLocaleString()} disabled/>-->
<!--            </div>-->
<!--        </div>-->

<!--        <div>-->
<!--            <span>{m.manage_room_bookings_label_status()}</span>-->
<!--            <Input type="text" value={booking.status} disabled/>-->
<!--        </div>-->

<!--        <div class="flex gap-5">-->
<!--            <div class="flex-1">-->
<!--                <span>{m.manage_room_bookings_label_created_at()}</span>-->
<!--                <Input type="text" value={(new Date(booking.createdAt)).toLocaleString()} disabled/>-->
<!--            </div>-->
<!--            <div class="flex-1">-->
<!--                <span>{m.manage_room_bookings_label_updated_at()}</span>-->
<!--                <Input type="text" value={(new Date(booking.updatedAt)).toLocaleString()} disabled/>-->
<!--            </div>-->
<!--        </div>-->
<!--    </form>-->
<!--</Card>-->