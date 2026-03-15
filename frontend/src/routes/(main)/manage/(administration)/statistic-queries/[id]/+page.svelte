<script>
    import {m} from "$lib/paraglide/messages.js";
    import {Input, Select, Textarea} from "flowbite-svelte";
    import EntityPage from "$lib/components/EntityPage.svelte"
    import {StatisticQueries} from "$lib/api";

    let {data} = $props();
    let entity = $derived(data.entity);

    let typeItems = $derived(data.types.map((type) => ({
        value: type,
        name: type
    })));

    const items = $derived([{
        label: m.manage_statistic_queries_label_id(),
        field: "id",
        component: Input,
        props: {
            value: entity.id,
        }
    }, {
        label: m.manage_statistic_queries_label_name(),
        field: "name",
        editable: true,
        component: Input,
        props: {
            value: entity.name,
        }
    }, {
        label: m.manage_statistic_queries_label_description(),
        field: "description",
        editable: true,
        component: Input,
        props: {
            value: entity.description,
        }
    }, {
        label: m.manage_statistic_queries_label_query(),
        field: "query",
        editable: true,
        component: Textarea,
        props: {
            value: entity.query,
        }
    }, {
        label: m.manage_statistic_queries_label_type(),
        field: "type",
        editable: true,
        component: Select,
        props: {
            value: entity.type,
            items: typeItems
        }
    }, [{
        label: m.manage_statistic_queries_label_created_at(),
        field: "createdAt",
        component: Input,
        props: {
            value: entity.createdAt,
        }
    }, {
        label: m.manage_statistic_queries_label_updated_at(),
        field: "updatedAt",
        component: Input,
        props: {
            value: entity.updatedAt,
        }
    }]]);
</script>

<EntityPage items={items}
            onPatch={(payload) => StatisticQueries.patch(entity.id, payload)}
            onDelete={() => StatisticQueries.delete(entity.id)}
/>