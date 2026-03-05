<script>
    import {m} from "$lib/paraglide/messages.js";
    import {Input, Textarea} from "flowbite-svelte";
    import EntityPage from "$lib/components/EntityPage.svelte"

    let {data} = $props();
    let faq = $derived(data.faq);

    const items = $derived([{
        label:m.manage_faq_label_id(),
        field: "id",
        component: Input,
        props: {
            value: faq.id,
        }
    },{
        label:m.manage_faq_label_name(),
        field: "name",
        editable: true,
        component: Input,
        props: {
            value: faq.name,
        }
    },{
        label:m.manage_faq_label_title(),
        field: "title",
        editable: true,
        component: Input,
        props: {
            value: faq.title,
        }
    },{
        label:m.manage_faq_label_content(),
        field: "content",
        editable: true,
        component: Textarea,
        props: {
            value: faq.content,
        }
    },{
        label:m.manage_faq_label_enabled(),
        field: "enabled",
        editable: true,
        component: Input,
        props: {
            value: faq.enabled,
        }
    },[{
        label:m.manage_faq_label_created_at(),
        field: "createdAt",
        component: Input,
        props: {
            value: faq.createdAt,
        }
    },{
        label:m.manage_faq_label_updated_at(),
        field: "updatedAt",
        component: Input,
        props: {
            value: faq.updatedAt,
        }
    }]]);
</script>

<EntityPage items={items} onPatch={(payload) => Faq.patch(faq.id, payload)}/>