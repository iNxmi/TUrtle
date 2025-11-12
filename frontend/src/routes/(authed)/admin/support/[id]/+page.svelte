<script>
    import {Label, Input, Textarea, Heading} from "flowbite-svelte";
    import {m} from '$lib/paraglide/messages.js';

    let {data} = $props();
    const ticket = data.ticket
</script>

<form class="flex flex-col gap-5">
    <Heading tag="h3" class="text-center">{`${ticket.urgency}: ${ticket.subject}`}</Heading>

    <div class="flex gap-5">
        <Label class="flex-1">
            <span>{m.admin_support_id__id_label()}</span>
            <Input type="text" value={ticket.id} disabled/>
        </Label>

        <Label class="flex-1">
            <span>{m.admin_support_id__urgency_label()}</span>
            <Input type="text" value={ticket.urgency} disabled/>
        </Label>

        <Label class="flex-1">
            <span>{m.admin_support_id__category_label()}</span>
            <Input type="text" value={ticket.category} disabled/>
        </Label>
    </div>

    <Label>
        <span>{m.admin_support_id__email_label()}</span>
        <Input type="text" value={ticket.email} disabled/>
    </Label>

    <Label>
        <span>{m.admin_support_id__subject_label()}</span>
        <Input type="text" value={ticket.subject} disabled/>
    </Label>

    <Label>
        <span>{m.admin_support_id__description_label()}</span>
        <Textarea value={ticket.description} class="w-full" disabled></Textarea>
    </Label>
</form>


