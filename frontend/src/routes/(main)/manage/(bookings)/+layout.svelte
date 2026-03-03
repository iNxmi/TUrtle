<script>
    import TabNavigation from "$lib/components/TabNavigation.svelte";
    import {m} from '$lib/paraglide/messages.js';

    let {children} = $props();

    const items = [{
        title: m.main_navigation_item_manage_room_bookings(),
        href: "/manage/room-bookings"
    }, {
        title: m.main_navigation_item_manage_item_bookings(),
        href: "/manage/item-bookings"
    }];
</script>

<TabNavigation items={items}>
    {@render children?.()}
</TabNavigation>