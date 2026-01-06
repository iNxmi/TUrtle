<script>
    import TUrtleTable from '$lib/components/TUrtleTable.svelte';
    
    import { page } from '$app/state';
    import { goto, invalidateAll } from '$app/navigation';
   let {
        endpoint,
        headers = [],
        contentPage,
        showNewElementModal = $bindable(),
    } = $props();

    let items = $derived.by(() => {

        let items = [];
        for (const entity of contentPage.content) {
    
            const values = []
            for (const header of headers)
                values.push(entity[header.id])
    
            const item = {
                onClick: () => goto(`${("/admin"+page.url.searchParams.get('endpoint')) || endpoint}/${entity.id}`),
                values: values
            };
            items.push(item);     
        }
        return items;
});
    

   let pageInfo = $derived(contentPage.page);

    let searchParams = $derived(page.url.searchParams);
</script>

<!-- TODO improve redirect maybe with svelte or js props and js dynamis (this removes the feature to copy any url with search, pagination or sort queries) -->
<TUrtleTable
    headers={headers}
    items={items}
    page={pageInfo}
    bind:showNewElementModal={showNewElementModal}

    sortProperty={searchParams.get("sortProperty")}
    sortDirection={searchParams.get("sortDirection")}

    onFirstPage={() => {
        searchParams.set("pageNumber", 0)
        goto(`?${searchParams.toString()}`, {invalidateAll: true});
    }}
    onPreviousPage={() => {
        searchParams.set("pageNumber", pageInfo.number - 1)
        goto(`?${searchParams.toString()}`, {invalidateAll: true});
    }}
    onNextPage={() => {
        searchParams.set("pageNumber", pageInfo.number + 1)
        goto(`?${searchParams.toString()}`, {invalidateAll: true});
    }}
    onLastPage={() => {
        searchParams.set("pageNumber", pageInfo.totalPages - 1)
        goto(`?${searchParams.toString()}`, {invalidateAll: true});
    }}
    onSearch={(search) => {
        searchParams.set("search", search)
        goto(`?${searchParams.toString()}`, {invalidateAll: true});
    }}
    onHeaderClicked={(header) => {
        searchParams.set("sortProperty", header)

        if(searchParams.get("sortDirection") === "DESC") {
            searchParams.set("sortDirection", "ASC")
        } else {
            searchParams.set("sortDirection", "DESC")
        }

        goto(`?${searchParams.toString()}`, {invalidateAll: true});
    }}
    onReload={() => invalidateAll()}
/>
 