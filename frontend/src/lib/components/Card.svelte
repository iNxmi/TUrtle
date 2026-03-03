<script>
    let {
        children,
        class: className = "",
        ...rest
    } = $props()
</script>

<div class={`bg-background-secondary rounded-xl p-5 shadow-md/20 ${className}`} {...rest}>
    {@render children?.()}
</div>