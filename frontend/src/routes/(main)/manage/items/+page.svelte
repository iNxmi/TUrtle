<script>
    import TableView from '$lib/components/TableView.svelte';
    import {m} from "$lib/paraglide/messages.js";

    let {data} = $props();

    const columns = [
        {field: 'id', label: m.manage_items_label_id(), enabled: false},
        {field: 'name', label: m.manage_items_label_name()},
        {field: 'acquiredAt', label: m.manage_items_label_acquired_at()},
        {
            field: "updatedAt",
            label: m.manage_items_label_updated_at(),
            transform: (value) => new Date(value).toLocaleString(),
            enabled: false
        },
        {
            field: "createdAt",
            label: m.manage_items_label_created_at(),
            transform: (value) => new Date(value).toLocaleString(),
            enabled: false
        }
    ];
</script>

<TableView columns={columns}
           contentPage={data.page}
/>