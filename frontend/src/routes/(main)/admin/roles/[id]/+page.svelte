<script>
    import ItemDetailsPage from "$lib/components/ItemDetailsPage.svelte";

    let { data } = $props();

    let content = $derived( [
        {
            name: '_Name_',
            value: data.role.name,
        },
        {
            name: '_Permissions_',
            value: data.role.permissions
        }
    ]);
</script>
<ItemDetailsPage title='_Role_' {content} ></ItemDetailsPage>