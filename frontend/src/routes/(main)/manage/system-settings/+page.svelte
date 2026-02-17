<script>
    import { Tabs, TabItem, Heading, Button} from 'flowbite-svelte';
    import { LockOutline, OpenDoorOutline } from 'flowbite-svelte-icons';
    import { page } from '$app/state';
	import { goto } from '$app/navigation';
    import { m } from '$lib/paraglide/messages.js';
    import TableView from '$lib/components/TableView.svelte';
    import {openLocker, openDoor} from '$lib/utils';
    import { rolesPath, hardwarePath, lockersPath } from '$lib/backend.js';

    import NewRoleModal from '$lib/components/NewRoleModal.svelte';

    let { data } = $props();

    let allPermissions = $derived(data.allPermissions);

    let currentTab = $state(page.url.searchParams.get('endpoint') || rolesPath);

    let showNewRoleModal = $state(false);

    const roleHeaders = [
        {id: "id", display: m.admin_auditlogs__id_label()},
        {id: 'name', display: '_Name_'},
        {id: 'createdAt', display: '_Created At_'}
    ];

    async function lockLocker(locker) {
        const payload = {
            locked: locker.locked !== true
        }

        await request(`${lockersPath}/${locker.id}`, {
            method: "PATCH",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(payload)
        });

       /*  location.reload() */
       invalidateAll();
    }

    async function lockDoor() {
        //TODO: API/Backend Implementation
    }
</script>
<Tabs tabStyle="underline">
    {#if data.userPermissions.includes('MANAGE_ROLES')}
    <TabItem onclick={() => goto(`?endpoint=${rolesPath}`, {invalidateAll: true})} classes={{button: "cursor-pointer"}} open={currentTab === rolesPath}>
        {#snippet titleSlot()}
        <div class="flex items-center gap-2">
            <LockOutline size="md"/>
            <span>_Roles_</span>
        </div>
        {/snippet}
        <div class="flex flex-col gap-10">
            <Heading tag="h2" class="text-center">_Roles_</Heading>
        
            <TableView
                    endpoint="/admin/roles"
                    headers={roleHeaders}
                    contentPage={data.page}
                    bind:showNewElementModal={showNewRoleModal}
            />
        </div>
    </TabItem>
    {/if}
    {#if data.userPermissions.includes('MANAGE_DOOR') || data.userPermissions.includes('MANAGE_LOCKERS')}
    <TabItem onlick={() => goto(`?endpoint=${hardwarePath}`)} classes={{button: 'cursor-pointer'}} open={currentTab === '/hardwareoverrides'}>
        {#snippet titleSlot()}
        <div class="flex items-center gap-2">
            <OpenDoorOutline size="md"/>
            <span>_Hardware Overrides_</span>
        </div>
        {/snippet}
        <div class="flex flex-row gap-5">
            <div class="rounded-lg border-gray-200 p-1">
                <Heading class="mb-5" tag='h2'>_Locker_</Heading>
                {#if data.userPermissions.includes('MANAGE_LOCKERS')}
                    {#each data.lockers as locker, index}
                        {#if locker.isSoftwareUnlockable}
                            <span class="mb-2">_Locker_ {locker.name}_</span>
                            <div class="flex flex-row gap-2 mb-1">
                                <Button onclick={() => openLocker(locker.id)}>_Open_</Button>
                                <Button onclick={() => lockLocker(locker)} class="bg-red-600">
                                    {#if locker.locked === true}_Unlock_{:else}_Lock_{/if}
                                </Button>
                            </div>
                        {/if}
                    {/each}
                {/if}
            </div>
            {#if data.userPermissions.includes('MANAGE_DOOR')}
                <div class=" rounded-lg border-gray-200 p-1 text-center">
                    <Heading class="mb-5" tag="h2">_Door_</Heading>
                    <Button onclick={openDoor}>Open</Button>
                    <Button onclick={lockDoor} class="bg-red-600">Lock</Button>
                </div>
            {/if}
        </div>

        <!-- TODO -->
        <span class="text-red-500 font-bold">TODO: API/Backend Integration</span>
    </TabItem>
    {/if}
</Tabs>
<NewRoleModal bind:showNewRoleModal={showNewRoleModal} permissions={data.allPermissions}/>
