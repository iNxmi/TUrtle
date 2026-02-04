<script>
    import TableView from "$lib/components/TableView.svelte";
    let { data } = $props();

    const headers = [
        {id: 'id', display: '_ID_'},
        {id: 'name', display: '_Name_'},
        {id: 'description', display: '_Description_'},
        {id: 'category', display: '_Category Name_'},
        {id: 'locker', display: '_Locker Number_'},
        {id: 'acquiredAt', display: '_Acquired At_'},
        {id: 'createdAt', display: '_Created At_'}
    ];
</script>
<TableView endpoint="/admin/devices" {headers} contentPage={data.page} />