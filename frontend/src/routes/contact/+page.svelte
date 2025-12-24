<script>
    import {Button, Checkbox, Heading, Input, Hr, Label, Select, Textarea} from 'flowbite-svelte';
    import {m} from '$lib/paraglide/messages.js';
    import ReCAPTCHA from '$lib/components/ReCAPTCHA.svelte';
    import request from "$lib/api/api.js";
    import Markdown from "$lib/components/Markdown.svelte";

    const {data} = $props();
    const content = data.content;

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
            urgency: document.getElementById("input_urgency").value,
            category: document.getElementById("input_category").value,
            email: document.getElementById("input_email").value,
            subject: document.getElementById("input_subject").value,
            description: document.getElementById("input_description").value
        }

        const response = await request("/support", {
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
                <Select id="input_urgency" items={urgencies} required/>
            </Label>
            <Label class="flex flex-1 flex-col">
                <span>{m.contact__category_label()}</span>
                <Select id="input_category" items={categories} required/>
            </Label>
        </div>

        <Label>
            <span>{m.contact__email_label()}</span>
            <Input id="input_email" type="email" required/>
        </Label>

        <Label>
            <span>{m.contact__subject_label()}</span>
            <Input id="input_subject" type="text" required/>
        </Label>

        <Label>
            <span>{m.contact__description_label()}</span>
            <Textarea id="input_description" class="w-full" placeholder="_describe_your_issue" required/>
        </Label>

        <div class="flex items-center gap-5 justify-between">
            <Checkbox id="input_agree_tos" required>{m.contact__i_agree_to_tos()}</Checkbox>
            <ReCAPTCHA/>
        </div>

        <Button type="submit">{m.contact__button()}</Button>
    </form>

    <Hr/>

    <Markdown content={content}/>
</div>