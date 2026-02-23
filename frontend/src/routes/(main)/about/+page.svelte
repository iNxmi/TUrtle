<script>
    import Markdown from '$lib/components/Markdown.svelte';
    import Card from "$lib/components/Card.svelte";

    let {data} = $props();
    let content = $derived(data.content);
</script>

<Card>
    <Markdown {content}/>
</Card>
