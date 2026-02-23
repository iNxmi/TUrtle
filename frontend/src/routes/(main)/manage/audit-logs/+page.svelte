<script>
    import TableView from "$lib/components/TableView.svelte"
    import {m} from '$lib/paraglide/messages.js';

    const {data} = $props();

    const columns = [
        {field: "id", label: m.manage_audit_logs_label_id()},
        {field: "userId", label: m.manage_audit_logs_label_user_id()},
        {field: "ipAddress", label: m.manage_audit_logs_label_ip_address()},
        {field: "endpoint", label: m.manage_audit_logs_label_endpoint()},
        {field: "httpMethod", label: m.manage_audit_logs_label_http_method()},
        {
            field: "createdAt",
            label: m.manage_audit_logs_label_created_at(),
            transform: (value) => new Date(value).toLocaleString()
        }
    ];
</script>

<TableView columns={columns}
           contentPage={data.page}
/>