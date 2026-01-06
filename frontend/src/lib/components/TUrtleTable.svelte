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
        P,
        Hr,
        Card,
        ButtonGroup
    } from 'flowbite-svelte';

    import {
        AngleLeftOutline,
        AngleRightOutline,
        ChevronDoubleLeftOutline,
        ChevronDoubleRightOutline,
        PlusOutline,
        SearchOutline,
        RefreshOutline,
        CaretSortOutline,
        ArrowUpOutline,
        ArrowDownOutline
    } from 'flowbite-svelte-icons';

    const noop = () => {
    };

    let {
        headers = [],
        items = [],
        page = {},

        sortProperty,
        sortDirection,

        onCreate = noop,
        onSearch = noop,
        onReload = noop,
        onFirstPage = noop,
        onPreviousPage = noop,
        onNextPage = noop,
        onLastPage = noop,
        onHeaderClicked = noop,

        hideAdd = false,
        hideReload = false,
        hidePagination = false,
        hideCount = false,
        hideSearch = false,

        disableAdd = false,
        disableReload = false,
        disablePagination = false,
        disableSearch = false,
        showNewElementModal = $bindable()
    } = $props();

     onCreate = () => {
        showNewElementModal = true;
    }
    let search = $state("");
    
</script>

<Card class="min-w-full max-h-full overflow-clip">

    {#if !hideSearch || !hideAdd || !hideReload}
        <div class="flex flex-row gap-2 justify-between p-2">
            {#if !hideSearch}
                <ButtonGroup disabled={disableSearch}>
                    <Input placeholder={`_search_`} bind:value={search} disabled={disableSearch}/>
                    <Button class="cursor-pointer bg-orange-400"  onclick={() => onSearch(search)}>
                        <SearchOutline class="text-white"/>
                    </Button>
                </ButtonGroup>
            {/if}
                <div class="flex flex-row gap-2 justify-end">
            {#if !hideReload}
                <Button class="hover:cursor-pointer" onclick={() => onReload()} disabled={disableReload}>
                    <RefreshOutline/>
                </Button>
            {/if}

            {#if !hideAdd}
                <Button class="hover:cursor-pointer" onclick={onCreate} disabled={disableAdd}>
                    <PlusOutline/>
                </Button>
            {/if}
            </div>
        </div>

        <Hr class="m-0 p-0"/>
    {/if}

    <Table bor hoverable>
        <TableHead color="default">
            {#each headers as header}
                <TableHeadCell class="hover:cursor-pointer" onclick={() => onHeaderClicked(header.id)}>
                    <div class={header.id === sortProperty ? "flex items-center gap-1 text-orange-400" : "flex items-center gap-1"}>
                        <span>{header.display}</span>

                        {#if header.id === sortProperty && sortDirection === "ASC"}
                            <ArrowUpOutline/>
                        {:else if header.id === sortProperty && sortDirection === "DESC"}
                            <ArrowDownOutline/>
                        {:else}
                            <CaretSortOutline/>
                        {/if}
                    </div>
                </TableHeadCell>
            {/each}
        </TableHead>
        <TableBody>
            {#each items as item}
                <TableBodyRow class="hover:cursor-pointer hover:text-orange-400 border-gray-200" onclick={() => item.onClick()}>
                    {#each item.values as value}
                        <TableBodyCell>{value}</TableBodyCell>
                    {/each}
                </TableBodyRow>
            {/each}
        </TableBody>
    </Table>

    {#if !hideCount || !hidePagination}
        <Hr class="m-0 p-0"/>

        <div class="flex justify-between p-2">
            {#if !hideCount}
                <P class="flex items-center text-gray-500">
                    {page.number * page.size + 1} - {Math.min((page.number + 1) * page.size, page.totalElements)} ({page.totalElements})
                </P>
            {/if}

            {#if !hidePagination}
                <ButtonGroup disabled={disablePagination}>
                    <Button class="hover:cursor-pointer disabled:cursor-not-allowed" disabled={page.number <= 0}
                            onclick={() => onFirstPage()}>
                        <ChevronDoubleLeftOutline/>
                    </Button>

                    <Button class="hover:cursor-pointer disabled:cursor-not-allowed" disabled={page.number <= 0}
                            onclick={() => onPreviousPage()}>
                        <AngleLeftOutline/>
                    </Button>

                    <div class="border border-gray-100 dark:border-gray-700 text-orange-400 px-3 inline-flex items-center">
                         {`${page.number + 1} / ${page.totalPages}`}
                    </div>

                    <Button class="hover:cursor-pointer disabled:cursor-not-allowed"
                            disabled={page.number >= page.totalPages - 1} onclick={() => onNextPage()}>
                        <AngleRightOutline/>
                    </Button>

                    <Button class="hover:cursor-pointer disabled:cursor-not-allowed"
                            disabled={page.number >= page.totalPages - 1} onclick={() => onLastPage()}>
                        <ChevronDoubleRightOutline/>
                    </Button>
                </ButtonGroup>
            {/if}
        </div>
    {/if}
</Card>
