<script>
    import TabNavigation from "$lib/components/TabNavigation.svelte";

    let {children} = $props();

    const items = [{
        title: "_Users_",
        href: "/manage/users"
    }, {
        title: "_Roles_",
        href: "/manage/roles"
    }];
</script>

<TabNavigation items={items}>
    {@render children?.()}
</TabNavigation>