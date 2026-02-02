<script>
	import { goto } from '$app/navigation';
    import {Tabs, TabItem, Heading} from 'flowbite-svelte';
    import TableView from '$lib/components/TableView.svelte';
    import {devicesPath, deviceCategoriesPath} from '$lib/backend';
    import { page } from '$app/state';
    let {data} = $props();

    const devicesHeaders = [
        {id: 'id', display: '_ID_'},
        {id: 'name', display: '_Name_'},
        {id: 'description', display: '_Description_'},
        {id: 'category', display: '_Category Name_'},
        {id: 'locker', display: '_Locker Number_'},
        {id: 'acquiredAt', display: '_Acquired At_'},
        {id: 'createdAt', display: '_Created At_'}
    ];

    const deviceCategoryHeaders = [
        {id: 'id', display: '_ID_'},
        {id: 'name', display: '_Name_'},
        {id: 'updatedAt', display: '_Updated At_'},
        {id: 'createdAt', display: '_Created At_'}
    ];
    
    
    
    
    let showNewDeviceModal = $state(false);
    let showNewDeviceCategoryModal = $state(false);
    
    let currentTab = $state(page.url.searchParams.get("endpoint" || '/devices'));
</script>
<Tabs>
    {#if data.userPermissions.includes('MANAGE_DEVICES')}
    <TabItem onclick={() => goto(`?endpoint=${devicesPath}`, {invalidateAll: true}) } classes={{button: 'cursor-pointer'}} open={currentTab === devicesPath} title='_Devices_'>
        <Heading tag="h2" class="text-center mb-4">_Manage Devices_</Heading>
            <TableView
                    endpoint="/admin/devices"
                    headers={devicesHeaders}
                    contentPage={data.page}
                    bind:showNewElementModal={showNewDeviceModal}
            />
    </TabItem>
    {/if}
    {#if data.userPermissions.includes('MANAGE_DEVICE_CATEGORIES')}
    <TabItem onclick={() => goto(`?endpoint=${deviceCategoriesPath}`, {invalidateAll: true}) } classes={{button: 'cursor-pointer'}} open={currentTab === deviceCategoriesPath} title='_Device Categories_'>
        <Heading tag="h2" class="text-center mb-4">_Manage Device Categories_</Heading>
            <TableView
                    endpoint="/admin/devicecategories"
                    headers={deviceCategoryHeaders}
                    contentPage={data.page}
                    bind:showNewElementModal={showNewDeviceCategoryModal}
            />
    </TabItem>
    {/if}
</Tabs>
