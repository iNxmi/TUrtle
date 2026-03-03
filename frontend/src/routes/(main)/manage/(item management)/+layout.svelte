<script>
    import TabNavigation from "$lib/components/TabNavigation.svelte";

    let {children} = $props();

    const items = [{
        title: "Item Categories",
        href: "/manage/item-categories",
    }, {
        title: "Items",
        href: "/manage/items"
    }, {
        title: "Lockers",
        href: "/manage/lockers"
    }];
</script>

<TabNavigation items={items}>
    {@render children?.()}
</TabNavigation>