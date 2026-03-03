<script>
    import TabNavigation from "$lib/components/TabNavigation.svelte";

    let {children} = $props();

    const items = [{
        title: "Support Tickets",
        href: "/manage/support-tickets"
    }, {
        title: "Support Ticket Categories",
        href: "/manage/support-ticket-categories"
    }, {
        title: "Support Ticket Urgencies",
        href: "/manage/support-ticket-urgencies"
    }];
</script>

<TabNavigation items={items}>
    {@render children?.()}
</TabNavigation>