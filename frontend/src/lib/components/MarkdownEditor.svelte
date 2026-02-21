<script>
    import {TabItem, Tabs, Textarea} from "flowbite-svelte";
    import Markdown from '$lib/components/Markdown.svelte'

    let {content, disabled} = $props();
</script>

<Tabs>
    <TabItem open title="Markdown">
        <Textarea rows="12" class="w-full" bind:value={content} {disabled}/>
    </TabItem>

    <TabItem title="Preview">
        <Markdown content={content}/>
    </TabItem>
</Tabs>



