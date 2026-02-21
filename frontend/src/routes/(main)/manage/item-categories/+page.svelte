<script>
    import TableView from "$lib/components/TableView.svelte";
    import {m} from "$lib/paraglide/messages";

    let {data} = $props();

    const headers = [
        {id: 'id', display: m.manage_item_categories_label_id()},
        {id: 'name', display: m.manage_item_categories_label_name()},
        {id: 'updatedAt', display: m.manage_item_categories_label_updated_at()},
        {id: 'createdAt', display: m.manage_item_categories_label_created_at()}
    ];
</script>

<TableView endpoint="/manage/item-categories"
           headers={headers}
           contentPage={data.page}
/>