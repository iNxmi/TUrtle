<script>
    import {
        A,
        Button,
        ButtonGroup,
        Checkbox,
        Heading,
        Hr,
        Input,
        Modal,
        ProgressStepper,
        Spinner
    } from 'flowbite-svelte';
    import {BadgeCheckOutline, EnvelopeOutline, UserCircleOutline} from 'flowbite-svelte-icons';
    import PasswordInput from '$lib/components/PasswordInput.svelte';
    import {m} from '$lib/paraglide/messages.js';
    import {Auth} from '$lib/api';
    import Altcha from '$lib/components/Altcha.svelte';
    import LinkNavigation from '$lib/components/LinkNavigation.svelte';

    let {
        isTrusted = false,
        onLoginRedirect,
        open = $bindable(false),
        initialStep = 1,
        initialSession,
        initialUser,
    } = $props();

    let step = $state(initialStep);
    let session = $state(initialSession);
    let user = $state(initialUser);

    let username = $state('');
    let firstName = $state('');
    let lastName = $state('');
    let email = $state('');
    let password = $state('');
    let passwordRepeat = $state('');
    let altchaToken = $state('');

    let code = $state("");

    let loading = $state(false);
    let error = $state("");

    async function onRegister(event) {
        event.preventDefault();
        error = "";

        const payload = {
            username: username,
            firstName: firstName,
            lastName: lastName,
            email: email,
            password: password,
            altchaToken: altchaToken
        };

        loading = true;
        const response = await Auth.register(payload);
        loading = false;

        const json = await response.json();
        if (!response.ok) {
            error = json.message;
            return;
        }

        session = json.session;
        startCooldown();

        user = json.user

        step = 2;
    }

    async function onSubmitVerification(event) {
        event.preventDefault();
        error = "";

        loading = true;
        const response = await Auth.submitAccountVerification(session, code)
        loading = false;

        const json = await response.json();
        if (!response.ok) {
            error = json.message;
            return;
        }

        user = json

        step = 3
    }

    async function onResendVerification(event) {
        event.preventDefault();
        error = "";

        const response = await Auth.resendAccountVerification(session)
        if (!response.ok) {
            const json = await response.json()
            error = json.message;
            return;
        }

        startCooldown();
    }

    let resendTimer = null;
    let cooldown = $state(0);

    function startCooldown() {
        cooldown = 300;

        if (resendTimer)
            clearInterval(resendTimer);

        resendTimer = setInterval(() => {
            cooldown--;

            if (cooldown <= 0) {
                clearInterval(resendTimer);
                resendTimer = null;
            }
        }, 1000);
    }

    function formatCooldown(seconds) {
        const minutes = Math.floor(seconds / 60);
        const secs = seconds % 60;

        return `${minutes.toString().padStart(2, "0")}:${secs.toString().padStart(2, "0")}`;
    }

    const steps = [{
        id: 1,
        icon: UserCircleOutline
    }, {
        id: 2,
        icon: EnvelopeOutline
    }, {
        id: 3,
        icon: BadgeCheckOutline
    }];
</script>

{#snippet registration()}
    <form class="flex flex-col gap-5" onsubmit={onRegister}>
        <div>
            <div>{m.modal_register_label_username()}</div>
            <Input name="input_username" bind:value={username} type="text" required/>
        </div>

        <div class="flex gap-5">
            <div class="flex-1">
                <div>{m.modal_register_label_first_name()}</div>
                <Input name="input_first_name" bind:value={firstName} type="text" required/>
            </div>
            <div class="flex-1">
                <div>{m.modal_register_label_last_name()}</div>
                <Input name="input_last_name" bind:value={lastName} type="text" required/>
            </div>
        </div>

        <div>
            <div>{m.modal_register_label_email()}</div>
            <Input name="input_email" bind:value={email} type="email" required/>
        </div>

        <div class="flex gap-5">
            <div class="flex-1">
                <div>{m.modal_register_label_password()}</div>
                <PasswordInput name="input_password" bind:value={password} required/>
            </div>
            <div class="flex-1">
                <div>{m.modal_register_label_password_repeat()}</div>
                <PasswordInput name="input_password_repeat" bind:value={passwordRepeat} required/>
            </div>
        </div>

        <div class="flex">
            <div class="flex flex-col justify-center">
                <Checkbox name="input_tos" required/>
            </div>
            <div>{m.modal_register_label_i_agree_to()}
                <LinkNavigation id="tos" href="/tos">{m.modal_register_label_tos()}</LinkNavigation>
            </div>
        </div>

        {#if !isTrusted}
            <Altcha name="input_altcha" bind:value={altchaToken}/>
        {/if}

        {#if error?.trim()}
            <div class="text-red-400 text-justify">{error}</div>
        {/if}

        <Button type="submit" name="button_submit" class="w-full cursor-pointer">
            {#if loading === true}
                <Spinner size="5"/>
            {:else}
                {m.modal_register_button_register()}
            {/if}
        </Button>

        <div class="flex">
            <A onclick={() => onLoginRedirect?.()} class="text-blue-700 hover:underline dark:text-blue-500">
                {m.modal_register_label_already_have_a_account()}
            </A>
        </div>
    </form>
{/snippet}

{#snippet verification()}
    <form class="flex flex-col gap-5" onsubmit={onSubmitVerification}>
        <div class="text-center">
            <div>{m.modal_register_label_verification()}</div>
            <div class="font-bold">{user.email}</div>
        </div>

        {#if error?.trim()}
            <div class="text-red-400 text-justify">{error}</div>
        {/if}

        <ButtonGroup>
            <Input placeholder={m.modal_register_placeholder_verification()} bind:value={code} required
                   disabled={loading}/>
            <Button type="button" onclick={onSubmitVerification}>
                {#if loading === true}
                    <Spinner size="5"/>
                {:else}
                    {m.modal_register_button_verify()}
                {/if}
            </Button>
            <Button type="submit" onclick={onResendVerification} disabled={cooldown > 0}>
                {#if cooldown > 0}
                    {formatCooldown(cooldown)}
                {:else}
                    {m.modal_register_button_resend()}
                {/if}
            </Button>
        </ButtonGroup>
    </form>
{/snippet}

{#snippet conclusion()}
    <div class="flex flex-col gap-5">
        {#if user.status === "ACTIVE"}
            <div class="text-center">
                {m.modal_register_label_approved()}
            </div>
            <Button color="green" onclick={() => onLoginRedirect?.()}>{m.modal_register_button_login()}</Button>
        {:else if user.status === "PENDING_APPROVAL"}
            <div class="text-center">
                {m.modal_register_label_pending_approval()}
            </div>
            <Button color="yellow" onclick={() => open = false}>{m.modal_register_button_ok()}</Button>
        {/if}
    </div>
{/snippet}

<Modal bodyClass="flex flex-col gap-5" form bind:open outsideclose={false}>
    <Heading tag="h3" class="text-center m-0 p-0">
        {m.modal_register_title()}
    </Heading>

    <Hr class="m-0 p-0"/>

    <ProgressStepper class="p-0 m-0" bind:current={step} steps={steps} clickable={false}/>

    <Hr class="m-0 p-0"/>

    {#if step === 1}
        {@render registration()}
    {:else if step === 2}
        {@render verification()}
    {:else if step === 3}
        {@render conclusion()}
    {/if}
</Modal>
