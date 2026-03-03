<script>
    import TabNavigation from "$lib/components/TabNavigation.svelte";
    import {m} from '$lib/paraglide/messages.js';

    let {children} = $props();

    const items = [{
        title: m.main_navigation_item_manage_audit_logs(),
        href: "/manage/audit-logs"
    }, {
        title: m.main_navigation_item_manage_configuration(),
        href: "/manage/configuration"
    }, {
        title: m.main_navigation_item_manage_exceptions(),
        href: "/manage/exceptions"
    }, {
        title: m.main_navigation_item_manage_statistic_queries(),
        href: "/manage/statistic-queries"
    }];
</script>

<TabNavigation items={items}>
    {@render children?.()}
</TabNavigation>