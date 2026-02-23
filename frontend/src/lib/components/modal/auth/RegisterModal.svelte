<script>
    import {A, Button, Checkbox, Heading, Input, Modal} from "flowbite-svelte";
    import PasswordInput from "$lib/components/PasswordInput.svelte";
    import {m} from '$lib/paraglide/messages.js';
    import request from "$lib/api/api.js";
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

        const response = await request("/users", {
            method: 'POST',
            body: JSON.stringify(payload),
            headers: {'Content-Type': 'application/json'}
        });

        if (response.status !== 204)
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

        <div>
            <span>{m.modal_register_label_username()}</span>
            <Input bind:value={username} type="text" required/>
        </div>

        <div class="flex gap-5">
            <div class="flex-1">
                <span>{m.modal_register_label_first_name()}</span>
                <Input bind:value={firstName} type="text" required/>
            </div>
            <div class="flex-1">
                <span>{m.modal_register_label_last_name()}</span>
                <Input bind:value={lastName} type="text" required/>
            </div>
        </div>

        <div>
            <span>{m.modal_register_label_email()}</span>
            <Input bind:value={email} type="email" required/>
        </div>

        <div class="flex gap-5">
            <div class="flex-1">
                <span>{m.modal_register_label_password()}</span>
                <PasswordInput bind:value={password} required/>
            </div>
            <div class="flex-1">
                <span>{m.modal_register_label_password_repeat()}</span>
                <PasswordInput bind:value={passwordRepeat} required/>
            </div>
        </div>

        <Checkbox id="agree_tos" required>{m.modal_register_label_i_agree_to_tos()}</Checkbox>

        {#if !isTrusted}
            <Altcha bind:value={altchaToken}/>
        {/if}

        <Button type="submit" class="w-full cursor-pointer">{m.modal_register_button()}</Button>

        <A href="/auth/login"
           class="text-sm text-blue-700 hover:underline dark:text-blue-500">{m.modal_register_label_already_have_a_account()}
        </A>
    </form>
</Modal>