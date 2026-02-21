<script>
    import TableView from "$lib/components/TableView.svelte"
    import {m} from '$lib/paraglide/messages.js';

    const {data} = $props();

    const headers = [
        {id: "id", display: m.manage_configuration_label_id()},
        {id: "key", display: m.manage_configuration_label_key()},
        {id: "type", display: m.manage_configuration_label_type()},
        {id: "value", display: m.manage_configuration_label_value()},
        {id: "updatedAt", display: m.manage_configuration_label_updated_at()},
        {id: "createdAt", display: m.manage_configuration_label_created_at()}
    ];
</script>

<TableView endpoint="/manage/configuration"
           headers={headers}
           contentPage={data.page}
/>