<script>
    import {m} from "$lib/paraglide/messages.js";
    import {Button, Input, Toggle} from "flowbite-svelte";
    import {EditOutline, ShareNodesOutline} from "flowbite-svelte-icons";
    import Card from "$lib/components/Card.svelte";

    let {data} = $props();
    let locker = $derived(data.locker);
</script>

<div class="flex flex-col lg:flex-row gap-5">
    <Card>
        <div class="flex lg:flex-col gap-5">
            <Button><ShareNodesOutline/></Button>
            <Button><EditOutline/></Button>
        </div>
    </Card>

    <Card class="grow">
        <form class="flex flex-col gap-5">
            <div>
                <div>{m.manage_lockers_label_id()}</div>
                <Input type="text" value={locker.id} disabled/>
            </div>

            <div>
                <div>{m.manage_lockers_label_name()}</div>
                <Input type="text" value={locker.name} disabled/>
            </div>

            <div>
                <div>{m.manage_lockers_label_index()}</div>
                <Input type="text" value={locker.index} disabled/>
            </div>

            <div class="flex gap-5">
                <div class="flex-1">
                    <div>{m.manage_lockers_label_software_unlockable()}</div>
                    <Toggle checked={locker.softwareUnlockable} disabled/>
                </div>
                <div class="flex-1">
                    <div>{m.manage_lockers_label_locked()}</div>
                    <Toggle checked={locker.locked} disabled/>
                </div>
            </div>

            <div class="flex gap-5">
                <div class="flex-1">
                    <div>{m.manage_lockers_label_created_at()}</div>
                    <Input type="text" value={(new Date(locker.createdAt)).toLocaleString()} disabled/>
                </div>
                <div class="flex-1">
                    <div>{m.manage_lockers_label_updated_at()}</div>
                    <Input type="text" value={(new Date(locker.updatedAt)).toLocaleString()} disabled/>
                </div>
            </div>
        </form>
    </Card>
</div>