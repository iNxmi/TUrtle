<script>
    import ItemDetailsPage from "$lib/components/ItemDetailsPage.svelte";

    let { data } = $props();

    const content = $derived([
        {
            name: '_Username_',
            value: data.action.username
        },
        {
            name: '_IP Address_',
            value: data.action.ipAddress
        },
        {
            name: '_Endpoint_',
            value: data.action.endpoint
        },
        {
            name: '_HTTP Method_',
            value: data.action.httpMethod
        },
        {
            name: '_Created At_',
            value: data.action.createdAt
        }
    ]);
</script>
<ItemDetailsPage title='_Action_' {content} />