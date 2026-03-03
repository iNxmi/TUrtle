<script>
    import TabNavigation from "$lib/components/TabNavigation.svelte";

    let {children} = $props();

    const items = [{
        title: "_Audit Logs_",
        href: "/manage/audit-logs"
    }, {
        title: "_Configuration_",
        href: "/manage/configuration"
    }, {
        title: "_Exceptions_",
        href: "/manage/exceptions"
    }, {
        title: "_Statistic Queries_",
        href: "/manage/statistic-queries"
    }];
</script>

<TabNavigation items={items}>
    {@render children?.()}
</TabNavigation>