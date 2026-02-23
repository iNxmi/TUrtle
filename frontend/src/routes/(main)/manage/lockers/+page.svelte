<script>
    import TableView from "$lib/components/TableView.svelte"
    import {m} from '$lib/paraglide/messages.js';

    const {data} = $props();

    const columns = [
        {field: "id", label: m.manage_lockers_label_id()},
        {field: "name", label: m.manage_lockers_label_name()},
        {field: "index", label: m.manage_lockers_label_index()},
        {
            field: "createdAt",
            label: m.manage_lockers_label_created_at(),
            transform: (item) => new Date(item).toLocaleString()
        }
    ];
</script>

<TableView columns={columns}
           contentPage={data.page}
/>