<script>
    import { Tabs, TabItem, Textarea } from "flowbite-svelte";
    import Markdown from '$lib/components/Markdown.svelte'

    let {content} = $props();
</script>

<Tabs>
    <TabItem open title="Markdown">
        <Textarea rows="12" class="w-full" bind:value={content} />
    </TabItem>

    <TabItem title="Preview">
        <Markdown content={content}/>
    </TabItem>
</Tabs>



