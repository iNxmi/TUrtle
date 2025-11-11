<script>
    import {Button, Label, Input, Modal,Heading} from "flowbite-svelte";
    import {m} from '$lib/paraglide/messages.js';
    let changePassword = $state(false);
    let changeOTA = $state(false);

    let apiResponse = $state(null);
</script>

<form class="flex flex-col gap-5">
    <Heading tag="h3" class="text-center">{m.login__title()}</Heading>

    <Label>
        <span>_profile__username</span>
        <Input name="username" type="text" value="MaxMusterMannHD" disabled/>
    </Label>

    <div class="flex gap-5">
        <Label class="flex-1">
            <span>_profile__name</span>
            <Input name="name" type="text" value="Mustermann" disabled/>
        </Label>

        <Label class="flex-1">
            <span>_profile__surname</span>
            <Input name="surname" type="text" value="Max" disabled/>
        </Label>
    </div>

    <Label>
        <span>_profile__usergroup</span>
        <Input name="usergroup" type="text" value="Admin" disabled/>
    </Label>

    <Label>
        <span>_profile__email</span>
        <Input name="email" type="email" value="MaxMusterMann@Mustermail.io" disabled/>
    </Label>

    <Label>
        <span>_profile__study_field</span>
        <Input name="study_field" type="text" value="BWL" disabled/>
    </Label>

    <Label>
        <span>_profile__department</span>
        <Input name="department" type="text" value="67" disabled/>
    </Label>

    <Label>
        <span>_profile__studentID</span>
        <Input name="studentID" type="text" value="06069" disabled/>
    </Label>

    <div class="flex gap-5">
        <Button class="flex-1" onclick={() => changePassword = true}>_profile__password_change</Button>
        <Modal title="__profile_password_change_title" form bind:open={changePassword} onaction={({ action }) => alert(`Handle "${action}"`)}>
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
        <Button class="flex-1" onclick={() => changeOTA = true} >_profile__new_OTA</Button>
    </div>

</form>

{#if apiResponse}
    <p class="whitespace-pre">{JSON.stringify(apiResponse, null, 2)}</p>
{/if}
