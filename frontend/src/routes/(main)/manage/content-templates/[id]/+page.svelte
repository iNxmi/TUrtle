<script>
    import {Input, Label, Textarea, Button} from "flowbite-svelte";
    import {m} from '$lib/paraglide/messages.js';
    import MarkdownEditor from '$lib/components/MarkdownEditor.svelte';
	import request from "$lib/api/api.js";
    import { contentTemplatesPath } from "$lib/backend.js";

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

        const response = request(contentTemplatesPath+`/${template.id}`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body:JSON.stringify(updatedTemplate)
        });
    }
</script>

<form class="flex flex-col gap-5">
    <div class="flex flex-row justify-between">
        <Label class="flex flex-col">
            <span>{m.admin_templates_id__id_label()}</span>
            {template.id}
            </Label> 
            
        <Button class="cursor-pointer" onclick={saveTemplate}>_Save_</Button>
    </div>
    <Label>
        <span>{m.admin_templates_id__name_label()}</span>
        <Input type="text" value={template.name}/>
    </Label>

    <Label>
        <span>{m.admin_templates_id__description_label()}</span>
        <Textarea class="w-full" value={template.description}/>
    </Label>

    <Label>
        <span>{m.admin_templates_id__content_label()}</span>
        <MarkdownEditor class="w-full" {content} />
    </Label>

    <Label>
        <span>{m.admin_templates_id__created_at_label()}</span>
        <Input type="text" value={(new Date(template.createdAt)).toLocaleString()} disabled/>
    </Label>
</form>