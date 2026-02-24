<script>
    import TableView from "$lib/components/TableView.svelte"
    import {m} from '$lib/paraglide/messages.js';
    import {goto} from "$app/navigation";

    const {data} = $props();

    const columns = [
        {field: "id", label: m.manage_lockers_label_id(), enabled: false},
        {field: "name", label: m.manage_lockers_label_name()},
        {field: "index", label: m.manage_lockers_label_index()},
        {
            field: "updatedAt",
            label: m.manage_lockers_label_updated_at(),
            transform: (value) => new Date(value).toLocaleString(),
            enabled: false
        },
        {
            field: "createdAt",
            label: m.manage_lockers_label_created_at(),
            transform: (value) => new Date(value).toLocaleString(),
            enabled: false
        }
    ];
</script>

<TableView columns={columns}
           contentPage={data.page}
           onItemClicked={(item) => goto(`/manage/lockers/${item.id}`)}
/>