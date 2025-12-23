<script>
    import {m} from '$lib/paraglide/messages.js';
    import TableView from "$lib/components/TableView.svelte"

    import {Heading} from "flowbite-svelte";

    const {data} = $props();

    const headers = [
        {id: "id", display: m.admin_users__id_label()},
        {id: "username", display: m.admin_users__username_label()},
        {id: "firstName", display: m.admin_users__first_name_label()},
        {id: "lastName", display: m.admin_users__last_name_label()},
        {id: "email", display: m.admin_users__email_label()},
        {id: "createdAt", display: m.admin_users__created_at_label()},
    ]
</script>

<div class="flex flex-col gap-10">
    <Heading tag="h2" class="text-center">{m.admin_users__title()}</Heading>

    <TableView
            endpoint="/admin/users"
            headers={headers}
            contentPage={data.page}
    />
</div>
