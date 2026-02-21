<script>
    import TableView from "$lib/components/TableView.svelte"
    import {m} from '$lib/paraglide/messages.js';

    const {data} = $props();

    const headers = [
        {id: "id", display: m.manage_audit_logs_label_id()},
        {id: "username", display: m.manage_audit_logs_label_username()},
        {id: "ipAddress", display: m.manage_audit_logs_label_ip_address()},
        {id: "endpoint", display: m.manage_audit_logs_label_endpoint()},
        {id: "httpMethod", display: m.manage_audit_logs_label_http_method()},
        {id: "createdAt", display: m.manage_audit_logs_label_created_at()}
    ];
</script>

<TableView endpoint="/manage/audit-logs"
           headers={headers}
           contentPage={data.page}
/>