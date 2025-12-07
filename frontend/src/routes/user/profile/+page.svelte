<script>
    import {Button, Heading, Input, Label, Modal} from "flowbite-svelte";
    import {m} from '$lib/paraglide/messages.js';
    let changePasswordModal = $state(false);
    let changeOTA = $state(false);

    import request from "$lib/api/api.js";
    async function logout() {
        await request("/auth/logout")
    }
    async function changePassword(){
        let newPassword = document.getElementById("new_password").value
        let newPasswordRepeat = document.getElementById("new_password_repeat").value

        if(newPassword !== newPasswordRepeat){
            alert("__New Password does not match!!! PLZ Enter the same password!!__")
            return;
        }

        const payload = {
            password: document.getElementById("new_password").value
        }

        const response = await request("/user/profile",{
            method: 'PATCH',
            body: JSON.stringify(payload),
            headers: {'Content-Type': 'application/json'}
        })
        alert(JSON.stringify(await response.json()));
    }

    let {data} = $props();
    // user is already in data prop because it is defined in +layout.js so no need to create a +page.js to load it again :)
    const user = data.user;
</script>

<form class="flex flex-col gap-5">
    <Heading tag="h3" class="text-center">{m.profile__title({username: user.username})}</Heading>

    <Label>
        <span>{m.profile__username_label()}</span>
        <Input id="username" type="text" value={user.username} disabled/>
    </Label>

    <div class="flex gap-5">
        <Label class="flex-1">
            <span>{m.profile__first_name_label()}</span>
            <Input id="first_name" type="text" value={user.firstName} disabled/>
        </Label>

        <Label class="flex-1">
            <span>{m.profile__last_name_label()}</span>
            <Input id="last_name" type="text" value={user.lastName} disabled/>
        </Label>
    </div>

    <Label>
        <span>{m.profile__email_label()}</span>
        <Input id="email" type="text" value={user.email} disabled/>
    </Label>

    <Label>
        <span>{m.profile__role_label()}</span>
        <Input id="role" type="text" value={user.role} disabled/>
    </Label>

    <Label>
        <span>{m.profile__created_at_label()}</span>
        <Input type="text" value={(new Date(user.createdAt)).toLocaleString()} disabled/>
    </Label>

    <div class="flex gap-5">
        <Button class="flex-1" onclick={() => changePasswordModal = true}>{m.profile__password_change_button()}</Button>
        <Button class="flex-1" onclick={() => changeOTA = true}>{m.profile__new_ota_button()}</Button>
        <Button class="flex-1" color="orange" onclick={logout}>Logout</Button>
    </div>

    <Modal title={m.profile__password_change_title()} form bind:open={changePasswordModal}
           onaction={({ action }) => alert(`Handle "${action}"`)}>
        <Label>
            <span>{m.profile__password_change__current_password_label()}</span>
            <Input id="current_password" type="password" value="" required/>
        </Label>
        <Label>
            <span>{m.profile__password_change__new_password_label()}</span>
            <Input id="new_password" type="password" required/>
        </Label>
        <Label>
            <span>{m.profile__password_change__new_password_repeat_label()}</span>
            <Input id="new_password_repeat" type="password" required/>
        </Label>

        {#snippet footer()}
            <Button type="submit" onclick={changePassword}>{m.profile__password_change__submit_button()}</Button>
        {/snippet}
    </Modal>
</form>



