<script>
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
        <div class="flex flex-col">
            <label for="username">{m.register__username_label()}</label>
            <input id="username" type="text" required/>
        </div>

        <div class="flex gap-5">
            <div class="flex flex-1 flex-col">
                <label for="first_name">{m.register__first_name_label()}</label>
                <input id="first_name" type="text" required/>
            </div>
            <div class="flex flex-1 flex-col">
                <label for="last_name">{m.register__last_name_label()}</label>
                <input id="last_name" type="text" required/>
            </div>
        </div>

        <div class="flex flex-col">
            <label for="password">{m.register__password_label()}</label>
            <input id="password" type="password" required/>
        </div>

        <div class="flex gap-5">
            <input type="checkbox" required>
            <p>{m.register__i_agree_to_tos()}</p>
        </div>

        <div class="border border-dashed">
            <h1 class="text-center m-8">I am not a Robot ✅</h1>
        </div>

        <input type="submit" value={m.register__button()}/>
    </form>

    <a class="text-center" href="/login">{m.register__already_have_a_account()}</a>
</div>

{#if apiResponse}
    <p class="whitespace-pre">{JSON.stringify(apiResponse, null, 2)}</p>
{/if}
