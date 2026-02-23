<script>
    import {A, Button, Checkbox, Heading, Hr, Input, Modal, Spinner} from 'flowbite-svelte';
    import PasswordInput from "$lib/components/PasswordInput.svelte";
    import Altcha from "$lib/components/Altcha.svelte"
    import {m} from '$lib/paraglide/messages.js';
    import request from '$lib/api/api.js';
    import {invalidateAll} from '$app/navigation';
    import {authPath} from '$lib/backend.js'

    let {
        isTrusted = false,
        open = $bindable(false)
    } = $props();

    let loading = $state(false);

    let username = $state("");
    let password = $state("");
    let rememberMe = $state(false);
    let altchaToken = $state("");

    async function login(event) {
        event.preventDefault();

        loading = true;

        const payload = {
            emailOrUsername: $state.snapshot(username),
            password: $state.snapshot(password),
            rememberMe: $state.snapshot(rememberMe),
            altchaToken: $state.snapshot(altchaToken)
        };

        const response = await request("/api/auth/login", {
            method: "POST",
            body: JSON.stringify(payload),
            headers: {'Content-Type': 'application/json'}
        });

        loading = false;

        if (!response.ok)
            return;

        await invalidateAll()
        open = false
    }
</script>

<Modal form bind:open={open} size="sm" outsideclose={false}>
    <form class="flex flex-col gap-5" onsubmit={login}>
        <Heading tag="h3" class="text-center">
            {m.modal_login_title()}
        </Heading>

        <Hr class="m-0 p-0"/>

        <div>
            <span>{m.modal_login_label_username_or_email()}</span>
            <Input name="input_email_or_username" type="text" bind:value={username} required/>
        </div>

        <div>
            <span>{m.modal_login_label_password()}</span>
            <PasswordInput name="input_password" bind:value={password} required/>
        </div>

        <div class="flex">
            <div class="flex flex-col justify-center">
                <Checkbox name="input_remember_me" bind:checked={rememberMe}/>
            </div>
            <span>{m.modal_login_label_remember_me({days: 30})}</span>
        </div>

        {#if !isTrusted}
            <Altcha name="input_altcha" bind:value={altchaToken}/>
        {/if}

        <Button name="button_submit" type="submit">
            {#if loading === true}
                <Spinner type="dots"/>
            {:else}
                {m.modal_login_button()}
            {/if}
        </Button>

        <div class="flex justify-between">
            <A href="/auth/register" class="text-blue-700 hover:underline dark:text-blue-500">
                {m.modal_login_label_no_account()}
            </A>
            <A href="/frontend/static" class="text-blue-700 hover:underline dark:text-blue-500">
                {m.modal_login_label_forgot_password()}
            </A>
        </div>
    </form>
</Modal>