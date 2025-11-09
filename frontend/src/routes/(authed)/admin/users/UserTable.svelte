<script>
	import { m } from '$lib/paraglide/messages';
	import {
		Input,
		Button,
		Table,
		TableBody,
		TableBodyCell,
		TableBodyRow,
		TableHead,
		TableHeadCell,
		TableSearch
	} from 'flowbite-svelte';
	import { PlusOutline } from 'flowbite-svelte-icons';
	import NewUser from './NewUser.svelte';
	let { data } = $props();
	let searchTerm = $state('');
	let defaultModal = $state(false);

	let filteredUsers = $derived.by(() =>
		data.filter(
			(user) =>
				!searchTerm ||
				user.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
				user.email.toLowerCase().includes(searchTerm.toLowerCase()) ||
				user.id.includes(searchTerm.toLowerCase())
		)
	);
	const handleClick = () => {
		defaultModal = true;
	};
</script>

<div class="flex">
	<Input class="max-w-1/5 m-3" placeholder={m.search()} bind:value={searchTerm}></Input>
	<Button
		onclick={handleClick}
		class=" absolute right-20 m-3 bg-accent text-accent-dark hover:cursor-pointer"
	>
		<PlusOutline class="text-green-700 h-5 w-5 transition duration-75" />
		{m.new_user()}
	</Button>
</div>
<Table class="table-fixed" border={false} bind:inputValue={searchTerm}>
	<TableHead>
		<TableHeadCell class="bg-secondary">TU-ID</TableHeadCell>
		<TableHeadCell class="bg-secondary">Nachmame</TableHeadCell>
		<TableHeadCell class="bg-secondary">Vorname</TableHeadCell>
		<TableHeadCell class="bg-secondary">Nutzername</TableHeadCell>
		<TableHeadCell class="bg-secondary">E-Mail</TableHeadCell>
		<TableHeadCell class="bg-secondary">
			<span class="sr-only">Delete</span>
		</TableHeadCell>
	</TableHead>

	<TableBody>
		{#each filteredUsers as user, i (user.id)}
			<TableBodyRow
				class={i % 2 == 0
					? 'bg-table-dark hover:bg-table-dark'
					: 'bg-secondary hover:bg-table-dark'}
			>
			
				<TableBodyCell>{user.studentId}</TableBodyCell>
				<TableBodyCell>{user.lastName}</TableBodyCell>
				<TableBodyCell>{user.firstName}</TableBodyCell>
				<TableBodyCell>{user.username}</TableBodyCell>
				<TableBodyCell>{user.email}</TableBodyCell>
				<TableBodyCell
					><a
						href="users/{user.id}"
						class="text-orange-600 hover:underline hover:cursor-pointer p-2">{m.edit()}</a
					>
					<button
						class="text-red-600 font-medium hover:underline hover:cursor-pointer"
						onclick={async (e) => {
							await fetch(`/api/v1/users/${user.id}`, {
								method: 'DELETE'
							});

							data = data.filter((db_user) => db_user !== user);

							console.log(data);
						}}>{m.delete()}</button
					></TableBodyCell
				>
			</TableBodyRow>
		{/each}
	</TableBody>
</Table>
<NewUser bind:open={defaultModal}></NewUser>
