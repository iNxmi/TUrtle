<script>
	import UserTable from './UserTable.svelte';

	let { data } = $props();

	const users = data.users;
</script>

<div class="space-y-0.5 mb-8 md:mb-8">
	<h2 class="text-2xl font-bold text-text ml-2">Users</h2>
</div>
<div class="rounded-lg border border-table-dark">
	<UserTable data={users}></UserTable>
</div>
