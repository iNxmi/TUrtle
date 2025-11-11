<script>
    import {Button, Checkbox, Label, Input, Heading, A, P} from "flowbite-svelte";
    import {m} from '$lib/paraglide/messages.js';

    let apiResponse = $state(null);
</script>

<form class="flex flex-col gap-5" onsubmit={login}>
    <Heading tag="h3" class="text-center">{m.login__title()}</Heading>

    <Label>
        <span>_profile__username</span>
        <Input name="username" type="text" value="MaxMusterMannHD" required disabled/>
    </Label>

    <div class="flex gap-5">
        <Label class="flex-1">
            <span>_profile__name</span>
            <Input name="name" type="text" value="Mustermann" required disabled/>
        </Label>

        <Label class="flex-1">
            <span>_profile__surname</span>
            <Input name="surname" type="text" value="Max" required disabled/>
        </Label>
    </div>

    <Label>
        <span>_profile__usergroup</span>
        <Input name="usergroup" type="text" value="Admin" required disabled/>
    </Label>

    <Label>
        <span>_profile__email</span>
        <Input name="email" type="email" value="MaxMusterMann@Mustermail.io" required disabled/>
    </Label>

    <Label>
        <span>_profile__study_field</span>
        <Input name="study_field" type="text" value="BWL" required disabled/>
    </Label>

    <Label>
        <span>_profile__department</span>
        <Input name="department" type="text" value="67" required disabled/>
    </Label>

    <Label>
        <span>_profile__studentID</span>
        <Input name="studentID" type="text" value="06069" required disabled/>
    </Label>

    <div class="flex gap-5">
        <Button class="flex-1" type="button">_profile__password_change</Button>
        <Button class="flex-1" type="button">_profile__new_OTA</Button>
    </div>

</form>

{#if apiResponse}
    <p class="whitespace-pre">{JSON.stringify(apiResponse, null, 2)}</p>
{/if}
