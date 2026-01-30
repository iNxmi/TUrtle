<script>
    import ItemDetailsPage from "$lib/components/ItemDetailsPage.svelte";
    let { data } = $props()

    let content = $derived(
        [
            {
                name: '_Name_',
                value: data.device.name,
            },
            {
                name: '_Description_',
                value: data.device.description
            },
            {
                name: '_Category_',
                value: data.category.name
            },
            {
                name: '_Locker_',
                value: data.device.locker
            },
            {
                name: '_Accquired At_',
                value: data.device.acquiredAt
            },
            {
                name: '_Created At_',
                value: data.device.createdAt
            }
        ]
    )
</script>
<ItemDetailsPage title=_Device_ {content} />