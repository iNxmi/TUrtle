<script>
    import {Button, Checkbox, Label, Input, Heading, A, P, Modal} from "flowbite-svelte";
    import {m} from '$lib/paraglide/messages.js';

    let apiResponse = $state(null);
    let modal = $state(false);

    async function login(event) {
        event.preventDefault();

        const username = document.getElementById('username_or_email').value;
        const password = document.getElementById('password').value;

        const payload = {
            username: username,
            password: password
        }

        const response = await fetch("/api/auth/login", {
            method: "POST",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });

        apiResponse = await response.json();
        modal = true;

        window.location.reload()
    }
</script>

<div>
    <form class="flex flex-col gap-5" onsubmit={login}>
        <Heading tag="h3" class="text-center">{m.login__title()}</Heading>

        <Label>
            <span>{m.login__username_or_email_label()}</span>
            <Input id="username_or_email" type="text" required/>
        </Label>

        <Label>
            <span>{m.login__password_label()}</span>
            <Input id="password" type="password" required/>
        </Label>

        <Checkbox>{m.login__remember_me({days: 30})}</Checkbox>

        <div class="border border-dashed">
            <P class="text-center m-8">I am not a Robot ✅</P>
        </div>

        <Button type="submit">{m.login__button()}</Button>

        <div class="flex gap-5 justify-between">
            <A href="/register"
               class="text-sm text-blue-700 hover:underline dark:text-blue-500">{m.login__no_account()}</A>
            <A href="/"
               class="text-sm text-blue-700 hover:underline dark:text-blue-500">{m.login__forgot_password()}</A>
        </div>
    </form>

    <Modal title="API Response" bind:open={modal}>
        {#if apiResponse}
            <P class="whitespace-pre">{JSON.stringify(apiResponse, null, 2)}</P>
        {/if}
    </Modal>
</div>
