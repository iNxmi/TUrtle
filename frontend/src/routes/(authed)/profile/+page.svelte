<script>
    import {Button, Label, Input, Modal, Heading} from "flowbite-svelte";
    import {m} from '$lib/paraglide/messages.js';
    import {onMount} from "svelte";

    let changePassword = $state(false);
    let changeOTA = $state(false);

    let {data} = $props();
    const user = data.user

</script>

<form class="flex flex-col gap-5">
    <Heading tag="h3" class="text-center">{m.login__title()}</Heading>

    <Label>
        <span>_profile__username</span>
        <Input name="username" type="text" value={user.userName} disabled/>
    </Label>

    <div class="flex gap-5">
        <Label class="flex-1">
            <span>_profile__firstname</span>
            <Input name="first_name" type="text" value={user.firstName} disabled/>
        </Label>

        <Label class="flex-1">
            <span>_profile__surname</span>
            <Input name="last_name" type="text" value={user.lastName} disabled/>
        </Label>
    </div>

    <Label>
        <span>_profile__role</span>
        <Input name="role" type="text" value={user.role} disabled/>
    </Label>

    <Label>
        <span>_profile__email</span>
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
        <span>_profile__studentID</span>
        <Input name="studentID" type="text" value={user.studentId} disabled/>
    </Label>

    <div class="flex gap-5">
        <Button class="flex-1" onclick={() => changePassword = true}>_profile__password_change</Button>
        <Modal title="__profile_password_change_title" form bind:open={changePassword}
               onaction={({ action }) => alert(`Handle "${action}"`)}>
            <Label>
                <span>_profile__password_change__current_password</span>
                <Input name="current_password" type="password" value="" required/>
            </Label>
            <Label>
                <span>_profile__password_change__new_password</span>
                <Input name="new_password" type="password" value="" required/>
            </Label>
            <Label>
                <span>_profile__password_change__new_password_repeat</span>
                <Input name="new_password_repeat" type="password" value="" required/>
            </Label>

            {#snippet footer()}
                <Button type="submit" value="success">__profile__password_change_submit</Button>
            {/snippet}
        </Modal>
        <Button class="flex-1" onclick={() => changeOTA = true}>_profile__new_OTA</Button>
    </div>
</form>


