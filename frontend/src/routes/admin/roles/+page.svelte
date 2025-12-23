<script>
    import {m} from '$lib/paraglide/messages.js';
    import TableView from "$lib/components/TableView.svelte"

    import {Heading} from "flowbite-svelte";

    const {data} = $props();

    const headers = [
        {id: "id", display: m.admin_roles__id_label()},
        {id: "name", display: m.admin_roles__name_label()},
        {id: "createdAt", display: m.admin_roles__created_at_label()},
    ]
</script>

<div class="flex flex-col gap-10">
    <Heading tag="h2" class="text-center">{m.admin_roles__title()}</Heading>

    <TableView
            endpoint="/admin/roles"
            headers={headers}
            contentPage={data.page}
    />
</div>
