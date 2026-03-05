<script>
    import {m} from '$lib/paraglide/messages.js';
    import TableView from "$lib/components/TableView.svelte"
    import {goto} from "$app/navigation";

    const {data} = $props();

    const columns = [
        {field: "id", label: m.manage_email_templates_label_id(), enabled: false},
        {field: "name", label: m.manage_email_templates_label_name()},
        {field: "type", label: m.manage_email_templates_label_type()},
        {field: "description", label: m.manage_email_templates_label_description()},
        {
            field: "updatedAt",
            label: m.manage_users_label_updated_at(),
            transform: (value) => new Date(value).toLocaleString(),
            enabled: false
        },
        {
            field: "createdAt",
            label: m.manage_users_label_created_at(),
            transform: (value) => new Date(value).toLocaleString(),
            enabled: false
        }
    ];
</script>

<TableView columns={columns}
           contentPage={data.page}
           onItemClicked={(item) => goto(`/manage/email-templates/${item.id}`)}
/>