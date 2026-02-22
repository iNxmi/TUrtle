<script>
    import {m} from '$lib/paraglide/messages.js';
    import TableView from "$lib/components/TableView.svelte"

    const {data} = $props();

    const headers = [
        {id: "id", display: m.manage_email_templates_label_id()},
        {id: "name", display: m.manage_email_templates_label_name()},
        {id: "createdAt", display: m.manage_email_templates_label_created_at()}
    ];
</script>

<TableView endpoint="/manage/email-templates"
           headers={headers}
           contentPage={data.page}
/>