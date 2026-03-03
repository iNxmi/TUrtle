<script>
    import {m} from "$lib/paraglide/messages.js";
    import {Input} from "flowbite-svelte";
    import Card from "$lib/components/Card.svelte";

    let {data} = $props();
    let category = $derived(data.category);
</script>

<Card>
    <form class="flex flex-col gap-5">
        <div>
            <span>{m.manage_item_categories_label_id()}</span>
            <Input type="text" value={category.id} disabled/>
        </div>

        <div>
            <span>{m.manage_item_categories_label_name()}</span>
            <Input type="text" value={category.name} disabled/>
        </div>

        <div class="flex gap-5">
            <div class="flex-1">
                <span>{m.manage_item_categories_label_created_at()}</span>
                <Input type="text" value={(new Date(category.createdAt)).toLocaleString()} disabled/>
            </div>
            <div class="flex-1">
                <span>{m.manage_item_categories_label_updated_at()}</span>
                <Input type="text" value={(new Date(category.updatedAt)).toLocaleString()} disabled/>
            </div>
        </div>
    </form>
</Card>