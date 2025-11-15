<script>
    import {Button, Label, Input, Modal, Heading} from "flowbite-svelte";
    import {m} from '$lib/paraglide/messages.js';

    let changePassword = $state(false);
    let changeOTA = $state(false);

    let {data} = $props();
    const user = data.user;
</script>

<form class="flex flex-col gap-5">
    <Heading tag="h3" class="text-center">{m.profile__title({username: user.username})}</Heading>

    <Label>
        <span>{m.profile__username_label()}</span>
        <Input name="username" type="text" value={user.username} disabled/>
    </Label>

    <div class="flex gap-5">
        <Label class="flex-1">
            <span>{m.profile__first_name_label()}</span>
            <Input name="first_name" type="text" value={user.firstName} disabled/>
        </Label>

        <Label class="flex-1">
            <span>{m.profile__last_name_label()}</span>
            <Input name="last_name" type="text" value={user.lastName} disabled/>
        </Label>
    </div>

    <Label>
        <span>{m.profile__email_label()}</span>
        <Input name="email" type="text" value={user.email} disabled/>
    </Label>

    <!--    <Label>-->
    <!--        <span>_profile__study_field</span>-->
    <!--        <Input name="study_field" type="text" value="__BWL" disabled/>-->
    <!--    </Label>-->

    <!--    <Label>-->
    <!--        <span>_profile__department</span>-->
    <!--        <Input name="department" type="text" value="__67" disabled/>-->
    <!--    </Label>-->

    <Label>
        <span>{m.profile__student_id_label()}</span>
        <Input name="studentID" type="text" value={user.studentId} disabled/>
    </Label>

    <Label>
        <span>{m.profile__role_label()}</span>
        <Input name="role" type="text" value={user.role} disabled/>
    </Label>

    <Label>
        <span>{m.profile__created_at_label()}</span>
        <Input type="text" value={(new Date(user.createdAt)).toLocaleString()} disabled/>
    </Label>

    <div class="flex gap-5">
        <Button class="flex-1" onclick={() => changePassword = true}>{m.profile__password_change_button()}</Button>
        <Modal title={m.profile__password_change_title()} form bind:open={changePassword}
               onaction={({ action }) => alert(`Handle "${action}"`)}>
            <Label>
                <span>{m.profile__password_change__current_password_label()}</span>
                <Input name="current_password" type="password" value="" required/>
            </Label>
            <Label>
                <span>{m.profile__password_change__new_password_label()}</span>
                <Input name="new_password" type="password" value="" required/>
            </Label>
            <Label>
                <span>{m.profile__password_change__new_password_repeat_label()}</span>
                <Input name="new_password_repeat" type="password" value="" required/>
            </Label>

            {#snippet footer()}
                <Button type="submit" value="success">{m.profile__password_change__submit_button()}</Button>
            {/snippet}
        </Modal>
        <Button class="flex-1" onclick={() => changeOTA = true}>{m.profile__new_ota_button()}</Button>
    </div>
</form>


