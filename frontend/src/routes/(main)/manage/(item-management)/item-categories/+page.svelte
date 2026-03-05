<script>
    import TableView from "$lib/components/TableView.svelte";
    import {m} from "$lib/paraglide/messages.js";
    import {goto} from "$app/navigation";
    import CreateItemCategoryModal from "$lib/components/modal/manage/CreateItemCategoryModal.svelte";

    let {data} = $props();

    const columns = [
        {field: "id", label: m.manage_item_categories_label_id(), enabled: false},
        {field: "name", label: m.manage_item_categories_label_name()},
        {
            field: "updatedAt",
            label: m.manage_item_categories_label_updated_at(),
            transform: (value) => new Date(value).toLocaleString(),
            enabled: false
        },
        {
            field: "createdAt",
            label: m.manage_item_categories_label_created_at(),
            transform: (value) => new Date(value).toLocaleString(),
            enabled: false
        }
    ];

    let modal = $state(false);
</script>

<TableView columns={columns}
           contentPage={data.page}
           onItemClicked={(item) => goto(`/manage/item-categories/${item.id}`)}
           onCreate={() => modal = true}
/>

{#if modal}
    <CreateItemCategoryModal bind:open={modal}/>
{/if}