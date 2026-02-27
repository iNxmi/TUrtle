<script>
    import TabNavigation from "$lib/components/TabNavigation.svelte";

    let {children} = $props();

    const items = [{
        title: "Email Templates",
        href: "/manage/email-templates"
    }, {
        title: "Content Templates",
        href: "/manage/content-templates"
    }, {
        title: "FAQ",
        href: "/manage/faq"
    }];
</script>

<TabNavigation items={items}>
    {@render children?.()}
</TabNavigation>