<script>
    import {A, Accordion, AccordionItem} from 'flowbite-svelte';
    import {m} from '$lib/paraglide/messages.js';
    import Markdown from '$lib/components/Markdown.svelte';

    let {data} = $props();
    let all = $derived(data.all);
</script>

<div class="flex flex-col gap-5">
    <Accordion class="bg-background-secondary dark:border-none" multiple>
        {#each all as faq}
            <AccordionItem>
                {#snippet header()}
                    <span>
                        {faq.id}. {faq.title} ({new Date(faq.updatedAt).toLocaleDateString()})
                    </span>
                {/snippet}
                <Markdown content={faq.content}/>
            </AccordionItem>
        {/each}
    </Accordion>

    <A href="/contact">
        {m.faq_label_problem_not_solved()}
    </A>
</div>