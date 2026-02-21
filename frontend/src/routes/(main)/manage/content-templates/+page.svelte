<script>
    import {m} from '$lib/paraglide/messages.js';
    import TableView from "$lib/components/TableView.svelte"

    const {data} = $props();

    const headers = [
        {id: "id", display: m.admin_templates__id_label()},
        {id: "name", display: m.admin_templates__name_label()},
        {id: "description", display: m.admin_templates__description_label()},
        {id: "createdAt", display: m.admin_templates__created_at_label()}
    ]
</script>

<TableView
        endpoint="/manage/content-templates"
        headers={headers}
        contentPage={data.page}
/>
