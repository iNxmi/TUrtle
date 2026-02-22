<script>
    import {TabItem, Tabs} from "flowbite-svelte";
    import {page} from "$app/state";

    let {children} = $props();
    let key = $derived(page.url.pathname);

    const items = [{
        title: "Email",
        href: "/manage/email-templates"
    }, {
        title: "Content",
        href: "/manage/content-templates"
    }];
</script>

<Tabs bind:selected={key}>
    {#each items as item}
        <a href={item.href}>
            <TabItem key={item.href} title={item.title}>
                {@render children?.()}
            </TabItem>
        </a>
    {/each}
</Tabs>