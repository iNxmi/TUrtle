<script>
    import {m} from '$lib/paraglide/messages.js';
    import TableView from "$lib/components/TableView.svelte"
    import {goto} from "$app/navigation";
    import Card from "$lib/components/Card.svelte";
    import Calendar from "$lib/components/Calendar.svelte";
    import _ from "lodash";

    const {data} = $props();

    const usersById = $derived(_.keyBy(data.users, "id"));
    const itemsById = $derived(_.keyBy(data.items, "id"));

    const columns = [
        {field: "id", label: m.manage_item_bookings_label_id(), enabled: false},
        {
            field: "itemId", label: m.manage_item_bookings_label_item(),
            transform: (value) => itemsById[value].name
        },
        {
            field: "userId", label: m.manage_item_bookings_label_user(),
            transform: (value) => usersById[value].username
        },
        {field: "status", label: m.manage_item_bookings_label_status()},
        {
            field: "start", label: m.manage_item_bookings_label_start(),
            transform: (value) => new Date(value).toLocaleString()
        },
        {
            field: "end", label: m.manage_item_bookings_label_end(),
            transform: (value) => new Date(value).toLocaleString()
        },
        {
            field: "updatedAt",
            label: m.manage_item_bookings_label_updated_at(),
            transform: (value) => new Date(value).toLocaleString(),
            enabled: false
        },
        {
            field: "createdAt",
            label: m.manage_item_bookings_label_created_at(),
            transform: (value) => new Date(value).toLocaleString(),
            enabled: false
        }
    ];

    let sources = [];
</script>

<div class="flex-1 flex flex-col 2xl:flex-row gap-5">
    <Card class="flex-1">
        <Calendar bind:sources={sources} onEventClicked={(info) => goto(info.event.extendedProps.href)}/>
    </Card>

    <div class="flex-1 flex">
        <TableView columns={columns}
                   contentPage={data.page}
                   onItemClicked={(item) => goto(`/manage/item-bookings/${item.id}`)}
        />
    </div>
</div>

<!--{#if modal}-->
<!--    <CreateItemBookingModal bind:open={modal} categoryList={data.categories} itemList={data.items}/>-->
<!--{/if}-->