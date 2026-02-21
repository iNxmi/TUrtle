<script>
    import ItemDetailsPage from "$lib/components/ItemDetailsPage.svelte";
    import {m} from "$lib/paraglide/messages";

    let {data} = $props();

    let content = $derived([{
        name: m.manage_roles_id_label_id(),
        value: data.role.id,
    },{
        name: m.manage_roles_id_label_name(),
        value: data.role.name,
    }, {
        name: m.manage_roles_id_label_permissions(),
        value: data.role.permissions
    }]);
</script>

<ItemDetailsPage title={data.role.name} {content}></ItemDetailsPage>