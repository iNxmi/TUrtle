<script>
	import {
		TableBody,
		TableBodyCell,
		TableBodyRow,
		TableHead,
		TableHeadCell,
		Table,
		Input,
		Button,
		Pagination,
		PaginationNav,
		PaginationItem,
		Select,
		Label,
		Card,
		ButtonGroup
	} from 'flowbite-svelte';
	import {
		AngleLeftOutline,
		AngleRightOutline,
		ArrowLeftOutline,
		ArrowRightOutline,
		ChevronDoubleLeftOutline,
		ChevronDoubleRightOutline,
		PlusOutline,
		SearchOutline
	} from 'flowbite-svelte-icons';
	import request from '$lib/api/api';
	/* Props:
        tableData: Initial data : Array[Object]
        columnNames: Column Names for the table on chronological order : Array[String] 
        itemType: Localized category name of the data for displaying purposes: String
        apiResource: Name of the data type in backend api for fetching purposes: String
        newItemModal: bindable boolean to show the Model for creating a new Item : boolean
        */

	let {
		tableData,
		columnNames,
		itemType = 'Item',
		apiResource,
		newItemModal = $bindable(false)
	} = $props();

	let currentPage = $state(1);
	let pageSize = $state(10);
	let searchTerm = $state('');
	let itemKeys = $state(Object.keys(tableData.content[0]));
	async function fetchData(search) {
		/* ToDo: Search */
		const fetchPage = search ? 1 : $state.snapshot(currentPage);
		const response = await request(`/${apiResource}/page?page=${fetchPage - 1}&size=${$state.snapshot(pageSize)}`, {
			method: 'GET',
			headers: { 'Content-Type': 'application/json' }
		});
		tableData = await response.json();

		itemKeys = Object.keys(tableData.content[0]);
	}

	let totalPages = $derived(Math.ceil(100 / pageSize));
	let helper = $derived({
		start: (currentPage - 1) * pageSize + 1,
		end: currentPage * pageSize,
		total: 100
	});

	function handleSearch() {
		fetchData(true);
	}

	function newItem() {
		newItemModal = true;
	}
	const firstPage = () => {
		currentPage = 1;

		fetchData();
	};
	const lastPage = () => {
		currentPage = totalPages;

		fetchData();
	};

	const previousPage = () => {
		currentPage = currentPage > 1 ? currentPage - 1 : currentPage;

		fetchData();
	};

	const nextPage = () => {
		currentPage = currentPage < totalPages ? currentPage + 1 : currentPage;

		fetchData();
	};
</script>

<Card shadow={false} class="min-w-full max-h-full p-2">
	<div class="flex flex-row justify-between">
		<ButtonGroup class="w-full max-w-1/6 m-3">
			<Input
				class="dark:bg-background-50"
				placeholder={`_Search_ ${itemType}`}
				bind:value={searchTerm}
			/>
			<Button class="bg-csw" onclick={handleSearch}>
				<SearchOutline class="size-5 text-white" />
			</Button>
		</ButtonGroup>
		<Button
			class="m-3 text-white hover:cursor-pointer"
			onclick={newItem}
		>
			<PlusOutline class="h-5 w-5" />
			_Create_ {itemType}
		</Button>
	</div>
	<Table border={false} hoverable striped bind:inputValue={searchTerm}>
		<TableHead>
			{#each columnNames as cell}
				<TableHeadCell>{cell}</TableHeadCell>
			{/each}
		</TableHead>
		<TableBody>
			{#each tableData.content as item, i}
				<TableBodyRow>
					{#each itemKeys as key}
						<TableBodyCell class="hover:text-csw">{item[key]}</TableBodyCell>
					{/each}
				</TableBodyRow>
			{/each}
		</TableBody>
	</Table>
	<div class="flex justify-between">
		<Label class="w-16">
			<Select
			class="mt-5"
				items={[
					{
						value: 10,
						name: '10'
					},
					{
						value: 25,
						name: '25'
					},
					{
						value: 50,
						name: '50'
					},
					{
						value: 100,
						name: '100'
					}
				]}
				bind:value={pageSize}
				onchange={fetchData}
			/>
		</Label>

		<div class="flex flex-col items-center justify-center gap-3">
			<div class="flex flex-col items-center justify-center gap-2">
				<div class="text-sm text-gray-700 dark:text-gray-400">
					_Showing_ <span class="font-semibold text-gray-900 dark:text-white">{helper.start}</span>
					_to_
					<span class="font-semibold text-gray-900 dark:text-white">{helper.end}</span>
					_of_
					<span class="font-semibold text-gray-900 dark:text-white">{helper.total}</span>
					_Entries_
				</div>
				<div class="flex items-center space-x-2">
					<Button
						class="size-6 hover:cursor-pointer disabled:cursor-not-allowed"
						onclick={firstPage}
						disabled={currentPage == 1}
					>
						<ChevronDoubleLeftOutline class="size-4" />
					</Button>
					<Button
						class="size-6 hover:cursor-pointer disabled:cursor-not-allowed"
						onclick={previousPage}
						disabled={currentPage == 1}
					>
						<AngleLeftOutline class="size-4" />
					</Button>
					<Button
						class="size-6 hover:cursor-pointer disabled:cursor-not-allowed"
						onclick={nextPage}
						disabled={currentPage == totalPages}
					>
						<AngleRightOutline class="size-4" />
					</Button>
					<Button
						class="size-6 hover:cursor-pointer disabled:cursor-not-allowed"
						onclick={lastPage}
						disabled={currentPage == totalPages}
					>
						<ChevronDoubleRightOutline class="size-4" />
					</Button>
				</div>
			</div>
		</div>
	</div>
</Card>
