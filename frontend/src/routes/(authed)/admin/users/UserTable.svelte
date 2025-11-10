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
	<Input class="max-w-1/5 m-3" placeholder={m.admin_users__search_user()} bind:value={searchTerm}
	></Input>
	<Button
		onclick={handleClick}
		class=" absolute right-20 m-3 bg-accent text-accent-dark hover:cursor-pointer"
	>
		<PlusOutline class="text-green-700 h-5 w-5 transition duration-75" />
		{m.admin_users__create_user()}
	</Button>
</div>
<Table class="table-fixed" border={false} bind:inputValue={searchTerm}>
	<TableHead>
		<TableHeadCell class="bg-secondary">{m.admin_users__student_id()}</TableHeadCell>
		<TableHeadCell class="bg-secondary">{m.admin_users__last_name()}</TableHeadCell>
		<TableHeadCell class="bg-secondary">{m.admin_users__first_name()}</TableHeadCell>
		<TableHeadCell class="bg-secondary">{m.admin_users__username()}</TableHeadCell>
		<TableHeadCell class="bg-secondary">{m.admin_users__email()}</TableHeadCell>
		<TableHeadCell class="bg-secondary">
			<span class="sr-only">{m.admin_users__delete_user()}</span>
		</TableHeadCell>
	</TableHead>

	<TableBody>
		{#each filteredUsers as user, i (user.id)}
			<TableBodyRow
				class={i % 2 == 0
					? 'bg-table-dark dark:bg-table-dark hover:bg-table-dark'
					: 'bg-secondary dark:bg-secondary hover:bg-table-dark'}
			>
				<TableBodyCell>{user.studentId}</TableBodyCell>
				<TableBodyCell>{user.lastName}</TableBodyCell>
				<TableBodyCell>{user.firstName}</TableBodyCell>
				<TableBodyCell>{user.username}</TableBodyCell>
				<TableBodyCell>{user.email}</TableBodyCell>
				<TableBodyCell
					><a
						href="users/{user.id}"
						class="text-orange-600 hover:underline hover:cursor-pointer p-2"
						>{m.admin_users__edit_user()}</a
					>
					<button
						class="text-red-600 font-medium hover:underline hover:cursor-pointer"
						onclick={async (e) => {
							await fetch(`/api/users/${user.id}`, {
								method: 'DELETE'
							});

							data = data.filter((db_user) => db_user !== user);
						}}>{m.admin_users__delete_user()}</button
					>
				</TableBodyCell>
			</TableBodyRow>
		{/each}
	</TableBody>
</Table>
<NewUser bind:open={defaultModal}></NewUser>
