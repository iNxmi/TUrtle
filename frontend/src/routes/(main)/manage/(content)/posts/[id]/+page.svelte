<script>
    import {m} from "$lib/paraglide/messages.js";
    import {Input, Textarea} from "flowbite-svelte";
    import EntityPage from "$lib/components/EntityPage.svelte"
    import {Posts} from "$lib/api";

    let {data} = $props();
    let entity = $derived(data.entity);

    const items = $derived([{
        label: m.manage_posts_label_id(),
        field: "id",
        component: Input,
        props: {
            value: entity.id,
        }
    }, {
        label: m.manage_posts_label_name(),
        field: "name",
        editable: true,
        component: Input,
        props: {
            value: entity.name,
        }
    }, {
        label: m.manage_posts_label_description(),
        field: "description",
        editable: true,
        component: Textarea,
        props: {
            value: entity.description,
        }
    },{
        label: m.manage_posts_label_title(),
        field: "title",
        editable: true,
        component: Input,
        props: {
            value: entity.title,
        }
    }, {
        label: m.manage_posts_label_content(),
        field: "content",
        editable: true,
        component: Textarea,
        props: {
            value: entity.content,
        }
    }, {
        label: m.manage_posts_label_enabled(),
        field: "enabled",
        editable: true,
        component: Input,
        props: {
            value: entity.enabled,
        }
    }, [{
        label: m.manage_posts_label_created_at(),
        field: "createdAt",
        component: Input,
        props: {
            value: entity.createdAt,
        }
    }, {
        label: m.manage_posts_label_updated_at(),
        field: "updatedAt",
        component: Input,
        props: {
            value: entity.updatedAt,
        }
    }]]);
</script>

<EntityPage items={items}
            onPatch={(payload) => Posts.patch(entity.id, payload)}
            onDelete={() => Posts.delete(entity.id)}
/>