<script>
    import {TabItem, Tabs} from "flowbite-svelte";
    import {page} from "$app/state";
    import { resolve } from "$app/paths";
    let {
        children,
        items = [],
        class: className = "",
        ...rest
    } = $props();

    let visibleItems = $derived(items.filter((item) => !item.permission || item.permission && page.data.permissions.includes(item.permission)));
    let key = $derived(page.url.pathname);
</script>

<div class={className} {...rest}>
    <Tabs classes={{content: "p-0 bg-transparent!"}} bind:selected={key}>
        {#each visibleItems as item (item.title)}
            <a href={resolve(item.href)}>
                <TabItem classes={{button: "cursor-pointer"}} open={key.includes(item.href)} key={item.href} title={item.title}>
                    {@render children?.()}
                </TabItem>
            </a>
        {/each}
    </Tabs>
</div>