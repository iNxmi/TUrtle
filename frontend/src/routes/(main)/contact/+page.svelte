<script>
    import {Button, Checkbox, Heading, Hr, Input, Label, Select, Textarea} from 'flowbite-svelte';
    import {m} from '$lib/paraglide/messages.js';
    import Altcha from '$lib/components/Altcha.svelte';
    import request from "$lib/api/api.js";
    import Markdown from "$lib/components/Markdown.svelte";

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

        const response = await request("/support-tickets", {
            method: "POST",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });
    }
</script>

<div class="flex flex-col gap-10">
    <form class="flex flex-col gap-5" onsubmit={send}>
        <Heading class="text-center" tag="h3">
            {m.modal_contact_title()}
        </Heading>

        <div class="flex gap-5">
            <Label class="flex flex-1 flex-col">
                <span>{m.modal_contact_label_urgency()}</span>
                <Select bind:value={urgency} items={urgencies} required/>
            </Label>
            <Label class="flex flex-1 flex-col">
                <span>{m.modal_contact_label_category()}</span>
                <Select bind:value={category} items={categories} required/>
            </Label>
        </div>

        <Label>
            <span>{m.modal_contact_label_email()}</span>
            <Input bind:value={email} type="email" required/>
        </Label>

        <Label>
            <span>{m.modal_contact_label_subject()}</span>
            <Input bind:value={subject} type="text" required/>
        </Label>

        <Label>
            <span>{m.modal_contact_label_description()}</span>
            <Textarea bind:value={description} class="w-full" placeholder={m.modal_contact_placeholder_description()}
                      required/>
        </Label>

        <Checkbox required>{m.modal_contact_label_i_agree_to_tos()}</Checkbox>

        <Altcha bind:value={altchaToken}/>

        <Button type="submit">{m.modal_contact_button()}</Button>
    </form>

    <Hr class="m-0 p-0"/>

    <Markdown content={content}/>
</div>