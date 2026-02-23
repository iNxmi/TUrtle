<script>
    import {Input, MultiSelect} from "flowbite-svelte";
    import {m} from '$lib/paraglide/messages.js';

    let {data} = $props();
    let user = $derived(data.user);
    let roles = $derived(data.roles);
</script>

<form class="bg-background-secondary rounded-2xl shadow-sm/30 p-5 flex flex-col gap-5">
    <div>
        <span>{m.manage_users_label_id()}</span>
        <Input type="text" value={user.id} disabled/>
    </div>

    <div>
        <span>{m.manage_users_label_username()}</span>
        <Input type="text" value={user.username} disabled/>
    </div>

    <div class="flex gap-5">
        <div class="flex-1">
            <span>{m.manage_users_label_first_name()}</span>
            <Input ype="text" value={user.firstName} disabled/>
        </div>

        <div class="flex-1">
            <span>{m.manage_users_label_last_name()}</span>
            <Input type="text" value={user.lastName} disabled/>
        </div>
    </div>

    <div>
        <span>{m.manage_users_label_email()}</span>
        <Input type="text" value={user.email} disabled/>
    </div>

    <div>
        <span>{m.manage_users_label_emojis()}</span>
        <Input type="text" value={user.emojis} disabled/>
    </div>

    <div>
        <span>{m.manage_users_label_roles()}</span>
        <MultiSelect items={roles.map((role) => ({value: role.id, name: role.name}))} value={user.roleIds} disabled/>
    </div>

    <div>
        <span>{m.manage_users_label_created_at()}</span>
        <Input type="text" value={(new Date(user.createdAt)).toLocaleString()} disabled/>
    </div>
</form>