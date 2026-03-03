<script>
    import {A, Button, Checkbox, Heading, Hr, Input, Modal, Spinner} from 'flowbite-svelte';
    import PasswordInput from "$lib/components/PasswordInput.svelte";
    import Altcha from "$lib/components/Altcha.svelte"
    import {m} from '$lib/paraglide/messages.js';
    import {Auth} from "$lib/api";
    import {goto, invalidateAll} from '$app/navigation';

    let {
        isTrusted = false,
        open = $bindable(false)
    } = $props();

    let loading = $state(false);

    let username = $state("");
    let password = $state("");
    let rememberMe = $state(false);
    let altchaToken = $state("");

    let error = $state("");

    async function login(event) {
        event.preventDefault();
        error = "";

        const payload = {
            emailOrUsername: $state.snapshot(username),
            password: $state.snapshot(password),
            rememberMe: $state.snapshot(rememberMe),
            altchaToken: $state.snapshot(altchaToken)
        };

        loading = true;
        const response = await Auth.login(payload);
        loading = false;

        //TODO replace by local checks and interpretation as exceptions are given in english only and only for dev/api purposes.
        if (!response.ok) {
            const json = await response.json();
            error = json.message;
            return;
        }

        await invalidateAll()
        await goto("/user/dashboard")
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
            <div>{m.modal_login_label_username_or_email()}</div>
            <Input name="input_email_or_username" type="text" bind:value={username} required/>
        </div>

        <div>
            <div>{m.modal_login_label_password()}</div>
            <PasswordInput name="input_password" bind:value={password} required/>
        </div>

        <div class="flex">
            <div class="flex flex-col justify-center">
                <Checkbox name="input_remember_me" bind:checked={rememberMe}/>
            </div>
            <div>{m.modal_login_label_remember_me({days: 30})}</div>
        </div>

        {#if !isTrusted}
            <Altcha name="input_altcha" bind:value={altchaToken}/>
        {/if}

        {#if error?.trim()}
            <div class="text-red-400 text-justify">{error}</div>
        {/if}

        <Button name="button_submit" type="submit">
            {#if loading === true}
                <Spinner size="5"/>
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