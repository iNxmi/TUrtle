<script>
    import {m} from '$lib/paraglide/messages.js';
    import {Heading} from 'flowbite-svelte';
    import TUrtleTable from '$lib/components/TUrtleTable.svelte';

    const headers = [
        {id: "id", display: m.admin_users__id_label()},
        {id: "username", display: m.admin_users__username_label()},
        {id: "firstName", display: m.admin_users__first_name_label()},
        {id: "lastName", display: m.admin_users__last_name_label()},
        {id: "email", display: m.admin_users__email_label()},
        {id: "createdAt", display: m.admin_users__created_at_label()},
    ]

    let {data} = $props();
    const contentPage = data.page;

    let items = [];
    for (const user of contentPage.content) {
        const item = {
            onClick: () => window.location.href = `/admin/users/${user.id}`,
            values: [
                user.id,
                user.username,
                user.firstName,
                user.lastName,
                user.email,
                (new Date(user.createdAt)).toLocaleString()
            ]
        };
        items.push(item);
    }

    const pageInfo = {
        size: contentPage.page.size,
        number: contentPage.page.number,
        totalElements: contentPage.page.totalElements,
        totalPages: contentPage.page.totalPages
    }

    import {page} from "$app/state";
    const searchParams = page.url.searchParams
</script>

<div class="flex flex-col gap-10">
    <Heading tag="h2" class="text-center">{m.admin_users__title()}</Heading>

    <!-- TODO improve redirect maybe with svelte or js props and js dynamis (this removes the feature to copy any url with search, pagination or sort queries) -->
    <TUrtleTable
        headers={headers}
        items={items}
        page={pageInfo}

        sortProperty={searchParams.get("sortProperty")}
        sortDirection={searchParams.get("sortDirection")}

        onFirstPage={() => {
            searchParams.set("pageNumber", 0)
            window.location.href = `/admin/users?${searchParams.toString()}`
        }}
        onPreviousPage={() => {
            searchParams.set("pageNumber", pageInfo.number - 1)
            window.location.href = `/admin/users?${searchParams.toString()}`
        }}
        onNextPage={() => {
            searchParams.set("pageNumber", pageInfo.number + 1)
            window.location.href = `/admin/users?${searchParams.toString()}`
        }}
        onLastPage={() => {
            searchParams.set("pageNumber", pageInfo.totalPages - 1)
            window.location.href = `/admin/users?${searchParams.toString()}`
        }}
        onSearch={(search) => {
            searchParams.set("search", search)
            window.location.href = `/admin/users?${searchParams.toString()}`
        }}
        onHeaderClicked={(header) => {
            searchParams.set("sortProperty", header)

            if(searchParams.get("sortDirection") === "DESC") {
                searchParams.set("sortDirection", "ASC")
            } else {
                searchParams.set("sortDirection", "DESC")
            }

            window.location.href = `/admin/users?${searchParams.toString()}`
        }}
    />
</div>