<script>
    import { TableBody, TableBodyCell, TableBodyRow, TableHead, TableHeadCell, Table, Input, Button, Pagination, PaginationNav, PaginationItem, Select, Label, Card } from "flowbite-svelte";
    import { AngleLeftOutline, AngleRightOutline, ArrowLeftOutline, ArrowRightOutline, ChevronDoubleLeftOutline, ChevronDoubleRightOutline, PlusOutline } from 'flowbite-svelte-icons';
   
    /* Props:
        tableDate: Array of data objects : Array[Object]
        columnNames: Column Names of the table on chronological order : Array[String] 
        itemType: Localized name of the data : String
        newItemModal: bindable boolean to show the Model for creating a new Item : boolean
        */
    let { tableData, columnNames, itemType = "Item", newItemModal = $bindable(false) } = $props();
    let searchTerm = $state("");
    let itemKeys = $derived(Object.keys(tableData[0]));
    let currentPage =$state(1);
    let pageSize = $state(10);
    let totalPages = $derived(Math.ceil(filteredItems.length / pageSize));
    let helper =  $derived({start: (currentPage - 1)* pageSize + 1, end: currentPage * pageSize, total: filteredItems.length});
    let filteredItems = $derived.by(() => 
    tableData.filter((item) => 
        !searchTerm || itemKeys.filter((key) =>
            item[key].toLowerCase().includes(searchTerm.toLowerCase())).length != 0));
    
    let paginatedItems = $derived(filteredItems.slice((currentPage - 1) * pageSize, currentPage * pageSize));

    function newItem(){ 
        newItemModal = true;
    }
    const firstPage = () => {
        currentPage = 1;
    }
    const lastPage = () => {
        currentPage = totalPages;
    }

    const previousPage = () => {   
        currentPage = currentPage > 1 ? currentPage -1 : currentPage;
    }
       
    const nextPage = () => {
        currentPage = currentPage < totalPages  ? currentPage + 1 : currentPage;
    }
    
</script>

<Card shadow={false} class="min-w-full max-h-full p-2">
    <div class="flex flex-row justify-between">
        <Input
            class="max-w-1/6 m-3 dark:bg-background-50"
            placeholder={`_Search_ ${itemType}`}
            bind:value={searchTerm}
        ></Input>
        <Button class="m-3 bg-csw text-white hover:bg-orange-500 hover:cursor-pointer" onclick={newItem}>
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
            {#each paginatedItems as item, i}
                <TableBodyRow>
                    {#each itemKeys as key}
                        <TableBodyCell class="hover:text-csw">{item[key]}</TableBodyCell>
                    {/each}
                </TableBodyRow>
            {/each}
        </TableBody>
    </Table>
    <div class="flex  justify-between">
        <Label class="w-16">
            <Select class="mt-5" items={[{
                value: 10,
                name: "10"
            },
            {
                value: 25,
                name: "25"
            },
            {
                value: 50,
                name: "50"
            },
            {
                value: 100,
                name: "100"
            },
            ]} bind:value={pageSize}/>
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
                    <Button class=" hover:cursor-pointer disabled:cursor-not-allowed" onclick={firstPage} disabled={currentPage == 1}>              
                        <ChevronDoubleLeftOutline class="size-4"/>           
                    </Button>
                    <Button class="hover:cursor-pointer disabled:cursor-not-allowed" onclick={previousPage} disabled={currentPage == 1}>
                        <AngleLeftOutline class="size-4"/>              
                    </Button>
                    <Button class="hover:cursor-pointer disabled:cursor-not-allowed" onclick={nextPage} disabled={currentPage == totalPages}>
                        <AngleRightOutline class="size-4"/>                 
                    </Button>
                    <Button class="hover:cursor-pointer disabled:cursor-not-allowed" onclick={lastPage} disabled={currentPage == totalPages}>                 
                        <ChevronDoubleRightOutline class="size-4"/>                
                    </Button>
                </div>
            </div> 
        </div>
    </div>
</Card>