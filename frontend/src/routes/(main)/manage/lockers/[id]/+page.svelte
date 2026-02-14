<script>
    import TableView from "$lib/components/TableView.svelte";
    let { data } = $props();

    let devices = $derived(data.page[0].content);
    let deviceBookings = $derived(data.page[1].content);
    const headers = [
        {id: 'id', display: '_ID_'},
        {id: 'name', display: '_Name_'},
        {id: 'status', display: '_Status_'},
        {id: 'description', display: '_Description_'},
        {id: 'category', display: '_Category Name_'},
        {id: 'locker', display: '_Locker Number_'},
        {id: 'acquiredAt', display: '_Acquired At_'},
        {id: 'createdAt', display: '_Created At_'}
    ];

    let devicesWithStatus = $derived(devices.map((device) =>  ({...device, ...(deviceBookings.find((booking) => booking.itemId === device.id) ? {status: deviceBookings.find((booking) => booking.itemId === device.id).status} : {status: '_Available_'})})));

    let contentPage = $derived({content: devicesWithStatus, page: data.page[0].page});
</script>
<TableView endpoint="/admin/devices" {headers} {contentPage} />