<script>
    import TabNavigation from "$lib/components/TabNavigation.svelte";

    let {children} = $props();

    const items = [{
        title: "Item Bookings",
        href: "/manage/item-bookings"
    }, {
        title: "Room Bookings",
        href: "/manage/room-bookings"
    }];
</script>

<TabNavigation items={items}>
    {@render children?.()}
</TabNavigation>