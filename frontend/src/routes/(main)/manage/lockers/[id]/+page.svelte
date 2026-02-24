<script>
    import {m} from "$lib/paraglide/messages.js";
    import {Button, Input, ButtonGroup} from "flowbite-svelte";
    import Card from "$lib/components/Card.svelte";

    let {data} = $props();
    let locker = $derived(data.locker);
</script>

<Card>
    <form class="flex flex-col gap-5">
        <div>
            <span>{m.manage_lockers_label_id()}</span>
            <Input type="text" value={locker.id} disabled/>
        </div>

        <div>
            <span>{m.manage_lockers_label_name()}</span>
            <Input type="text" value={locker.name} disabled/>
        </div>

        <div>
            <span>{m.manage_lockers_label_index()}</span>
            <Input type="text" value={locker.index} disabled/>
        </div>

        <div class="flex gap-5">
            <div class="flex-1">
                <span>{m.manage_lockers_label_created_at()}</span>
                <Input type="text" value={(new Date(locker.createdAt)).toLocaleString()} disabled/>
            </div>
            <div class="flex-1">
                <span>{m.manage_lockers_label_updated_at()}</span>
                <Input type="text" value={(new Date(locker.updatedAt)).toLocaleString()} disabled/>
            </div>
        </div>
    </form>
</Card>