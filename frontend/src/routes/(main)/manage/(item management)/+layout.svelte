<script>
    import TabNavigation from "$lib/components/TabNavigation.svelte";
    import {m} from '$lib/paraglide/messages.js';

    let {children} = $props();

    const items = [{
        title: m.main_navigation_item_manage_items(),
        href: "/manage/items",
    }, {
        title: m.main_navigation_item_manage_item_categories(),
        href: "/manage/item-categories"
    }, {
        title: m.main_navigation_item_manage_lockers(),
        href: "/manage/lockers"
    }];
</script>

<TabNavigation items={items}>
    {@render children?.()}
</TabNavigation>