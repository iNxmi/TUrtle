<script>
    import {Input, Label, Textarea} from "flowbite-svelte";
    import {m} from '$lib/paraglide/messages.js';

    let {data} = $props();
    const template = data.template;
</script>

<form class="flex flex-col gap-5">
    <Label>
        <span>_template_id_</span>
        <Input type="text" value={template.id} disabled/>
    </Label>

    <div class="flex gap-5">
        <Label class="flex-1">
            <span>_template_name_</span>
            <Input ype="text" value={template.name} disabled/>
        </Label>

        <Label class="flex-1">
            <span>_template_description_</span>
            <Input type="text" value={template.description} disabled/>
        </Label>
    </div>

    <Label>
        <span>_template_content_</span>
        <Textarea class="w-full" value={template.content} disabled/>
    </Label>

    <Label>
        <span>_template_created_at_</span>
        <Input type="text" value={(new Date(template.createdAt)).toLocaleString()} disabled/>
    </Label>
</form>