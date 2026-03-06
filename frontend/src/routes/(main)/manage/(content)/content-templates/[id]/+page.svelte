<script>
    import {ContentTemplates} from "$lib/api";
    import EntityPage from "$lib/components/EntityPage.svelte";
    import {m} from '$lib/paraglide/messages.js';
    import {Input, Textarea, Select} from "flowbite-svelte";

    let {data} = $props();

    let template = $derived(data.template);

    let typeItems = $derived(data.types.map((type) => ({
        value: type,
        name: type
    })));

    const items = $derived([{
        label: m.manage_content_templates_label_id(),
        field: "id",
        component: Input,
        props: {
            value: template.id
        }
    }, {
        label: m.manage_content_templates_label_name(),
        field: "name",
        editable: true,
        component: Input,
        props: {
            value: template.name
        }
    },{
        label: m.manage_content_templates_label_type(),
        field: "type",
        editable: true,
        component: Select,
        props: {
            value: template.type,
            items: typeItems
        }
    }, {
        label: m.manage_content_templates_label_description(),
        field: "description",
        editable: true,
        component: Textarea,
        props: {
            value: template.description
        }
    }, {
        label: m.manage_content_templates_label_content(),
        field: "content",
        editable: true,
        component: Textarea,
        props: {
            value: template.content
        }
    }, [{
        label: m.manage_content_templates_label_created_at(),
        field: "createdAt",
        component: Input,
        props: {
            value: template.createdAt
        }
    }, {
        label: m.manage_content_templates_label_updated_at(),
        field: "updatedAt",
        component: Input,
        props: {
            value: template.updatedAt
        }
    }]]);
</script>

<EntityPage items={items} onPatch={(payload) => ContentTemplates.patch(template.id, payload)}/>