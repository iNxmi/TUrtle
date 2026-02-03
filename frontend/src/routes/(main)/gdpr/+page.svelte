<script>
    import {m} from '$lib/paraglide/messages.js';
    import {Heading} from 'flowbite-svelte';
    import Markdown from '$lib/components/Markdown.svelte';

    let {data} = $props();
    let content = $derived(data.content);
</script>

<div class="flex flex-col align-center gap-10">
    <Heading class="text-center m-0 p-0" tag="h3">{m.gdpr__title()}</Heading>

    <Markdown {content}/>
</div>