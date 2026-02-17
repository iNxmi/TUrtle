<script>
    import {m} from '$lib/paraglide/messages.js';
    import TableView from "$lib/components/TableView.svelte"

    import {Heading} from "flowbite-svelte";

    const {data} = $props();

    const headers = [
        {id: "id", display: m.admin_templates__id_label()},
        {id: "name", display: m.admin_templates__name_label()},
        {id: "description", display: m.admin_templates__description_label()},
        {id: "createdAt", display: m.admin_templates__created_at_label()}
    ]
</script>

<div class="flex flex-col gap-10">
    <Heading tag="h2" class="text-center">{m.admin_templates__title()}</Heading>

    <TableView
            endpoint="/admin/templates"
            headers={headers}
            contentPage={data.page}
    />
</div>
