<script>
    import TabNavigation from "$lib/components/TabNavigation.svelte";

    let {children} = $props();

    const items = [{
        title: "_Support Tickets_",
        href: "/manage/support-tickets"
    }, {
        title: "_Support Ticket Categories_",
        href: "/manage/support-ticket-categories"
    }, {
        title: "_Support Ticket Urgencies_",
        href: "/manage/support-ticket-urgencies"
    }];
</script>

<TabNavigation items={items}>
    {@render children?.()}
</TabNavigation>