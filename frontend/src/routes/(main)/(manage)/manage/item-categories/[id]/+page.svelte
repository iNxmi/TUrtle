<script>
    import ItemDetailsPage from "$lib/components/ItemDetailsPage.svelte";

    let {data} = $props();

    let content = $derived([
        {name: '_Name_', value: data.deviceCategory.name},
        {name: '_Updated At_', value: data.deviceCategory.updatedAt},
        {name: '_Created At_', value: data.deviceCategory.createdAt}
    ]);
</script>
<ItemDetailsPage title="_Device Category_" {content}/>