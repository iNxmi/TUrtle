<script>
    import {
        TableBody,
        TableBodyCell,
        TableBodyRow,
        TableHead,
        TableHeadCell,
        Table,
        Input,
        Button,
        P,
        Hr,
        Select,
        Label,
        Card,
        ButtonGroup
    } from 'flowbite-svelte';

    import {
        AngleLeftOutline,
        AngleRightOutline,
        ChevronDoubleLeftOutline,
        ChevronDoubleRightOutline,
        PlusOutline,
        SearchOutline,
        RefreshOutline
    } from 'flowbite-svelte-icons';

    let {
        endpoint,
        headers,
        transformation,

        showAdd, showReload, showPagination, showCount, showSearch,
        disableAdd, disableReload, disablePagination, disableSearch,

        onCreate,
        onSearch,
        onReload,
        onFirstPage,
        onPreviousPage,
        onNextPage,
        onLastPage
    } = $props();

</script>

<div>

</div>