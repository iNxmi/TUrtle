<script>
    import {EmailTemplates} from "$lib/api";
    import EntityPage from "$lib/components/EntityPage.svelte";
    import {m} from '$lib/paraglide/messages.js';
    import {Input, Select, Textarea} from "flowbite-svelte";

    let {data} = $props();
    let entity = $derived(data.entity);

    const typeItems = $derived(data.types.map((type) => ({
        value: type,
        name: type
    })));

    const items = $derived([{
        label: m.manage_email_templates_label_id(),
        field: "id",
        component: Input,
        props: {
            value: entity.id
        }
    }, {
        label: m.manage_email_templates_label_name(),
        field: "name",
        editable: true,
        component: Input,
        props: {
            value: entity.name
        }
    }, {
        label: m.manage_email_templates_label_type(),
        field: "type",
        editable: true,
        component: Select,
        props: {
            value: entity.type,
            items: typeItems
        }
    }, {
        label: m.manage_email_templates_label_description(),
        field: "description",
        editable: true,
        component: Textarea,
        props: {
            value: entity.description
        }
    }, {
        label: m.manage_email_templates_label_subject(),
        field: "subject",
        editable: true,
        component: Input,
        props: {
            value: entity.subject
        }
    }, {
        label: m.manage_email_templates_label_content(),
        field: "content",
        editable: true,
        component: Textarea,
        props: {
            value: entity.content
        }
    }, [{
        label: m.manage_email_templates_label_created_at(),
        field: "createdAt",
        component: Input,
        props: {
            value: entity.createdAt
        }
    }, {
        label: m.manage_email_templates_label_updated_at(),
        field: "updatedAt",
        component: Input,
        props: {
            value: entity.updatedAt
        }
    }]]);
</script>

<EntityPage items={items}
            onPatch={(payload) => EmailTemplates.patch(entity.id, payload)}
            onDelete={() => EmailTemplates.delete(entity.id)}
/>