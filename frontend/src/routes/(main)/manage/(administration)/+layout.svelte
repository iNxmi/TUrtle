<script>
    import TabNavigation from "$lib/components/TabNavigation.svelte";

    let {children} = $props();

    const items = [{
        title: "Audit Logs",
        href: "/manage/audit-logs"
    }, {
        title: "Configuration",
        href: "/manage/configuration"
    }, {
        title: "Exceptions",
        href: "/manage/exceptions"
    }, {
        title: "Statistic Queries",
        href: "/manage/statistic-queries"
    }];
</script>

<TabNavigation items={items}>
    {@render children?.()}
</TabNavigation>