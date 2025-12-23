<script>
    import {m} from '$lib/paraglide/messages.js';
    import TableView from "$lib/components/TableView.svelte"

    import {Heading} from "flowbite-svelte";

    const {data} = $props();

    const headers = [
        {id: "id", display: m.admin_auditlogs__id_label()},
        {id: "username", display: m.admin_auditlogs__username_label()},
        {id: "ipAddress", display: m.admin_auditlogs__ip_address_label()},
        {id: "endpoint", display: m.admin_auditlogs__endpoint_label()},
        {id: "httpMethod", display: m.admin_auditlogs__http_method_label()},
        {id: "createdAt", display: m.admin_auditlogs__created_at_label()}
    ]
</script>

<div class="flex flex-col gap-10">
    <Heading tag="h2" class="text-center">{m.admin_auditlogs__title()}</Heading>

    <TableView
            endpoint="/admin/auditlogs"
            headers={headers}
            contentPage={data.page}
    />
</div>
