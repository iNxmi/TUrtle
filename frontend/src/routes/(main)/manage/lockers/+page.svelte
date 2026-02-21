<script>
    import TableView from "$lib/components/TableView.svelte"
    import {m} from '$lib/paraglide/messages.js';

    const {data} = $props();

    const headers = [
        {id: "id", display: m.manage_lockers_label_id()},
        {id: "name", display: m.manage_lockers_label_name()},
        {id: "index", display: m.manage_lockers_label_index()},
        {id: "createdAt", display: m.manage_lockers_label_created_at()}
    ];
</script>

<TableView endpoint="/manage/lockers"
           headers={headers}
           contentPage={data.page}
/>