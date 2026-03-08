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
    import {ArrowRightAltOutline, AwardOutline, BadgeCheckOutline, UserCircleOutline} from 'flowbite-svelte-icons';
    import PasswordInput from '$lib/components/PasswordInput.svelte';
    import {m} from '$lib/paraglide/messages.js';
    import {Auth} from '$lib/api';
    import Altcha from '$lib/components/Altcha.svelte';
    import LinkNavigation from '$lib/components/LinkNavigation.svelte';

    let loading = $state(false);

    let username = $state('');
    let firstName = $state('');
    let lastName = $state('');
    let email = $state('');
    let password = $state('');
    let passwordRepeat = $state('');
    let altchaToken = $state('');
    let verificationCode = $state("");

    let error = $state("");

    let {
        isTrusted = false,
        open = $bindable(false),
        initialStep = 1
    } = $props();

    let step = $state(initialStep);

    async function register(event) {
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

        if (!response.ok) {
            const json = await response.json();
            error = json.message;
            return;
        }

        step = 2;
    }

    async function verify(event) {
        event.preventDefault();
        error = "";

        const response = await Auth.verify(verificationCode)
        const json = await response.json()
        if(!response.ok) {
            error = json.message;
            return;
        }

        if(json.status === "PENDING_APPROVAL")
            step = 3

        if(json.status === "ACTIVE")
            step = 4;
    }

    const steps = [{
        id: 1,
        icon: UserCircleOutline
    }, {
        id: 2,
        icon: BadgeCheckOutline
    }, {
        id: 3,
        icon: ArrowRightAltOutline
    }, {
        id: 4,
        icon: AwardOutline
    }];
</script>

<Modal bodyClass="flex flex-col gap-5" form bind:open outsideclose={false}>
    <Heading tag="h3" class="text-center m-0 p-0">
        {m.modal_register_title()}
    </Heading>

    <Hr class="m-0 p-0"/>

    <ProgressStepper class="p-0 m-0" bind:current={step} steps={steps} clickable={false}/>

    <Hr class="m-0 p-0"/>

    {#if step === 1}
        <div class="flex flex-col gap-3">
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

            <Button id="register" name="button_submit" onclick={register} class="w-full cursor-pointer">
                {#if loading === true}
                    <Spinner size="5"/>
                {:else}
                    {m.modal_register_button()}
                {/if}
            </Button>

            <div class="flex">
                <A href="/auth/login" class="text-blue-700 hover:underline dark:text-blue-500">
                    {m.modal_register_label_already_have_a_account()}
                </A>
            </div>
        </div>
    {:else if step === 2}
        <div class="flex flex-col gap-3">
            <div>_Please Verify your email address._ <span class="font-bold">{email}</span></div>

            {#if error?.trim()}
                <div class="text-red-400 text-justify">{error}</div>
            {/if}

            <ButtonGroup>
                <Input placeholder="550e8400-e29b-41d4-a716-446655440000" bind:value={verificationCode}/>
                <Button onclick={verify}>_Send_</Button>
                <Button onclick={async () => await Auth.requestVerification()}>_Request_</Button>
            </ButtonGroup>
        </div>
    {:else if step === 3}
        your registration was successful, an admin now has to accept your account
    {:else if step === 4}
        success, now login
    {/if}
</Modal>
