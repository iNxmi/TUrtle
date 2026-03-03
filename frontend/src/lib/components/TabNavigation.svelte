<script>
    import {TabItem, Tabs} from "flowbite-svelte";
    import {page} from "$app/state";

    /*
    Example Items:

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
     */

    let {
        children,
        items = [],
        class: className = "",
        ...rest
    } = $props();

    let key = $derived(page.url.pathname);
</script>

<div class={className} {...rest}>
    <Tabs contentClass="p-0" bind:selected={key}>
        {#each items as item}
            <a href={item.href}>
                <TabItem open={key.includes(item.href)} key={item.href} title={item.title}>
                    {@render children?.()}
                </TabItem>
            </a>
        {/each}
    </Tabs>
</div>