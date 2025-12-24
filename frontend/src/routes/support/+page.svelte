<script>
    import {Button, Checkbox, Heading, Input, Label, Select, Textarea} from 'flowbite-svelte';
    import {m} from '$lib/paraglide/messages.js';
    import ReCAPTCHA from '$lib/components/ReCAPTCHA.svelte';
    import request from "$lib/api/api.js";

    const urgencies = [
        {value: "LOW", name: m.support__urgency_low()},
        {value: "MEDIUM", name: m.support__urgency_medium()},
        {value: "HIGH", name: m.support__urgency_high()},
        {value: "CRITICAL", name: m.support__urgency_critical()}
    ];

    const categories = [
        {value: "TECHNICAL", name: m.support__category_technical()},
        {value: "BILLING", name: m.support__category_billing()},
        {value: "GENERAL", name: m.support__category_general()},
        {value: "OTHER", name: m.support__category_other()}
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
            {m.support__title()}
        </Heading>

        <div class="flex gap-5">
            <Label class="flex flex-1 flex-col">
                <span>{m.support__urgency_label()}</span>
                <Select id="input_urgency" items={urgencies} required/>
            </Label>
            <Label class="flex flex-1 flex-col">
                <span>{m.support__category_label()}</span>
                <Select id="input_category" items={categories} required/>
            </Label>
        </div>

        <Label>
            <span>{m.support__email_label()}</span>
            <Input id="input_email" type="email" required/>
        </Label>

        <Label>
            <span>{m.support__subject_label()}</span>
            <Input id="input_subject" type="text" required/>
        </Label>

        <Label>
            <span>{m.support__description_label()}</span>
            <Textarea id="input_description" class="w-full" placeholder="_describe_your_issue" required/>
        </Label>

        <div class="flex items-center gap-5 justify-between">
            <Checkbox id="input_agree_tos" required>{m.support__i_agree_to_tos()}</Checkbox>
            <ReCAPTCHA/>
        </div>

        <Button type="submit">{m.support__button()}</Button>
    </form>
</div>