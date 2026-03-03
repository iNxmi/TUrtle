<script>
    import TabNavigation from "$lib/components/TabNavigation.svelte";
    import {m} from '$lib/paraglide/messages.js';

    let {children} = $props();

    const items = [{
        title: m.main_navigation_item_manage_users(),
        href: "/manage/users"
    }, {
        title: m.main_navigation_item_manage_roles(),
        href: "/manage/roles"
    }];
</script>

<TabNavigation items={items}>
    {@render children?.()}
</TabNavigation>