<script>
    import TableView from '$lib/components/TableView.svelte';
    import {m} from "$lib/paraglide/messages";

    let {data} = $props();

    const headers = [
        {id: 'id', display: m.manage_items_label_id()},
        {id: 'name', display: m.manage_items_label_name()},
        {id: 'acquiredAt', display: m.manage_items_label_acquired_at()},
        {id: 'updatedAt', display: m.manage_items_label_updated_at()},
        {id: 'createdAt', display: m.manage_items_label_created_at()}
    ];
</script>

<TableView endpoint="/manage/items"
           headers={headers}
           contentPage={data.page}
/>
