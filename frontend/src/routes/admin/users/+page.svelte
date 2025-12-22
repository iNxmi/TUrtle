<script>
    import {m} from '$lib/paraglide/messages.js';
    import {Heading} from 'flowbite-svelte';
    import TUrtleTable from '$lib/components/TUrtleTable.svelte';

    const headers = [
        m.admin_users__username_label(),
        m.admin_users__first_name_label(),
        m.admin_users__last_name_label(),
        m.admin_users__email_label(),
        m.admin_users__created_at_label()
    ]

    let {data} = $props();
    const page = data.page;

    let items = [];
    for (const user of page.content) {
        const item = {
            onClick: () => window.location.href = `/admin/users/${user.id}`,
            values: [
                user.username,
                user.firstName,
                user.lastName,
                user.email,
                (new Date(user.createdAt)).toLocaleString()
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
    <Heading tag="h2" class="text-center">{m.admin_users__title()}</Heading>

    <!-- TODO improve redirect maybe with svelte or js props and js dynamis (this removes the feature to copy any url with search or pagination queries) -->
    <TUrtleTable
            headers={headers}
            items={items}
            page={pageInfo}
            onFirstPage={() => window.location.href = "/admin/users?pageNumber=0"}
            onPreviousPage={() => window.location.href = `/admin/users?pageNumber=${pageInfo.number - 1}`}
            onNextPage={() => window.location.href = `/admin/users?pageNumber=${pageInfo.number + 1}`}
            onLastPage={() => window.location.href = `/admin/users?pageNumber=${pageInfo.totalPages - 1}`}
            onSearch={(search) => window.location.href = `/admin/users?rsql=username=like=${search},firstName=like=${search},lastName=like=${search},email=like=${search}`}
    />
</div>