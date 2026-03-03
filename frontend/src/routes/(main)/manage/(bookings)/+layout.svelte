<script>
    import TabNavigation from "$lib/components/TabNavigation.svelte";

    let {children} = $props();

    const items = [{
        title: "_Room Bookings_",
        href: "/manage/room-bookings"
    }, {
        title: "_Item Bookings_",
        href: "/manage/item-bookings"
    }];
</script>

<TabNavigation items={items}>
    {@render children?.()}
</TabNavigation>