<script>
    import { Tabs} from 'flowbite-svelte'

    let { children} = $props();
</script>
<div>
    <Tabs tabStyle="underline"  classes={{content: "flex flex-col gap-3 min-h-[calc(100svh-300px)]"}}>
      {@render children?.()}
    </Tabs>
</div>
