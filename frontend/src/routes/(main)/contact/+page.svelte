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
        {value: "LOW", name: m.contact__urgency_low()},
        {value: "MEDIUM", name: m.contact__urgency_medium()},
        {value: "HIGH", name: m.contact__urgency_high()},
        {value: "CRITICAL", name: m.contact__urgency_critical()}
    ];

    const categories = [
        {value: "TECHNICAL", name: m.contact__category_technical()},
        {value: "BILLING", name: m.contact__category_billing()},
        {value: "GENERAL", name: m.contact__category_general()},
        {value: "OTHER", name: m.contact__category_other()}
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
            {m.contact__title()}
        </Heading>

        <div class="flex gap-5">
            <Label class="flex flex-1 flex-col">
                <span>{m.contact__urgency_label()}</span>
                <Select bind:value={urgency} items={urgencies} required/>
            </Label>
            <Label class="flex flex-1 flex-col">
                <span>{m.contact__category_label()}</span>
                <Select bind:value={category} items={categories} required/>
            </Label>
        </div>

        <Label>
            <span>{m.contact__email_label()}</span>
            <Input bind:value={email} type="email" required/>
        </Label>

        <Label>
            <span>{m.contact__subject_label()}</span>
            <Input bind:value={subject} type="text" required/>
        </Label>

        <Label>
            <span>{m.contact__description_label()}</span>
            <Textarea bind:value={description} class="w-full" placeholder="_describe_your_issue" required/>
        </Label>

        <Checkbox required>{m.contact__i_agree_to_tos()}</Checkbox>

        <Altcha bind:value={altchaToken}/>

        <Button type="submit">{m.contact__button()}</Button>
    </form>

    <Hr/>

    <Markdown content={content}/>
</div>