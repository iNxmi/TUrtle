<script>
    import {Button, Checkbox, Input, Select, Textarea} from 'flowbite-svelte';
    import {m} from '$lib/paraglide/messages.js';
    import Altcha from '$lib/components/Altcha.svelte';
    import request from "$lib/api/api.js";
    import Markdown from "$lib/components/Markdown.svelte";
    import Card from "$lib/components/Card.svelte";

    let urgency = $state('');
    let category = $state('');
    let email = $state('');
    let subject = $state('');
    let description = $state('');
    let altchaToken = $state('');

    const {data} = $props();
    let content = $derived(data.content);

    const urgencies = [
        {value: "LOW", name: m.modal_contact_urgency_low()},
        {value: "MEDIUM", name: m.modal_contact_urgency_medium()},
        {value: "HIGH", name: m.modal_contact_urgency_high()},
        {value: "CRITICAL", name: m.modal_contact_urgency_critical()}
    ];

    const categories = [
        {value: "TECHNICAL", name: m.modal_contact_category_technical()},
        {value: "BILLING", name: m.modal_contact_category_billing()},
        {value: "GENERAL", name: m.modal_contact_category_general()},
        {value: "OTHER", name: m.modal_contact_category_other()}
    ];

    async function send(event) {
        event.preventDefault();

        const payload = {
            urgency: $state.snapshot(urgency),
            category: $state.snapshot(category),
            email: $state.snapshot(email),
            subject: $state.snapshot(subject),
            description: $state.snapshot(description),
            altchaToken: $state.snapshot(altchaToken)
        }

        const response = await request("/api/support-tickets", {
            method: "POST",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });
    }
</script>

<Card>
    <Markdown {content}/>
</Card>

<form class="bg-background-secondary rounded-2xl shadow-sm/30 p-5 flex flex-col gap-5" onsubmit={send}>
    <div class="flex gap-5">
        <div class="flex flex-1 flex-col">
            <span>{m.modal_contact_label_urgency()}</span>
            <Select bind:value={urgency} items={urgencies} required/>
        </div>
        <div class="flex flex-1 flex-col">
            <span>{m.modal_contact_label_category()}</span>
            <Select bind:value={category} items={categories} required/>
        </div>
    </div>

    <div>
        <span>{m.modal_contact_label_email()}</span>
        <Input bind:value={email} type="email" required/>
    </div>

    <div>
        <span>{m.modal_contact_label_subject()}</span>
        <Input bind:value={subject} type="text" required/>
    </div>

    <div>
        <span>{m.modal_contact_label_description()}</span>
        <Textarea bind:value={description} class="w-full" placeholder={m.modal_contact_placeholder_description()}
                  required/>
    </div>

    <Checkbox required>{m.modal_contact_label_i_agree_to_tos()}</Checkbox>

    <Altcha bind:value={altchaToken}/>

    <Button type="submit">{m.modal_contact_button()}</Button>
</form>