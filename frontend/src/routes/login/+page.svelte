<script>
	import { m } from '$lib/paraglide/messages.js';

	let apiResponse = $state(null);

	async function login(event) {
		event.preventDefault();

		const username = document.getElementById('usernameOrEmail').value;
		const password = document.getElementById('password').value;
		const response = await fetch('/api/auth/login', {
			method: 'POST',
			body: JSON.stringify({ username: username, password: password }),
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
		<div class="flex flex-col">
			<label for="usernameOrEmail">{m.login__username_or_email_label()}</label>
			<input id="usernameOrEmail" type="text" value="Twitchi" required />
		</div>

		<div class="flex flex-col">
			<label for="password">{m.login__password_label()}</label>
			<input id="password" type="password" value="eosc2d" required />
		</div>

		<div class="border border-dashed">
			<h1 class="text-center m-8">I am not a Robot ✅</h1>
		</div>

		<input type="submit" value={m.login__button()} />
	</form>

	<div class="flex gap-5 justify-center">
		<a class="text-center" href="/">{m.login__forgot_password()}</a>
		<a class="text-center" href="/register">{m.login__no_account()}</a>
	</div>
</div>

{#if apiResponse}
	<p class="whitespace-pre">{JSON.stringify(apiResponse, null, 2)}</p>
{/if}
