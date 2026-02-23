<script>
    import TUrtleTable from '$lib/components/TUrtleTable.svelte';

    import {page} from '$app/state';
    import {goto, invalidateAll} from '$app/navigation';

    let {
        columns = [],
        contentPage,
        onCreate,
        onItemClicked
    } = $props();

    let items = $derived.by(() => {
        let items = [];
        for (const entity of contentPage.content) {
            const values = []
            for (const column of columns)
                values.push(entity[column.field])
            items.push(values);
        }
        return items;
    });


    let pageInfo = $derived(contentPage.page);
    let searchParams = $derived(page.url.searchParams);
</script>

<TUrtleTable columns={columns}
             items={contentPage.content}
             page={pageInfo}

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

             onColumnClicked={(column) => {
                searchParams.set("sortProperty", column.field)

                if(searchParams.get("sortDirection") === "DESC") {
                    searchParams.set("sortDirection", "ASC")
                } else {
                    searchParams.set("sortDirection", "DESC")
                }

                goto(`?${searchParams.toString()}`, {invalidateAll: true});
             }}

             onItemClicked={onItemClicked}

             onReload={() => invalidateAll()}

             hideAdd={!onCreate}
             onCreate={onCreate}
/>
 