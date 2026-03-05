<script>
    import {m} from "$lib/paraglide/messages.js";
    import {Input, Select} from "flowbite-svelte";
    import Card from "$lib/components/Card.svelte";
    import Calendar from "$lib/components/Calendar.svelte";
    import EntityPage from "$lib/components/EntityPage.svelte";

    let {data} = $props();
    let booking = $derived(data.booking);

    const itemItems = $derived(data.items.map((item) => ({
        value: item.id,
        name: item.name
    })));

    const userItems = $derived(data.users.map((user) => ({
        value: user.id,
        name: user.username
    })));

    const statusItems = $derived(data.statuses.map((status) => ({
        value: status,
        name: status
    })));

    let sources = [];

    let items = $derived([{
        label: m.manage_item_bookings_label_id(),
        field: "id",
        component: Input,
        props: {
            value: booking.id
        }
    }, {
        label: m.manage_item_bookings_label_item(),
        field: "itemId",
        editable: true,
        component: Select,
        props: {
            value: booking.itemId,
            items: itemItems
        }
    }, {
        label: m.manage_item_bookings_label_user(),
        field: "userId",
        editable: true,
        component: Select,
        props: {
            value: booking.userId,
            items: userItems
        }
    }, [{
        label: m.manage_item_bookings_label_start(),
        field: "start",
        editable: true,
        component: Input,
        props: {
            value: booking.start
        }
    }, {
        label: m.manage_item_bookings_label_end(),
        field: "end",
        editable: true,
        component: Input,
        props: {
            value: booking.end
        }
    }], {
        label: m.manage_item_bookings_label_status(),
        field: "status",
        editable: true,
        component: Select,
        props: {
            value: booking.status,
            items: statusItems
        }
    }, [{
        label: m.manage_item_bookings_label_created_at(),
        field: "createdAt",
        component: Input,
        props: {
            value: booking.createdAt
        }
    }, {
        label: m.manage_item_bookings_label_updated_at(),
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

    <EntityPage items={items} onPatch={(payload) => ItemBookings.patch(bookings.id, payload)}/>
</div>