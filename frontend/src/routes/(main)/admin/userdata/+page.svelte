<script>
    import {m} from '$lib/paraglide/messages.js';
    import TableView from "$lib/components/TableView.svelte"
    import NewUserModal from '$lib/components/NewUserModal.svelte';
    import { Tabs, TabItem} from 'flowbite-svelte';
    import {goto} from "$app/navigation";
    import { page } from '$app/state';
    import { usersPath, deviceBookingsPath } from '$lib/backend.js';

    import {Heading} from "flowbite-svelte";

    let {data} = $props();
    let showNewElementModal = $state(false);

    const usersHeaders = [
        {id: "id", display: m.admin_users__id_label()},
        {id: "username", display: m.admin_users__username_label()},
        {id: "firstName", display: m.admin_users__first_name_label()},
        {id: "lastName", display: m.admin_users__last_name_label()},
        {id: "email", display: m.admin_users__email_label()},
        {id: "createdAt", display: m.admin_users__created_at_label()},
    ];

    const devicebookingsHeaders = [
        {id: "id", display: "_ID_"},
        {id: "start", display: "_Start Date_"},
        {id: "end", display: "_End Date_"},
        {id: "deviceId", display: "_Device_"},
        {id: "userId", display:"_User Name_"},
        {id: "status", display: "_Status_"},
        {id: "createdAt", display: "_created at_"}
    ]
    let currentTab = $state(page.url.searchParams.get("endpoint" || '/users'));

</script>
<!-- <div class="flex flex-col gap-10"> -->
    <Tabs>
        {#if data.userPermissions.includes('MANAGE_USERS')}
        <TabItem onclick={() => goto(`?endpoint=${usersPath}`, {invalidateAll: true})} classes={{button:"cursor-pointer"}} open={currentTab === usersPath} title="_Users_">
            <Heading tag="h2" class="text-center mb-4">{m.admin_users__title()}</Heading>
            <TableView
                    endpoint="/admin/users"
                    headers={usersHeaders}
                    contentPage={data.page}
                    bind:showNewElementModal={showNewElementModal}
            />
        </TabItem>
        {/if}
        {#if data.userPermissions.includes('MANAGE_ITEM_BOOKINGS')}
        <TabItem onclick={() => goto(`?endpoint=${deviceBookingsPath}`, {invalidateAll: true})} classes={{button:"cursor-pointer"}} title="devicebookings" open={currentTab === deviceBookingsPath}>
            <Heading tag="h2" class="text-center mb-4">_Device Booking Management_</Heading>
            <TableView 
            endpoint="/admin/item-bookings"
            headers={devicebookingsHeaders}
            contentPage={data.page}
            hideAdd={true}/>
        </TabItem>
        {/if}
    </Tabs>
<!-- </div> -->
<NewUserModal bind:showModal={showNewElementModal} />
