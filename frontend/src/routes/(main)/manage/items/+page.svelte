<script>
    import TableView from '$lib/components/TableView.svelte';
    import {m} from "$lib/paraglide/messages.js";
    import {goto} from "$app/navigation";

    let {data} = $props();

    const columns = [
        {field: 'id', label: m.manage_items_label_id(), enabled: false},
        {field: 'name', label: m.manage_items_label_name()},
        {field: 'description', label: m.manage_items_label_description(), enabled: false},
        {field: 'needsConfirmation', label: m.manage_items_label_needs_confirmation(), enabled: false},
        {field: 'categoryId', label: m.manage_items_label_category_id()},
        {field: 'lockerId', label: m.manage_items_label_locker_id()},
        {
            field: 'acquiredAt',
            label: m.manage_items_label_acquired_at(),
            transform: (value) => new Date(value).toLocaleString()
        },
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
           onItemClicked={(item) => goto(`/manage/items/${item.id}`)}
/>