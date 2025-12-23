<script>
    import {m} from '$lib/paraglide/messages.js';
    import {Heading} from 'flowbite-svelte';
    import TUrtleTable from '$lib/components/TUrtleTable.svelte';

    const headers = [
        "_template_id_",
        "_template_name_",
        "_template_description_",
        "_template_created_at_",
    ]

    let {data} = $props();
    const page = data.page;

    let items = [];
    for (const template of page.content) {
        const item = {
            onClick: () => window.location.href = `/admin/templates/${template.id}`,
            values: [
                template.id,
                template.name,
                template.description,
                (new Date(template.createdAt)).toLocaleString()
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
    <Heading tag="h2" class="text-center">_manage_templates_</Heading>

    <!-- TODO improve redirect maybe with svelte or js props and js dynamis (this removes the feature to copy any url with search or pagination queries) -->
    <TUrtleTable
            headers={headers}
            items={items}
            page={pageInfo}
            onFirstPage={() => window.location.href = "/admin/templates?pageNumber=0"}
            onPreviousPage={() => window.location.href = `/admin/templates?pageNumber=${pageInfo.number - 1}`}
            onNextPage={() => window.location.href = `/admin/templates?pageNumber=${pageInfo.number + 1}`}
            onLastPage={() => window.location.href = `/admin/templates?pageNumber=${pageInfo.totalPages - 1}`}
            onSearch={(search) => window.location.href = `/admin/templates?search=${search}`}
    />
</div>