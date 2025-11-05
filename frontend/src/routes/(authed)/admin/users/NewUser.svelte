<script>
	import { m } from '$lib/paraglide/messages';
	import { Button, Modal, Input, Label } from 'flowbite-svelte';

	let { open = $bindable() } = $props();
	let elementRef = $state() 
</script>

<Modal bind:open class="bg-background-50">
	{#snippet header()}
	{m.new_user()}
	{/snippet}
	<form method="POST">
		<p class="text-base leading-relaxed text-gray-500 dark:text-gray-400"></p>
		<div class="mb-6 grid gap-6 md:grid-cols-2">
			<div>
				<Label for="first_name" class="mb-2">{m.first_name()}</Label>
				<Input bind:elementRef name="first_name" type="text" id="first_name" placeholder="John" required />
			</div>
			<div>
				<Label for="last_name" class="mb-2">{m.last_name()}</Label>
				<Input type="text" name="last_name" id="last_name" placeholder="Doe" required />
			</div>
			<div>
				<Label for="email" class="mb-2">E-Mail</Label>
				<Input type="email" name="email" id="email" placeholder="example@gmail.com" required />
			</div>
			<div>
				<Label for="phone" name="phone" class="mb-2">{m.phone_number()}</Label>
				<Input
					type="tel"
					id="phone"
					placeholder="123-45-678"
					pattern={'[0-9]{3}-[0-9]{2}-[0-9]{3}'}
					required
				/>
			</div>
		</div>
		<div class="flex items-center pt-3 space-x-3 rtl:space-x-reverse rounded-b-lg shrink-0 ">
			<Button class="bg-accent" type="submit">Log in</Button>
		</div>
	</form>
	<!-- {#snippet footer()}
		<Button type="submit" class="bg-accent">Submit</Button>
	
		<Button onclick={() => (open = false)} class="bg-primary">Cancel</Button>
	{/snippet} -->

</Modal>
