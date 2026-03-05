<script>
    import {m} from "$lib/paraglide/messages.js";
    import {Input} from "flowbite-svelte";
    import EntityPage from "$lib/components/EntityPage.svelte";
    import {ItemCategories} from "$lib/api";

    let {data} = $props();
    let category = $derived(data.category);

    const items = $derived([{
        label: m.manage_item_categories_label_id(),
        field: "id",
        component: Input,
        props: {
            value: category.id
        }
    },{
        label: m.manage_item_categories_label_name(),
        field: "name",
        editable: true,
        component: Input,
        props: {
            value: category.name
        }
    },[{
        label: m.manage_item_categories_label_created_at(),
        field: "createdAt",
        component: Input,
        props: {
            value: category.createdAt
        }
    },{
        label: m.manage_item_categories_label_updated_at(),
        field: "updatedAt",
        component: Input,
        props: {
            value: category.updatedAt
        }
    }]]);

</script>

<EntityPage items={items} onPatch={(payload) => ItemCategories.patch(category.id, payload)}/>

<!--<Card>-->
<!--    <form class="flex flex-col gap-5">-->
<!--        <div>-->
<!--            <span>{m.manage_item_categories_label_id()}</span>-->
<!--            <Input type="text" value={category.id} disabled/>-->
<!--        </div>-->

<!--        <div>-->
<!--            <span>{m.manage_item_categories_label_name()}</span>-->
<!--            <Input type="text" value={category.name} disabled/>-->
<!--        </div>-->

<!--        <div class="flex gap-5">-->
<!--            <div class="flex-1">-->
<!--                <span>{m.manage_item_categories_label_created_at()}</span>-->
<!--                <Input type="text" value={(new Date(category.createdAt)).toLocaleString()} disabled/>-->
<!--            </div>-->
<!--            <div class="flex-1">-->
<!--                <span>{m.manage_item_categories_label_updated_at()}</span>-->
<!--                <Input type="text" value={(new Date(category.updatedAt)).toLocaleString()} disabled/>-->
<!--            </div>-->
<!--        </div>-->
<!--    </form>-->
<!--</Card>-->