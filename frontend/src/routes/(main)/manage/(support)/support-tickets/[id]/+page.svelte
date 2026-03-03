<script>
    import {Input, Select, Textarea} from "flowbite-svelte";
    import {m} from '$lib/paraglide/messages.js';
    import Card from "$lib/components/Card.svelte";

    let {data} = $props();
    let ticket = $derived(data.ticket);

    const urgencyItems = $derived(data.urgencies.map((urgency) => ({
        value: urgency.id,
        name: urgency.name
    })));

    const categoryItems = $derived(data.categories.map((category) => ({
        value: category.id,
        name: category.name
    })));
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
                <Select items={urgencyItems} value={ticket.urgencyId} disabled/>
            </div>

            <div class="flex-1">
                <span>{m.manage_support_tickets_label_category()}</span>
                <Select items={categoryItems} value={ticket.categoryId} disabled/>
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