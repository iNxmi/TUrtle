<script>
    import {m} from '$lib/paraglide/messages.js';
    import TableView from "$lib/components/TableView.svelte"

    const {data} = $props();

    const headers = [
        {id: "id", display: m.manage_exceptions_label_id()},
        {id: "endpoint", display: m.manage_exceptions_label_endpoint()},
        {id: "exception", display: m.manage_exceptions_label_exception()},
        {id: "message", display: m.manage_exceptions_label_message()},
        {id: "createdAt", display: m.manage_exceptions_label_created_at()},
    ]
</script>

<TableView endpoint="/manage/exceptions"
           headers={headers}
           contentPage={data.page}
/>
