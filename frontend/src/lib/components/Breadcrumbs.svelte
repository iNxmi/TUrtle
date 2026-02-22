<script>
    import {Breadcrumb, BreadcrumbItem} from "flowbite-svelte";
    import {page} from "$app/state";

    let breadcrumbs = $derived(getBreadcrumbs(page.url.pathname))

    function getBreadcrumbs(path) {
        const result = [{href: "/", name: "/"}];
        if (!path || path === "/")
            return result;

        const segments = path.split("/").filter(Boolean);

        let current = "";
        for (const segment of segments) {
            current += `/${segment}`;
            result.push({
                href: current,
                name: getBreadcrumbName(segment)
            });
        }

        return result;
    }

    function getBreadcrumbName(segment) {
        if (!segment)
            return "";

        return segment
            .replace(/[-_]+/g, " ")
            .toLowerCase()
            .split(" ")
            .map(word =>
                word ? word[0].toUpperCase() + word.slice(1) : ""
            )
            .join(" ");
    }
</script>

<Breadcrumb>
    {#each breadcrumbs as breadcrumb, index}
        {#if index === 0}
            <BreadcrumbItem href={breadcrumb.href} home>
                <span class="font-bold">{breadcrumb.name}</span>
            </BreadcrumbItem>
        {:else}
            <BreadcrumbItem href={breadcrumb.href}>
                <span class="font-bold">{breadcrumb.name}</span>
            </BreadcrumbItem>
        {/if}
    {/each}
</Breadcrumb>