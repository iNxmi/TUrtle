<script>
    import {m} from '$lib/paraglide/messages.js';
    import {Heading} from 'flowbite-svelte';
    import TUrtleTable from '$lib/components/TUrtleTable.svelte';

    const headers = [
        m.admin_auditlogs__id_label(),
        m.admin_auditlogs__ip_address_label(),
        m.admin_auditlogs__username_label(),
        m.admin_auditlogs__endpoint_label(),
        m.admin_auditlogs__http_method_label(),
        m.admin_auditlogs__created_at_label()
    ]

    let {data} = $props();
    const page = data.page;

    let items = [];
    for (const log of page.content) {
        const item = {
            onClick: () => window.location.href = `/admin/auditlogs/${log.id}`,
            values: [
                log.id,
                log.ipAddress,
                log.username,
                log.endpoint,
                log.httpMethod,
                (new Date(log.createdAt)).toLocaleString()
            ]
        };
        items.push(item);
    }

    let pageInfo = {
        size: page.page.size,
        number: page.page.number,
        totalElements: page.page.totalElements,
        totalPages: page.page.totalPages
    }
</script>

<div class="flex flex-col gap-10">
    <Heading tag="h2" class="text-center">{m.admin_auditlogs__title()}</Heading>

    <!-- TODO improve redirect maybe with svelte or js props and js dynamis (this removes the feature to copy any url with search or pagination queries) -->
    <TUrtleTable
            headers={headers}
            items={items}
            page={pageInfo}
            onFirstPage={() => window.location.href = "/admin/auditlogs?pageNumber=0"}
            onPreviousPage={() => window.location.href = `/admin/auditlogs?pageNumber=${pageInfo.number - 1}`}
            onNextPage={() => window.location.href = `/admin/auditlogs?pageNumber=${pageInfo.number + 1}`}
            onLastPage={() => window.location.href = `/admin/auditlogs?pageNumber=${pageInfo.totalPages - 1}`}
            onSearch={(search) => window.location.href = `/admin/auditlogs?search=${search}`}
    />
</div>