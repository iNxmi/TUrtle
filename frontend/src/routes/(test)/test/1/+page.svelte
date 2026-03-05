<script>
    import EntityPage from "$lib/components/EntityPage.svelte";
    import DateTimePicker from "$lib/components/DateTimePicker.svelte";
    import {Input, Select} from "flowbite-svelte";
    import {ItemBookings} from "$lib/api";

    let {data} = $props();

    let items = $derived([{
        label: "ID",
        field: "id",
        component: Input,
        props: {
            value: data.itemBooking.id
        }
    }, {
        label: "User",
        field: "userId",
        editable: true,
        href: `/manage/users/${data.itemBooking.userId}`,
        component: Select,
        props: {
            value: data.itemBooking.userId,
            items: data.users.map((user) => ({name: user.username, value: user.id}))
        }
    }, {
        label: "Item",
        field: "itemId",
        editable: true,
        href: `/manage/items/${data.itemBooking.itemId}`,
        component: Select,
        props: {
            value: data.itemBooking.itemId,
            items: data.items.map((item) => ({name: item.name, value: item.id}))
        }
    }, [{
        label: "Start",
        field: "start",
        editable: true,
        component: DateTimePicker,
        props: {
            value: new Date(data.itemBooking.start),
        }
    }, {
        label: "End",
        field: "end",
        editable: true,
        component: DateTimePicker,
        props: {
            value: new Date(data.itemBooking.end)
        }
    }], {
        label: "Status",
        field: "status",
        editable: true,
        component: Select,
        props: {
            value: data.itemBooking.status,
            items: data.itemBookingStatus.map((status) => ({name: status, value: status}))
        }
    }, [{
        label: "Created At",
        field: "createdAt",
        component: Input,
        props: {
            value: new Date(data.itemBooking.createdAt)
        }
    }, {
        label: "Updated At",
        field: "updatedAt",
        component: Input,
        props: {
            value: new Date(data.itemBooking.updatedAt)
        }
    }]]);
</script>

<EntityPage items={items} onPatch={(payload) => ItemBookings.patch(data.itemBooking.id, payload)}/>