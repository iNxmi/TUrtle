<script>
    import {Input, Label} from "flowbite-svelte";
    import {m} from '$lib/paraglide/messages.js';

    let {data} = $props();
    const user = data.user;
</script>

<form class="flex flex-col gap-5">
    <Label>
        <span>{m.admin_users_id__username_label()}</span>
        <Input type="text" value={user.username} disabled/>
    </Label>

    <div class="flex gap-5">
        <Label class="flex-1">
            <span>{m.admin_users_id__first_name_label()}</span>
            <Input ype="text" value={user.firstName} disabled/>
        </Label>

        <Label class="flex-1">
            <span>{m.admin_users_id__last_name_label()}</span>
            <Input type="text" value={user.lastName} disabled/>
        </Label>
    </div>

    <Label>
        <span>{m.admin_users_id__email_label()}</span>
        <Input type="text" value={user.email} disabled/>
    </Label>

    <Label>
        <span>{m.admin_users_id__student_id_label()}</span>
        <Input type="text" value={user.studentId} disabled/>
    </Label>

    <Label>
        <span>{m.admin_users_id__roles_label()}</span>
        <Input type="text" value={JSON.stringify(user.roles)} disabled/>
    </Label>

    <Label>
        <span>{m.admin_users_id__created_at_label()}</span>
        <Input type="text" value={(new Date(user.createdAt)).toLocaleString()} disabled/>
    </Label>
</form>