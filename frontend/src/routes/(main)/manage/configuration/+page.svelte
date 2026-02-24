<script>
    import TableView from "$lib/components/TableView.svelte"
    import {m} from '$lib/paraglide/messages.js';

    const {data} = $props();

    const columns = [
        {field: "id", label: m.manage_configuration_label_id(), enabled: false},
        {field: "key", label: m.manage_configuration_label_key()},
        {field: "value", label: m.manage_configuration_label_value()},
        {field: "type", label: m.manage_configuration_label_type()},
        {
            field: "updatedAt",
            label: m.manage_configuration_label_updated_at(),
            transform: (value) => new Date(value).toLocaleString(),
            enabled: false
        },
        {
            field: "createdAt",
            label: m.manage_configuration_label_created_at(),
            transform: (value) => new Date(value).toLocaleString(),
            enabled: false
        }
    ];
</script>

<TableView columns={columns}
           contentPage={data.page}
/>