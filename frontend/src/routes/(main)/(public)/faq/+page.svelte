<script>
    import {A, Accordion, AccordionItem, Heading} from 'flowbite-svelte';
    import {m} from '$lib/paraglide/messages.js';
    import Markdown from '$lib/components/Markdown.svelte';

    let {data} = $props();
    let all = $derived(data.all);
</script>

<div class="flex flex-col align-center gap-10">
    <Heading class="text-center m-0 p-0" tag="h3">
        {m.faq_title()}
    </Heading>

    <Accordion flush multiple>
        {#each all as faq, index}
            <AccordionItem>
                {#snippet header()}
                    {`${index + 1}. ${faq.title}`}
                {/snippet}
                <Markdown content={faq.content}/>
            </AccordionItem>
        {/each}
    </Accordion>

    <A href="/contact">{m.faq_label_problem_not_solved()}</A>
</div>