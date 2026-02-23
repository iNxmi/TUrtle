<script>
    import {m} from '$lib/paraglide/messages.js';
    import TableView from "$lib/components/TableView.svelte"

    const {data} = $props();

    const columns = [
        {field: "id", label: m.manage_exceptions_label_id()},
        {field: "endpoint", label: m.manage_exceptions_label_endpoint()},
        {field: "exception", label: m.manage_exceptions_label_exception()},
        {field: "message", label: m.manage_exceptions_label_message()},
        {
            field: "createdAt",
            label: m.manage_exceptions_label_created_at(),
            transform: (item) => new Date(item).toLocaleString()
        }
    ]
</script>

<TableView columns={columns}
           contentPage={data.page}
/>
