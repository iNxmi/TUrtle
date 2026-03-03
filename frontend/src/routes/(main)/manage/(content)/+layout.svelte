<script>
    import TabNavigation from "$lib/components/TabNavigation.svelte";
    import {m} from '$lib/paraglide/messages.js';

    let {children} = $props();

    const items = [{
        title: m.main_navigation_item_manage_email_templates(),
        href: "/manage/email-templates"
    }, {
        title: m.main_navigation_item_manage_content_templates(),
        href: "/manage/content-templates"
    }, {
        title: m.main_navigation_item_manage_faq(),
        href: "/manage/faq"
    }];
</script>

<TabNavigation items={items}>
    {@render children?.()}
</TabNavigation>