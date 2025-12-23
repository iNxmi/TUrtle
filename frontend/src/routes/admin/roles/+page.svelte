<script>
    import {m} from '$lib/paraglide/messages.js';
    import {Heading} from 'flowbite-svelte';
    import TUrtleTable from '$lib/components/TUrtleTable.svelte';

    const headers = [
        m.admin_roles__id_label(),
        m.admin_roles__name_label(),
        m.admin_roles__created_at_label()
    ]

    let {data} = $props();
    const page = data.page;

    let items = [];
    for (const role of page.content) {
        const item = {
            onClick: () => window.location.href = `/admin/roles/${role.id}`,
            values: [
                role.id,
                role.name,
                (new Date(role.createdAt)).toLocaleString()
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
    <Heading tag="h2" class="text-center">{m.admin_roles__title()}</Heading>

    <!-- TODO improve redirect maybe with svelte or js props and js dynamis (this removes the feature to copy any url with search or pagination queries) -->
    <TUrtleTable
            headers={headers}
            items={items}
            page={pageInfo}
            onFirstPage={() => window.location.href = "/admin/roles?pageNumber=0"}
            onPreviousPage={() => window.location.href = `/admin/roles?pageNumber=${pageInfo.number - 1}`}
            onNextPage={() => window.location.href = `/admin/roles?pageNumber=${pageInfo.number + 1}`}
            onLastPage={() => window.location.href = `/admin/roles?pageNumber=${pageInfo.totalPages - 1}`}
            onSearch={(search) => window.location.href = `/admin/roles?search=${search}`}
    />
</div>