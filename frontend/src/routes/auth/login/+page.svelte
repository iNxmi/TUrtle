<script>
	import { A, Button, Checkbox, Heading, Input, Label, Modal, P } from 'flowbite-svelte';
	import { m } from '$lib/paraglide/messages.js';
	import request from '$lib/api/api.js';
	import { goto } from '$app/navigation';
	import { page } from '$app/state';

	let apiResponse = $state(null);
	let modal = $state(false);
	let username = $state('');
	let password = $state('');
	let rememberMe = $state(false);

	async function login(event) {
		event.preventDefault();

		const payload = {
			username: $state.snapshot(username),
			password: $state.snapshot(password),
			rememberMe: $state.snapshot(rememberMe)
		};

		const response = await request('/auth/login', {
			method: 'POST',
			body: JSON.stringify(payload),
			headers: { 'Content-Type': 'application/json' }
		});

		apiResponse = await response.json();

		goto(page.url.searchParams.get('redirectTo') || '/user/dashboard',{invalidateAll: true,});
		// window.location.reload()
	}
</script>

<div class="flex justify-center w-[30rem] h-[40rem] bg-neutral-100 dark:bg-[#1E2939] rounded-lg p-0 pt-20 mx-auto max-w-full shadow-xl border-1 border-neutral-300 dark:border-gray-700">
	<form class="flex flex-col gap-5" onsubmit={login}>
		<Heading tag="h3" class="text-center">
			{m.login__title()}
		</Heading>

		<Label>
			<span>{m.login__username_or_email_label()}</span>
			<Input bind:value={username} type="text" required />
		</Label>

		<Label>
			<span>{m.login__password_label()}</span>
			<Input bind:value={password} type="password" required />
		</Label>

		<Checkbox bind:checked={rememberMe}>{m.login__remember_me({ days: 30 })}</Checkbox>

		<div class="border border-dashed">
			<P class="text-center m-8">I am not a Robot ✅</P>
		</div>

		<Button type="submit">{m.login__button()}</Button>

		<div class="flex gap-5 justify-between">
			<A href="/register" class="text-sm text-blue-700 hover:underline dark:text-blue-500"
				>{m.login__no_account()}</A
			>
			<A href="/frontend/static" class="text-sm text-blue-700 hover:underline dark:text-blue-500"
				>{m.login__forgot_password()}</A
			>
		</div>
	</form>
</div>
