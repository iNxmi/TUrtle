<script>
    import {m} from "$lib/paraglide/messages.js";
    import {Input, Textarea} from "flowbite-svelte";
    import Card from "$lib/components/Card.svelte";

    let {data} = $props();
    let role = $derived(data.role);
</script>

<Card>
    <form class="flex flex-col gap-5">
        <div>
            <span>{m.manage_roles_label_id()}</span>
            <Input type="text" value={role.id} disabled/>
        </div>

        <div>
            <span>{m.manage_roles_label_name()}</span>
            <Input type="text" value={role.name} disabled/>
        </div>

        <!--TODO replace by multiselect-->
        <div>
            <span>{m.manage_roles_label_name()}</span>
            <Textarea class="w-full" value={JSON.stringify(role.permissions, null, 2)} disabled/>
        </div>

        <div class="flex gap-5">
            <div class="flex-1">
                <span>{m.manage_roles_label_created_at()}</span>
                <Input type="text" value={(new Date(role.createdAt)).toLocaleString()} disabled/>
            </div>
            <div class="flex-1">
                <span>{m.manage_roles_label_updated_at()}</span>
                <Input type="text" value={(new Date(role.updatedAt)).toLocaleString()} disabled/>
            </div>
        </div>
    </form>
</Card>