<script>
    import {Button, Checkbox, Label, Input} from "flowbite-svelte";
    import {m} from '$lib/paraglide/messages.js';

    let apiResponse = $state(null);

    async function login(event) {
        event.preventDefault();

        const username = document.getElementById("username").value;
        const password = document.getElementById("password").value;
        const response = await fetch('/api/auth/register', {
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
    <h1 class="text-center">{m.register__title()}</h1>

    <form class="flex flex-col gap-5" onsubmit={login}>
        <Label>
            <span>{m.register__username_label()}</span>
            <Input name="username" type="text" required/>
        </Label>

        <div class="flex gap-5">
            <Label class="flex-1">
                <span>{m.register__first_name_label()}</span>
                <Input name="first_name" type="text" required/>
            </Label>
            <Label class="flex-1">
                <span>{m.register__last_name_label()}</span>
                <Input name="last_name" type="text" required/>
            </Label>
        </div>

        <Label>
            <span>{m.register__email_label()}</span>
            <Input name="email" type="email" required/>
        </Label>

        <Label>
            <span>{m.register__student_id_label()}</span>
            <Input name="student_id" type="text" required/>
        </Label>

        <div class="flex gap-5">
            <Label class="flex-1">
                <span>{m.register__password_label()}</span>
                <Input name="password" type="password" required/>
            </Label>
            <Label class="flex-1">
                <span>{m.register__password_repeat_label()}</span>
                <Input name="password_repeat" type="password" required/>
            </Label>
        </div>

        <Checkbox>{m.register__i_agree_to_tos()}</Checkbox>

        <div class="border border-dashed">
            <h1 class="text-center m-8">I am not a Robot ✅</h1>
        </div>

        <Button type="submit" class="w-full1">{m.register__button()}</Button>

        <a href="/login" class="text-sm text-blue-700 hover:underline dark:text-blue-500">{m.register__already_have_a_account()}</a>
    </form>
</div>

{#if apiResponse}
    <p class="whitespace-pre">{JSON.stringify(apiResponse, null, 2)}</p>
{/if}
