<script>
    import {Button, Checkbox, Label, Input} from "flowbite-svelte";
    import {m} from '$lib/paraglide/messages.js';

    let apiResponse = $state(null);

    async function login(event) {
        event.preventDefault();

        const username = document.getElementById('usernameOrEmail').value;
        const password = document.getElementById('password').value;
        const response = await fetch('/api/auth/login', {
            method: 'POST',
            body: JSON.stringify({username: username, password: password}),
            headers: {
                'Content-Type': 'application/json'
            }
        });

        apiResponse = await response.json();
    }
</script>

<div>
    <h1 class="text-center">{m.login__title()}</h1>

    <form class="flex flex-col gap-5" onsubmit={login}>
        <Label>
            <span>{m.login__username_or_email_label()}</span>
            <Input name="username_or_email" type="text" required/>
        </Label>

        <Label>
            <span>{m.login__password_label()}</span>
            <Input name="password" type="password" required/>
        </Label>

        <Checkbox>_remember_me_text</Checkbox>

        <div class="border border-dashed">
            <h1 class="text-center m-8">I am not a Robot ✅</h1>
        </div>

        <Button type="submit">{m.login__button()}</Button>

        <div class="flex gap-5 justify-between">
            <a href="/register" class="text-sm text-blue-700 hover:underline dark:text-blue-500">{m.login__no_account()}</a>
            <a href="/" class="text-sm text-blue-700 hover:underline dark:text-blue-500">{m.login__forgot_password()}</a>
        </div>
    </form>
</div>

{#if apiResponse}
    <p class="whitespace-pre">{JSON.stringify(apiResponse, null, 2)}</p>
{/if}
