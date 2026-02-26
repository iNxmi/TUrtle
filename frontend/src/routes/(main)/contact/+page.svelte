<script>
    import {Button, Checkbox, Input, Select, Textarea} from 'flowbite-svelte';
    import {m} from '$lib/paraglide/messages.js';
    import Altcha from '$lib/components/Altcha.svelte';
    import {SupportTickets} from "$lib/api";
    import Markdown from "$lib/components/Markdown.svelte";
    import Card from "$lib/components/Card.svelte";

    let urgency = $state('');
    let category = $state('');
    let email = $state('');
    let subject = $state('');
    let ticketContent = $state('');
    let altchaToken = $state('');

    const {data} = $props();
    let content = $derived(data.content);

    const urgencyItems = $derived(data.urgencies.map((urgency) => ({
        value: urgency.id,
        name: urgency.name
    })));
    let urgencyId = $state();

    const categoryItems = $derived(data.categories.map((category) => ({
        value: category.id,
        name: category.name
    })));
    let categoryId = $state();

    async function send(event) {
        event.preventDefault();

        const payload = {
            urgencyId: $state.snapshot(urgencyId),
            categoryId: $state.snapshot(categoryId),
            email: $state.snapshot(email),
            subject: $state.snapshot(subject),
            content: $state.snapshot(ticketContent),
            altchaToken: $state.snapshot(altchaToken)
        }

        const response = await SupportTickets.create(payload);
    }
</script>

<Card>
    <Markdown {content}/>
</Card>

<Card class="flex flex-col">
    <form class="flex-1 flex flex-col gap-5" onsubmit={send}>
        <div class="flex gap-5">
            <div class="flex flex-1 flex-col">
                <div>{m.modal_contact_label_urgency()}</div>
                <Select bind:value={urgencyId} items={urgencyItems} required/>
            </div>
            <div class="flex flex-1 flex-col">
                <div>{m.modal_contact_label_category()}</div>
                <Select bind:value={categoryId} items={categoryItems} required/>
            </div>
        </div>

        <div>
            <div>{m.modal_contact_label_email()}</div>
            <Input bind:value={email} type="email" required/>
        </div>

        <div>
            <div>{m.modal_contact_label_subject()}</div>
            <Input bind:value={subject} type="text" required/>
        </div>

        <div>
            <div>{m.modal_contact_label_content()}</div>
            <Textarea class="flex-1 w-full" bind:value={ticketContent}
                      placeholder={m.modal_contact_placeholder_content()} required/>
        </div>

        <div class="flex">
            <div class="flex flex-col justify-center">
                <Checkbox name="input_tos" required/>
            </div>
            <div>{m.modal_contact_label_i_agree_to_tos()}</div>
        </div>

        <Altcha bind:value={altchaToken}/>

        <Button type="submit">{m.modal_contact_button()}</Button>
    </form>
</Card>