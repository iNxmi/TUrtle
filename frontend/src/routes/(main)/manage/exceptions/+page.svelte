<script>
    import {m} from '$lib/paraglide/messages.js';
    import TableView from "$lib/components/TableView.svelte"

    import {Heading} from "flowbite-svelte";

    const {data} = $props();

    const headers = [
        {id: "id", display: m.admin_exceptions__id_label()},
        {id: "endpoint", display: m.admin_exceptions__endpoint_label()},
        {id: "exception", display: m.admin_exceptions__exception_label()},
        {id: "message", display: m.admin_exceptions__message_label()},
        {id: "createdAt", display: m.admin_exceptions__created_at_label()},
    ]
</script>

<div class="flex flex-col gap-10">
    <Heading tag="h2" class="text-center">{m.admin_exceptions__title()}</Heading>

    <TableView
            endpoint="/admin/exceptions"
            headers={headers}
            contentPage={data.page}
    />
</div>
