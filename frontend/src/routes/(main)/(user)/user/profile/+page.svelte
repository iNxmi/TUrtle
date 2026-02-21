<script>
    import {Input, Label} from "flowbite-svelte";
    import {m} from '$lib/paraglide/messages.js';

    let {data} = $props();
    let user = $derived(data.user);
</script>

<form class="flex flex-col gap-5">
    <Label>
        <span>{m.user_profile_label_username()}</span>
        <Input id="username" type="text" value={user.username} disabled/>
    </Label>

    <div class="flex gap-5">
        <Label class="flex-1">
            <span>{m.user_profile_label_first_name()}</span>
            <Input id="first_name" type="text" value={user.firstName} disabled/>
        </Label>

        <Label class="flex-1">
            <span>{m.user_profile_label_last_name()}</span>
            <Input id="last_name" type="text" value={user.lastName} disabled/>
        </Label>
    </div>

    <Label>
        <span>{m.user_profile_label_email()}</span>
        <Input id="email" type="text" value={user.email} disabled/>
    </Label>

    <Label>
        <span>{m.user_profile_label_roles()}</span>
        <Input name="roles" type="text" value={JSON.stringify(user.roles)} disabled/>
    </Label>

    <Label>
        <span>{m.user_profile_label_created_at()}</span>
        <Input type="text" value={(new Date(user.createdAt)).toLocaleString()} disabled/>
    </Label>
</form>


