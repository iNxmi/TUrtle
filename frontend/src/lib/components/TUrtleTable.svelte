<script>
    import {
        Button,
        ButtonGroup,
        Card,
        Checkbox,
        Dropdown,
        DropdownItem,
        Hr,
        Input,
        Table,
        TableBody,
        TableBodyCell,
        TableBodyRow,
        TableHead,
        TableHeadCell
    } from 'flowbite-svelte';

    import {
        ChevronDoubleLeftOutline,
        ChevronDoubleRightOutline,
        ChevronDownOutline,
        ChevronLeftOutline,
        ChevronRightOutline,
        ChevronSortOutline,
        ChevronUpOutline,
        PlusOutline,
        RefreshOutline,
        SearchOutline,
        TableRowOutline
    } from 'flowbite-svelte-icons';

    //Example for columns prop,
    // const columns = [
    //     {field: "id", label: "ID", enabled: false},
    //     {field: "username", label: "Username"},
    //     {field: "createdAt", label: "Created At", transform: (item) => new Date(item).toLocaleString()}
    // ]

    let {
        columns = [],
        items = [],
        page = {},

        sortProperty,
        sortDirection,

        onCreate,
        onSearch,
        onReload,
        onFirstPage,
        onPreviousPage,
        onNextPage,
        onLastPage,
        onColumnClicked,
        onItemClicked,

        hideAdd = false,
        hideReload = false,
        hidePagination = false,
        hideCount = false,
        hideSearch = false,

        disableAdd = false,
        disableReload = false,
        disablePagination = false,
        disableSearch = true,

        class: className = "",
        ...rest
    } = $props();

    let activeColumns = $derived(Object.fromEntries(columns.map(column => [column.field, column.enabled !== false])));
    let search = $state("");
</script>

<Card class={`min-w-full dark:border-none ${className}`}>
    {#if !hideSearch || !hideAdd || !hideReload}
        <div class="flex gap-2 justify-between p-3">
            <div class="flex-1 flex">
                <ButtonGroup>
                    <Button>
                        <Dropdown simple>
                            {#each columns as column}
                                <DropdownItem>
                                    <Checkbox bind:checked={activeColumns[column.field]}>{column.label}</Checkbox>
                                </DropdownItem>
                            {/each}
                        </Dropdown>
                        <TableRowOutline/>
                    </Button>
                </ButtonGroup>
            </div>

            {#if !hideSearch}
                <ButtonGroup disabled={disableSearch} class="flex-2">
                    <Input placeholder="_search_" bind:value={search} disabled={disableSearch}/>

                    <Button onclick={() => onSearch?.(search)}>
                        <SearchOutline/>
                    </Button>
                </ButtonGroup>
            {/if}

            <div class="flex-1 flex justify-end">
                <ButtonGroup>
                    {#if !hideReload}
                        <Button class="hover:cursor-pointer" color="alternative" onclick={() => onReload?.()}
                                disabled={disableReload}>
                            <RefreshOutline/>
                        </Button>
                    {/if}

                    {#if !hideAdd}
                        <Button class="hover:cursor-pointer" color="orange" onclick={() => onCreate?.()}
                                disabled={disableAdd}>
                            <PlusOutline/>
                        </Button>
                    {/if}
                </ButtonGroup>
            </div>
        </div>

        <Hr class="m-0 p-0"/>
    {/if}

    <Table divClass="grow" hoverable>
        <TableHead>
            {#each columns as column}
                {#if activeColumns[column.field] === true}
                    <TableHeadCell class="hover:cursor-pointer" onclick={() => onColumnClicked?.(column)}>
                        <div class={`flex items-center gap-1 ${column.field === sortProperty ? "text-orange-400": ""}`}>
                            <span class="select-none">{column.label}</span>

                            {#if column.field === sortProperty && sortDirection === "ASC"}
                                <ChevronUpOutline/>
                            {:else if column.field === sortProperty && sortDirection === "DESC"}
                                <ChevronDownOutline/>
                            {:else}
                                <ChevronSortOutline/>
                            {/if}
                        </div>
                    </TableHeadCell>
                {/if}
            {/each}
        </TableHead>

        <TableBody>
            {#each items as item}
                <TableBodyRow class={`border-gray-200 ${onItemClicked !== undefined ? "hover:cursor-pointer": "" }`}
                              onclick={() => onItemClicked?.(item)}>
                    {#each columns as column}
                        {#if activeColumns[column.field] === true}
                            <TableBodyCell>
                                {#if column.transform === undefined}
                                    {item[column.field]}
                                {:else}
                                    {column.transform(item[column.field])}
                                {/if}
                            </TableBodyCell>
                        {/if}
                    {/each}
                </TableBodyRow>
            {/each}
        </TableBody>
    </Table>

    {#if !hideCount || !hidePagination}
        <Hr class="m-0 p-0"/>

        <div class="flex justify-between p-3">
            {#if !hideCount}
                <ButtonGroup>
                    <Button disabled>
                        {page.number * page.size + 1}
                        - {Math.min((page.number + 1) * page.size, page.totalElements)}
                        ({page.totalElements})
                    </Button>
                </ButtonGroup>
            {/if}

            {#if !hidePagination}
                <ButtonGroup disabled={disablePagination}>
                    <Button class="hover:cursor-pointer disabled:cursor-not-allowed" disabled={page.number <= 0}
                            onclick={() => onFirstPage?.()}>
                        <ChevronDoubleLeftOutline/>
                    </Button>

                    <Button class="hover:cursor-pointer disabled:cursor-not-allowed" disabled={page.number <= 0}
                            onclick={() => onPreviousPage?.()}>
                        <ChevronLeftOutline/>
                    </Button>

                    <Button disabled>
                        {`${page.number + 1} / ${page.totalPages}`}
                    </Button>

                    <Button class="hover:cursor-pointer disabled:cursor-not-allowed"
                            disabled={page.number >= page.totalPages - 1} onclick={() => onNextPage?.()}>
                        <ChevronRightOutline/>
                    </Button>

                    <Button class="hover:cursor-pointer disabled:cursor-not-allowed"
                            disabled={page.number >= page.totalPages - 1} onclick={() => onLastPage?.()}>
                        <ChevronDoubleRightOutline/>
                    </Button>
                </ButtonGroup>
            {/if}
        </div>
    {/if}
</Card>
