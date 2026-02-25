<script>
	import { goto } from "$app/navigation";
    import TableView from "$lib/components/TUrtleTable.svelte";
    import {m} from "$lib/paraglide/messages.js";
    import { Tabs, TabItem} from 'flowbite-svelte';

    let {data} = $props();

    const columns = [
        {field: "id", label: m.user_item_bookings_label_id()},
        {field: "itemId", label: m.user_item_bookings_label_item_id()},
        {
            field: "start", label: m.user_item_bookings_label_start(),
            transform: (item) => new Date(item).toLocaleString()
        },
        {
            field: "end", label: m.user_item_bookings_label_end(),
            transform: (item) => new Date(item).toLocaleString()
        },
        {
            field: "updatedAt",
            label: m.user_item_bookings_label_updated_at(),
            transform: (item) => new Date(item).toLocaleString()
        },
        {
            field: "createdAt",
            label: m.user_item_bookings_label_created_at(),
            transform: (item) => new Date(item).toLocaleString()
        }
    ];
</script> 
<div>
    <Tabs tabStyle="underline">
        <TabItem classes={{button: "cursor-pointer"}} title='_Current Bookings_' onclick={() => goto('/user/item-bookings', {invalidate: ['/user/item-bookings']})}/>
        <TabItem classes={{button: "cursor-pointer"}} open title="_All Bookings_">
            <TableView {columns} items={data.page.content} page={data.page} />
        </TabItem>
    </Tabs>
</div>
