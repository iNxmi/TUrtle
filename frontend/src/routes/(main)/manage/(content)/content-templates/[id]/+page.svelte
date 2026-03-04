<script>
    import {Button, Input, Textarea} from "flowbite-svelte";
    import {m} from '$lib/paraglide/messages.js';
    import MarkdownEditor from '$lib/components/MarkdownEditor.svelte';
    import {ContentTemplates} from "$lib/api";

    let {data} = $props();
    let template = $derived(data.template);

    let name = $derived(template.name);
    let description = $derived(template.description);
    let content = $derived(template.content);

    let updatedTemplate = $derived({
        name: name !== template.name ? name : undefined,
        description,
        content
    });

    async function saveTemplate() {
        const response = await ContentTemplates.patch(template.id, updatedTemplate);
    }
</script>

<form class="flex flex-col gap-5">
    <div class="flex flex-row justify-between">
        <div class="flex flex-col">
            <span>{m.manage_content_templates_label_id()}</span>
            {template.id}
        </div>

        <Button class="cursor-pointer" onclick={saveTemplate}>_Save_</Button>
    </div>
    <div>
        <span>{m.manage_content_templates_label_name()}</span>
        <Input type="text" value={template.name}/>
    </div>

    <div>
        <span>{m.manage_content_templates_label_description()}</span>
        <Textarea class="w-full" value={template.description}/>
    </div>

    <div>
        <span>{m.manage_content_templates_label_content()}</span>
        <MarkdownEditor class="w-full" {content}/>
    </div>

    <div>
        <span>{m.manage_content_templates_label_created_at()}</span>
        <Input type="text" value={(new Date(template.createdAt)).toLocaleString()} disabled/>
    </div>
</form>