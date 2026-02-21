<script>
    import TableView from "./TableView.svelte";

    let {lockers} = $props();

    const headers = [
        {id: 'id', display: '_ID_'},
        {id: 'name', display: '_Name_'},
        {id: 'index', display: '_Index_'},
        {id: 'isSoftwareUnlockable', display: '_Unlockable_'},
        {id: 'locked', display: '_Locked_'},
        {id: 'updatedAt', display: '_Updated At_'},
        {id: 'createdAt', display: '_Created At_'}
    ]
</script>

<TableView endpoint="/admin/lockers" {headers} contentPage={lockers}/>