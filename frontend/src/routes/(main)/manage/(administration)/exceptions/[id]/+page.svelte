<script>
    import {Input, Textarea} from "flowbite-svelte";
    import {m} from '$lib/paraglide/messages.js';
    import EntityPage from "$lib/components/EntityPage.svelte";

    let {data} = $props();
    let entity = $derived(data.entity);

    let items = $derived([{
        label: m.manage_exceptions_label_id(),
        field: "id",
        component: Input,
        props: {
            value: entity.id
        }
    }, {
        label: m.manage_exceptions_label_endpoint(),
        field: "endpoint",
        component: Input,
        props: {
            value: entity.endpoint
        }
    }, {
        label: m.manage_exceptions_label_exception(),
        field: "exception",
        component: Input,
        props: {
            value: entity.exception
        }
    }, {
        label: m.manage_exceptions_label_message(),
        field: "message",
        component: Input,
        props: {
            value: entity.message
        }
    }, {
        label: m.manage_exceptions_label_stack_trace(),
        field: "stackTrace",
        component: Textarea,
        props: {
            value: entity.stackTrace
        }
    }, [{
        label: m.manage_exceptions_label_created_at(),
        field: "createdAt",
        component: Input,
        props: {
            value: entity.createdAt
        }
    }, {
        label: m.manage_exceptions_label_updated_at(),
        field: "updatedAt",
        component: Input,
        props: {
            value: entity.updatedAt
        }
    }]]);
</script>

<EntityPage items={items}/>