<script>
    import {resolve} from "$app/paths";

    let {href, children, ...props} = $props();
</script>

<a {...props} class="text-csw" href={resolve(href)}>
    {@render children?.()}
</a>