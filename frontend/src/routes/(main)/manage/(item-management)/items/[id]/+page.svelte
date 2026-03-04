<script>
    import {m} from "$lib/paraglide/messages.js";
    import {Input, Textarea} from "flowbite-svelte";
    import Card from "$lib/components/Card.svelte";

    let {data} = $props();
    let item = $derived(data.item);
</script>

<Card>
    <form class="flex flex-col gap-5">
        <div>
            <span>{m.manage_items_label_id()}</span>
            <Input type="text" value={item.id} disabled/>
        </div>

        <div>
            <span>{m.manage_items_label_name()}</span>
            <Input type="text" value={item.name} disabled/>
        </div>

        <div>
            <span>{m.manage_items_label_category_id()}</span>
            <Input type="text" value={item.categoryId} disabled/>
        </div>

        <div>
            <span>{m.manage_items_label_locker_id()}</span>
            <Input type="text" value={item.lockerId} disabled/>
        </div>

        <div>
            <span>{m.manage_items_label_description()}</span>
            <Textarea class="w-full" value={item.description} disabled/>
        </div>

        <div class="flex-1">
            <span>{m.manage_items_label_needs_confirmation()}</span>
            <Input type="text" value={item.needsConfirmation} disabled/>
        </div>

        <div class="flex-1">
            <span>{m.manage_items_label_acquired_at()}</span>
            <Input type="text" value={(new Date(item.acquiredAt)).toLocaleString()} disabled/>
        </div>

        <div class="flex gap-5">
            <div class="flex-1">
                <span>{m.manage_items_label_created_at()}</span>
                <Input type="text" value={(new Date(item.createdAt)).toLocaleString()} disabled/>
            </div>
            <div class="flex-1">
                <span>{m.manage_items_label_updated_at()}</span>
                <Input type="text" value={(new Date(item.updatedAt)).toLocaleString()} disabled/>
            </div>
        </div>
    </form>
</Card>