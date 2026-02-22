<script>
    import {Input, Label} from "flowbite-svelte";
    import {m} from '$lib/paraglide/messages.js';

    let {data} = $props();
    let user = $derived(data.user);
</script>

<form class="flex flex-col gap-5">
    <Label>
        <span>{m.manage_users_label_id()}</span>
        <Input type="text" value={user.id} disabled/>
    </Label>

    <Label>
        <span>{m.manage_users_label_username()}</span>
        <Input type="text" value={user.username} disabled/>
    </Label>

    <div class="flex gap-5">
        <Label class="flex-1">
            <span>{m.manage_users_label_first_name()}</span>
            <Input ype="text" value={user.firstName} disabled/>
        </Label>

        <Label class="flex-1">
            <span>{m.manage_users_label_last_name()}</span>
            <Input type="text" value={user.lastName} disabled/>
        </Label>
    </div>

    <Label>
        <span>{m.manage_users_label_email()}</span>
        <Input type="text" value={user.email} disabled/>
    </Label>

    <Label>
        <span>{m.manage_users_label_roles()}</span>
        <Input type="text" value={JSON.stringify(user.roles)} disabled/>
    </Label>

    <Label>
        <span>{m.manage_users_label_created_at()}</span>
        <Input type="text" value={(new Date(user.createdAt)).toLocaleString()} disabled/>
    </Label>
</form>