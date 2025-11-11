<script>
    import {Button, Checkbox, Label, Input, Heading, A, P} from "flowbite-svelte";
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

<form class="flex flex-col gap-5" onsubmit={login}>
    <Heading tag="h3" class="text-center">{m.login__title()}</Heading>

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
        <P class="text-center m-8">I am not a Robot ✅</P>
    </div>

    <Button type="submit">{m.login__button()}</Button>

    <div class="flex gap-5 justify-between">
        <A href="/register" class="text-sm text-blue-700 hover:underline dark:text-blue-500">{m.login__no_account()}</A>
        <A href="/" class="text-sm text-blue-700 hover:underline dark:text-blue-500">{m.login__forgot_password()}</A>
    </div>
</form>

{#if apiResponse}
    <p class="whitespace-pre">{JSON.stringify(apiResponse, null, 2)}</p>
{/if}
