<script>
    import {A, Button, ButtonGroup, Checkbox, Heading, Input, InputAddon, Label, Modal} from 'flowbite-svelte';
    import {EyeOutline, EyeSlashOutline} from 'flowbite-svelte-icons';
    import {m} from '$lib/paraglide/messages.js';
    import request from '$lib/api/api.js';
    import {invalidateAll} from '$app/navigation';
    import {authPath} from '$lib/backend.js'

    let username = $state("");
    let password = $state("");
    let rememberMe = $state(false);
    let altchaToken = $state("");

    let showPassword = $state(false);

    let {
        open = $bindable(false)
    } = $props();

    async function login(event) {
        event.preventDefault();

        const payload = {
            emailOrUsername: $state.snapshot(username),
            password: $state.snapshot(password),
            rememberMe: $state.snapshot(rememberMe),
            altchaToken: $state.snapshot(altchaToken)
        };

        const response = await request(authPath + '/login', {
            method: 'POST',
            body: JSON.stringify(payload),
            headers: {'Content-Type': 'application/json'}
        });

        if (!response.ok)
            return;

        await invalidateAll()
        open = false
    }
</script>

<Modal form bind:open={open} size="sm" class="shadow-md/30">
    <form class="flex flex-col gap-5" onsubmit={login}>
        <Heading tag="h3" class="text-center">
            {m.modal_login_title()}
        </Heading>

        <Label>
            <span>{m.modal_login_label_username_or_email()}</span>
            <Input bind:value={username} type="text" required/>
        </Label>

        <Label>
            <div class="flex justify-between">
                <span>{m.modal_login_label_password()}</span>
            </div>

            <ButtonGroup class="w-full">
                <Input bind:value={password} type={showPassword ? "text" : "password"} required/>
                <InputAddon>
                    <button type="button" onclick={(e) => {
                            e.preventDefault();
                            showPassword = !showPassword;
                        }}>
                        {#if showPassword}
                            <EyeOutline class="h-6 w-6"/>
                        {:else}
                            <EyeSlashOutline class="h-6 w-6"/>
                        {/if}
                    </button>
                </InputAddon>
            </ButtonGroup>
        </Label>

        <Checkbox bind:checked={rememberMe}>{m.modal_login_label_remember_me({days: 30})}</Checkbox>

        <!--                TODO reimplement trusted check, get trusted value from layout-->
        <!--                {#if !data.trusted}-->
        <!--                            <Altcha bind:value={altchaToken}/>-->
        <!--{/if}-->

        <Button class="cursor-pointer" type="submit">{m.modal_login_button()}</Button>

        <div class="flex gap-20 justify-between">
            <A href="/auth/register" class="text-sm text-blue-700 hover:underline dark:text-blue-500">
                {m.modal_login_label_no_account()}
            </A>
            <A href="/frontend/static" class="text-sm text-blue-700 hover:underline dark:text-blue-500">
                {m.modal_login_label_forgot_password()}
            </A>
        </div>
    </form>
</Modal>