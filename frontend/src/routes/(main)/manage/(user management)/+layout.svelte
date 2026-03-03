<script>
    import TabNavigation from "$lib/components/TabNavigation.svelte";

    let {children} = $props();

    const items = [{
        title: "Users",
        href: "/manage/users"
    }, {
        title: "Roles",
        href: "/manage/roles"
    }];
</script>

<TabNavigation items={items}>
    {@render children?.()}
</TabNavigation>