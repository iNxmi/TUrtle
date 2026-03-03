<script>
    import TabNavigation from "$lib/components/TabNavigation.svelte";
    import {m} from '$lib/paraglide/messages.js';

    let {children} = $props();

    const items = [{
        title: m.main_navigation_item_manage_support_tickets(),
        href: "/manage/support-tickets"
    }, {
        title: m.main_navigation_item_manage_support_ticket_categories(),
        href: "/manage/support-ticket-categories"
    }, {
        title: m.main_navigation_item_manage_support_ticket_urgencies(),
        href: "/manage/support-ticket-urgencies"
    }];
</script>

<TabNavigation items={items}>
    {@render children?.()}
</TabNavigation>