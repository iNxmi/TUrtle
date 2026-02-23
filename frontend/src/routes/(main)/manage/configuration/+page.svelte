<script>
    import TableView from "$lib/components/TableView.svelte"
    import {m} from '$lib/paraglide/messages.js';

    const {data} = $props();

    const columns = [
        {field: "id", label: m.manage_configuration_label_id()},
        {field: "key", label: m.manage_configuration_label_key()},
        {field: "type", label: m.manage_configuration_label_type()},
        {field: "value", label: m.manage_configuration_label_value()},
        {
            field: "updatedAt",
            label: m.manage_configuration_label_updated_at(),
            transform: (item) => new Date(item).toLocaleString()
        },
        {
            field: "createdAt",
            label: m.manage_configuration_label_created_at(),
            transform: (item) => new Date(item).toLocaleString()
        }
    ];
</script>

<TableView columns={columns}
           contentPage={data.page}
/>