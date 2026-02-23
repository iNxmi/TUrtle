<script>
    import TableView from "$lib/components/TableView.svelte";
    import {m} from "$lib/paraglide/messages.js";

    let {data} = $props();

    const columns = [
        {field: 'id', label: m.manage_item_categories_label_id()},
        {field: 'name', label: m.manage_item_categories_label_name()},
        {
            field: 'updatedAt',
            label: m.manage_item_categories_label_updated_at(),
            transform: (item) => new Date(item).toLocaleString()
        },
        {
            field: 'createdAt',
            label: m.manage_item_categories_label_created_at(),
            transform: (item) => new Date(item).toLocaleString()
        }
    ];
</script>

<TableView columns={columns}
           contentPage={data.page}
/>