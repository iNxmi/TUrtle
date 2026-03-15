<script>
    import {m} from "$lib/paraglide/messages.js";
    import {Input, MultiSelect, Select, Textarea} from "flowbite-svelte";
    import Card from "$lib/components/Card.svelte";
    import Calendar from "$lib/components/Calendar.svelte";
    import EntityPage from "$lib/components/EntityPage.svelte";
    import {RoomBookings} from "$lib/api";

    let {data} = $props();
    let entity = $derived(data.entity);

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
            value: entity.id
        }
    }, {
        label: m.manage_room_bookings_label_user(),
        field: "userId",
        editable: true,
        component: Select,
        props: {
            value: entity.userId,
            items: userItems
        }
    }, {
        label: m.manage_room_bookings_label_title(),
        field: "title",
        editable: true,
        component: Input,
        props: {
            value: entity.title
        }
    }, {
        label: m.manage_room_bookings_label_description(),
        field: "description",
        editable: true,
        component: Textarea,
        props: {
            value: entity.description
        }
    }, {
        label: m.manage_room_bookings_label_access(),
        field: "access",
        editable: true,
        component: Select,
        props: {
            value: entity.access,
            items: accessItems
        }
    }, {
        label: m.manage_room_bookings_label_whitelist(),
        field: "whitelistedUserIds",
        editable: true,
        component: MultiSelect,
        props: {
            value: entity.whitelistedUserIds,
            items: userItems
        }
    }, [{
        label: m.manage_room_bookings_label_start(),
        field: "start",
        editable: true,
        component: Input,
        props: {
            value: entity.start
        }
    }, {
        label: m.manage_room_bookings_label_end(),
        field: "end",
        editable: true,
        component: Input,
        props: {
            value: entity.end
        }
    }], {
        label: m.manage_room_bookings_label_status(),
        field: "status",
        editable: true,
        component: Select,
        props: {
            value: entity.status,
            items: statusItems
        }
    }, [{
        label: m.manage_room_bookings_label_created_at(),
        field: "createdAt",
        component: Input,
        props: {
            value: entity.createdAt
        }
    }, {
        label: m.manage_room_bookings_label_updated_at(),
        field: "updatedAt",
        component: Input,
        props: {
            value: entity.updatedAt
        }
    }]]);
</script>

<div class="flex-1 flex flex-col 2xl:flex-row gap-5">
    <Card class="flex-1">
        <Calendar sources={sources}/>
    </Card>

    <EntityPage items={items}
                onPatch={(payload) => RoomBookings.patch(entity.id, payload)}
                onDelete={() => RoomBookings.delete(entity.id)}
    />
</div>