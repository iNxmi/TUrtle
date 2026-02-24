<script>
    import {m} from "$lib/paraglide/messages.js";
    import {Input, Textarea} from "flowbite-svelte";
    import Card from "$lib/components/Card.svelte";

    let {data} = $props();
    let faq = $derived(data.faq);
</script>

<Card>
    <form class="flex flex-col gap-5">
        <div>
            <span>{m.manage_faq_label_id()}</span>
            <Input type="text" value={faq.id} disabled/>
        </div>

        <div>
            <span>{m.manage_faq_label_name()}</span>
            <Input type="text" value={faq.name} disabled/>
        </div>

        <div>
            <span>{m.manage_faq_label_title()}</span>
            <Input type="text" value={faq.title} disabled/>
        </div>

        <div>
            <span>{m.manage_faq_label_content()}</span>
            <Textarea class="w-full" value={faq.content} disabled/>
        </div>

        <div>
            <span>{m.manage_faq_label_enabled()}</span>
            <Input type="text" value={faq.enabled} disabled/>
        </div>

        <div class="flex gap-5">
            <div class="flex-1">
                <span>{m.manage_faq_label_created_at()}</span>
                <Input type="text" value={(new Date(faq.createdAt)).toLocaleString()} disabled/>
            </div>
            <div class="flex-1">
                <span>{m.manage_faq_label_updated_at()}</span>
                <Input type="text" value={(new Date(faq.updatedAt)).toLocaleString()} disabled/>
            </div>
        </div>
    </form>
</Card>