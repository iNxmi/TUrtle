<script>
    import {TabItem, Tabs} from "flowbite-svelte";
    import {page} from "$app/state";

    let {children} = $props();
    let key = $derived(page.url.pathname);

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