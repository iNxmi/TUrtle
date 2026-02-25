<script>
    let {
        children,
        class: className = "",
        ...rest
    } = $props()
</script>

<div class={`bg-background-secondary rounded-xl p-5 ${className}`} {...rest}>
    {@render children?.()}
</div>