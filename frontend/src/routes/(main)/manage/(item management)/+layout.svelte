<script>
    import TabNavigation from "$lib/components/TabNavigation.svelte";

    let {children} = $props();

    const items = [{
        title: "_Items_",
        href: "/manage/items",
    }, {
        title: "_Item Categories_",
        href: "/manage/item-categories"
    }, {
        title: "_Lockers_",
        href: "/manage/lockers"
    }];
</script>

<TabNavigation items={items}>
    {@render children?.()}
</TabNavigation>