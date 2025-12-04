<script>
    import {Heading, A} from 'flowbite-svelte';
    import {m} from '$lib/paraglide/messages.js';
    import Markdown from "svelte-exmarkdown";

    let {data} = $props();
    const all = data.all;
</script>

<div class="flex flex-col align-center gap-10">
    <Heading class="text-center m-0 p-0" tag="h3">
        {m.faq__title()}
    </Heading>

    <div class="flex flex-col gap-5">
        {#each all as faq, index}
            <div class="flex flex-col">
                <Heading tag="h4">
                    {`${index + 1}. ${faq.title}`}
                </Heading>
                <!--TODO make the markdown be styled since tailwindcss removes all styling-->
                <Markdown md={faq.content}/>
            </div>
        {/each}
    </div>

    <A class="text-center" href="/support">{m.faq__problem_not_solved()}</A>
</div>