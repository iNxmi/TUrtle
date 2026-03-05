<script>
    import TabNavigation from "$lib/components/TabNavigation.svelte";
    import {m} from '$lib/paraglide/messages.js';

    let {children} = $props();

    const items = [{
        title: m.main_navigation_item_manage_email_templates(),
        href: "/manage/email-templates",
        permission: "MANAGE_EMAIL_TEMPLATES"
    }, {
        title: m.main_navigation_item_manage_content_templates(),
        href: "/manage/content-templates",
        permission: "MANAGE_GENERAL_TEMPLATES"
    }, {
        title: m.main_navigation_item_manage_faq(),
        href: "/manage/faq",
        permission: "MANAGE_FAQ"
    }];
</script>

<TabNavigation items={items}>
    {@render children?.()}
</TabNavigation>