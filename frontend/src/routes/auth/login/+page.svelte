<script>
    import {A, Button, Checkbox, Heading, Input, Label} from 'flowbite-svelte';
    import {m} from '$lib/paraglide/messages.js';
    import request from '$lib/api/api.js';
    import {goto} from '$app/navigation';
    import {page} from '$app/state';
    import {authPath} from '$lib/backend'
    import Altcha from "$lib/components/Altcha.svelte";
    import { dev } from '$app/environment';

    let apiResponse = $state(null);
    let modal = $state(false);
    let username = $state('');
    let password = $state('');
    let rememberMe = $state(false);
    let altchaToken = $state("");
    const {data} = $props();

    async function login(event) {
        event.preventDefault();

        const payload = {
            emailOrUsername: $state.snapshot(username),
            password: $state.snapshot(password),
            rememberMe: $state.snapshot(rememberMe),
            altchaToken: $state.snapshot(altchaToken),
        };

        const response = await request(authPath + '/login', {
            method: 'POST',
            body: JSON.stringify(payload),
            headers: {'Content-Type': 'application/json'}
        });

        /* apiResponse = await response.json(); */

        if (response.ok || dev) {
            goto(page.url.searchParams.get('redirectTo') || '/user/dashboard', {invalidateAll: true,});
        }

        // window.location.reload()
    }
</script>

<div class=" flex min-h-svh w-full bg-background justify-center items-center">
    <div class="flex justify-center w-120 bg-neutral-100 dark:bg-[#1E2939] h-120 rounded-lg p-20 mx-auto max-w-full shadow-xl border border-neutral-300 dark:border-gray-700">
        <form class="flex flex-col gap-5" onsubmit={login}>
            <Heading tag="h3" class="text-center">
                {m.login__title()}
            </Heading>

            <Label>
                <span>{m.login__username_or_email_label()}</span>
                <Input bind:value={username} type="text" required/>
            </Label>

            <Label>
                <span>{m.login__password_label()}</span>
                <Input bind:value={password} type="password" required/>
            </Label>

            <Checkbox bind:checked={rememberMe}>{m.login__remember_me({days: 30})}</Checkbox>

            {#if !data.trusted && !dev}
                <Altcha bind:value={altchaToken}/>
            {/if}

            <Button class="cursor-pointer" type="submit">{m.login__button()}</Button>

            <div class="flex gap-5 justify-between">
                <A href="/auth/register" class="text-sm text-blue-700 hover:underline dark:text-blue-500">
                    {m.login__no_account()}
                </A>
                <A href="/frontend/static" class="text-sm text-blue-700 hover:underline dark:text-blue-500">
                    {m.login__forgot_password()}
                </A>
            </div>
        </form>
    </div>
</div>