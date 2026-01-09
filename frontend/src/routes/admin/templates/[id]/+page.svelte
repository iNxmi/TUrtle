<script>
    import {Input, Label, Textarea} from "flowbite-svelte";
    import {m} from '$lib/paraglide/messages.js';
    import MarkdownEditor from '$lib/components/MarkdownEditor.svelte';

    let {data} = $props();
    const template = data.template;
</script>

<form class="flex flex-col gap-5">
    <Label>
        <span>{m.admin_templates_id__id_label()}</span>
        <Input type="text" value={template.id} disabled/>
    </Label>

    <Label>
        <span>{m.admin_templates_id__name_label()}</span>
        <Input type="text" value={template.name} disabled/>
    </Label>

    <Label>
        <span>{m.admin_templates_id__description_label()}</span>
        <Textarea class="w-full" value={template.description} disabled/>
    </Label>

    <Label>
        <span>{m.admin_templates_id__content_label()}</span>
        <MarkdownEditor class="w-full" content={template.content} disabled/>
    </Label>

    <Label>
        <span>{m.admin_templates_id__created_at_label()}</span>
        <Input type="text" value={(new Date(template.createdAt)).toLocaleString()} disabled/>
    </Label>
</form>