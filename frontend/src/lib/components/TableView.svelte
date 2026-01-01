<script>
    import TUrtleTable from '$lib/components/TUrtleTable.svelte';

   let {
        endpoint,
        headers = [],
        contentPage
    } = $props();

    let items = $derived.by(() => {

        let items = [];
        for (const entity of contentPage.content) {
    
            const values = []
            for (const header of headers)
                values.push(entity[header.id])
    
            const item = {
                onClick: () => window.location.href = `${endpoint}/${entity.id}`,
                values: values
            };
            items.push(item);     
        }
        return items;
});
    

   let pageInfo = $derived(contentPage.page);

    import {page} from "$app/state";
    const searchParams = page.url.searchParams
</script>

<!-- TODO improve redirect maybe with svelte or js props and js dynamis (this removes the feature to copy any url with search, pagination or sort queries) -->
<TUrtleTable
    headers={headers}
    items={items}
    page={pageInfo}

    sortProperty={searchParams.get("sortProperty")}
    sortDirection={searchParams.get("sortDirection")}

    onFirstPage={() => {
        searchParams.set("pageNumber", 0)
        window.location.href = `${endpoint}?${searchParams.toString()}`
    }}
    onPreviousPage={() => {
        searchParams.set("pageNumber", pageInfo.number - 1)
        window.location.href = `${endpoint}?${searchParams.toString()}`
    }}
    onNextPage={() => {
        searchParams.set("pageNumber", pageInfo.number + 1)
        window.location.href = `${endpoint}?${searchParams.toString()}`
    }}
    onLastPage={() => {
        searchParams.set("pageNumber", pageInfo.totalPages - 1)
        window.location.href = `${endpoint}?${searchParams.toString()}`
    }}
    onSearch={(search) => {
        searchParams.set("search", search)
        window.location.href = `${endpoint}?${searchParams.toString()}`
    }}
    onHeaderClicked={(header) => {
        searchParams.set("sortProperty", header)

        if(searchParams.get("sortDirection") === "DESC") {
            searchParams.set("sortDirection", "ASC")
        } else {
            searchParams.set("sortDirection", "DESC")
        }

        window.location.href = `${endpoint}?${searchParams.toString()}`
    }}
    onReload={() => window.location.reload()}
/>
