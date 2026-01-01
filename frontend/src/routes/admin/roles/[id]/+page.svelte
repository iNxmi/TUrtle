<script>
    import {Input, Label, Textarea} from "flowbite-svelte";
    import {m} from '$lib/paraglide/messages.js';

    let {data} = $props();
    let roles = $derived(data.role);
</script>

<form class="flex flex-col gap-5">
    <Label>
        <span>{m.admin_roles_id__id_label()}</span>
        <Input type="text" value={role.id} disabled/>
    </Label>

    <Label>
        <span>{m.admin_roles_id__name_label()}</span>
        <Input type="text" value={role.name} disabled/>
    </Label>

    <Label>
        <span>{m.admin_roles_id__permissions_label()}</span>
        <Textarea class="w-full" value={role.permissions.join("\r\n")} disabled/>
    </Label>

    <Label>
        <span>{m.admin_roles_id__created_at_label()}</span>
        <Input type="text" value={(new Date(role.createdAt)).toLocaleString()} disabled/>
    </Label>
</form>