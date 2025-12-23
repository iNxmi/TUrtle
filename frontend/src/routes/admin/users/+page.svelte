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

    const sortProperty = $derived(
        () => $page.url.searchParams.get("sortProperty")
    );

    const sortDirection = $derived(
        () => $page.url.searchParams.get("sortDirection")
    );
</script>

<div class="flex flex-col gap-10">
    <Heading tag="h2" class="text-center">{m.admin_users__title()}</Heading>

    <!-- TODO improve redirect maybe with svelte or js props and js dynamis (this removes the feature to copy any url with search or pagination queries) -->
    <TUrtleTable
            headers={headers}
            items={items}
            page={pageInfo}

            sortProperty={sortProperty}
            sortDirection={sortDirection}

            onFirstPage={() => window.location.href = "/admin/users?pageNumber=0"}
            onPreviousPage={() => window.location.href = `/admin/users?pageNumber=${pageInfo.number - 1}`}
            onNextPage={() => window.location.href = `/admin/users?pageNumber=${pageInfo.number + 1}`}
            onLastPage={() => window.location.href = `/admin/users?pageNumber=${pageInfo.totalPages - 1}`}
            onSearch={(search) => window.location.href = `/admin/users?search=${search}`}
            onHeaderClicked={(header) => {
                if(sortDirection === "DESC") {
                    window.location.href = `/admin/users?sortProperty=${header}&sortDirection=ASC`
                } else {
                    window.location.href = `/admin/users?sortProperty=${header}&sortDirection=DESC`
                }
            }}
    />
</div>