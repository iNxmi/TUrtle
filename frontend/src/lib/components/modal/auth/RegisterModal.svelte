<script>
    import {A, Button, Checkbox, Heading, Hr, Input, Modal} from "flowbite-svelte";
    import PasswordInput from "$lib/components/PasswordInput.svelte";
    import {m} from '$lib/paraglide/messages.js';
    import {Users} from "$lib/api";
    import Altcha from "$lib/components/Altcha.svelte";
    import {invalidateAll} from "$app/navigation";

    let username = $state("");
    let firstName = $state("");
    let lastName = $state("");
    let email = $state("");
    let password = $state("");
    let passwordRepeat = $state("");
    let altchaToken = $state("");

    let {
        isTrusted = false,
        open = $bindable(false)
    } = $props();

    async function register(event) {
        event.preventDefault();

        const payload = {
            username: $state.snapshot(username),
            firstName: $state.snapshot(firstName),
            lastName: $state.snapshot(lastName),
            email: $state.snapshot(email),
            password: $state.snapshot(password),
            altchaToken: $state.snapshot(altchaToken)
        };

        const response = await Users.create(payload);

        if (response.status !== 201)
            return;

        await invalidateAll()
        open = false
    }
</script>

<Modal form bind:open={open} outsideclose={false}>
    <form class="flex flex-col gap-5" onsubmit={register}>
        <Heading tag="h3" class="text-center">
            {m.modal_register_title()}
        </Heading>

        <Hr class="m-0 p-0"/>

        <div>
            <span>{m.modal_register_label_username()}</span>
            <Input name="input_username" bind:value={username} type="text" required/>
        </div>

        <div class="flex gap-5">
            <div class="flex-1">
                <span>{m.modal_register_label_first_name()}</span>
                <Input name="input_first_name" bind:value={firstName} type="text" required/>
            </div>
            <div class="flex-1">
                <span>{m.modal_register_label_last_name()}</span>
                <Input name="input_last_name" bind:value={lastName} type="text" required/>
            </div>
        </div>

        <div>
            <span>{m.modal_register_label_email()}</span>
            <Input name="input_email" bind:value={email} type="email" required/>
        </div>

        <div class="flex gap-5">
            <div class="flex-1">
                <span>{m.modal_register_label_password()}</span>
                <PasswordInput name="input_password" bind:value={password} required/>
            </div>
            <div class="flex-1">
                <span>{m.modal_register_label_password_repeat()}</span>
                <PasswordInput name="input_password_repeat" bind:value={passwordRepeat} required/>
            </div>
        </div>

        <div class="flex">
            <div class="flex flex-col justify-center">
                <Checkbox name="input_tos" required/>
            </div>
            <span>{m.modal_register_label_i_agree_to_tos()}</span>
        </div>

        {#if !isTrusted}
            <Altcha name="input_altcha" bind:value={altchaToken}/>
        {/if}

        <Button name="button_submit" type="submit" class="w-full cursor-pointer">{m.modal_register_button()}</Button>

        <div class="flex">
            <A href="/auth/login" class="text-blue-700 hover:underline dark:text-blue-500">
                {m.modal_register_label_already_have_a_account()}
            </A>
        </div>
    </form>
</Modal>