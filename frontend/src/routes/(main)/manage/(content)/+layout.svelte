<script>
    import TabNavigation from "$lib/components/TabNavigation.svelte";

    let {children} = $props();

    const items = [{
        title: "_Email Templates_",
        href: "/manage/email-templates"
    }, {
        title: "_Content Templates_",
        href: "/manage/content-templates"
    }, {
        title: "_FAQ_",
        href: "/manage/faq"
    }];
</script>

<TabNavigation items={items}>
    {@render children?.()}
</TabNavigation>