<script>
    import {TabItem, Tabs} from "flowbite-svelte";
    import {page} from "$app/state";

    let {children} = $props();

    const items = [{
        title: "Contact",
        href: "/contact"
    }, {
        title: "About",
        href: "/about"
    }, {
        title: "TOS",
        href: "/tos"
    }];

    let activeUrl = $derived(page.url.pathname);
</script>

<Tabs bind:selected={activeUrl}>
    {#each items as item}
        <a href={item.href}>
            <TabItem key={item.href} title={item.title}>
                {@render children?.()}
            </TabItem>
        </a>
    {/each}
</Tabs>