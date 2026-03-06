<script>
    import { Button } from "flowbite-svelte"
    let {class: className = "",
        onclick = () =>{},
        children,
        ...rest
    } = $props();
</script>
<Button class={`bg-orange-400! dark:bg-orange-600! text-white dark:text-white! cursor-pointer ${className}`} {onclick} {...rest}>
    {@render children?.()}
</Button>