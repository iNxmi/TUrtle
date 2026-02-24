<script>
    import {Input, Select, Textarea} from "flowbite-svelte";
    import {m} from '$lib/paraglide/messages.js';
    import Card from "$lib/components/Card.svelte";

    let {data} = $props();
    let ticket = $derived(data.ticket);

    //TODO get urgencies from backend
    const urgencies = [
        {value: "LOW", name: m.modal_contact_urgency_low()},
        {value: "MEDIUM", name: m.modal_contact_urgency_medium()},
        {value: "HIGH", name: m.modal_contact_urgency_high()},
        {value: "CRITICAL", name: m.modal_contact_urgency_critical()}
    ];

    //TODO get categories from backend
    const categories = [
        {value: "TECHNICAL", name: m.modal_contact_category_technical()},
        {value: "BILLING", name: m.modal_contact_category_billing()},
        {value: "GENERAL", name: m.modal_contact_category_general()},
        {value: "OTHER", name: m.modal_contact_category_other()}
    ];
</script>

<Card>
    <form class="flex flex-col gap-5">
        <div>
            <span>{m.manage_support_tickets_label_id()}</span>
            <Input type="text" value={ticket.id} disabled/>
        </div>

        <div class="flex gap-5">
            <div class="flex-1">
                <span>{m.manage_support_tickets_label_urgency()}</span>
                <Select items={urgencies} value={ticket.urgency} disabled/>
            </div>

            <div class="flex-1">
                <span>{m.manage_support_tickets_label_category()}</span>
                <Select items={categories} value={ticket.category} disabled/>
            </div>
        </div>

        <div>
            <span>{m.manage_support_tickets_label_email()}</span>
            <Input type="text" value={ticket.email} disabled/>
        </div>

        <div class="flex-1">
            <span>{m.manage_support_tickets_label_subject()}</span>
            <Input type="text" value={ticket.subject} disabled/>
        </div>

        <div class="flex-1">
            <span>{m.manage_support_tickets_label_content()}</span>
            <Textarea class="w-full" value={ticket.content} disabled/>
        </div>

        <div>
            <span>{m.manage_support_tickets_label_status()}</span>
            <Input type="text" value={ticket.status} disabled/>
        </div>

        <div class="flex gap-5">
            <div class="flex-1">
                <span>{m.manage_support_tickets_label_created_at()}</span>
                <Input type="text" value={(new Date(ticket.createdAt)).toLocaleString()} disabled/>
            </div>
            <div class="flex-1">
                <span>{m.manage_support_tickets_label_updated_at()}</span>
                <Input type="text" value={(new Date(ticket.updatedAt)).toLocaleString()} disabled/>
            </div>
        </div>
    </form>
</Card>