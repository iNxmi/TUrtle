<script>
    import {m} from '$lib/paraglide/messages.js';
    import TableView from "$lib/components/TableView.svelte"
    import NewUserModal from '$lib/components/NewUserModal.svelte';
    import { Tabs, TabItem} from 'flowbite-svelte';
    import {goto} from "$app/navigation";
    import { page } from '$app/state';
    import { usersPath, itemsBookingsPath } from '$lib/backend.js';

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

    const itembookingsHeaders = [
        {id: "id", display: "_ID_"},
        {id: "start", display: "_Start Date_"},
        {id: "end", display: "_End Date_"},
        {id: "itemId", display: "_Device_"},
        {id: "userId", display:"_User Name_"},
        {id: "status", display: "_Status_"},
        {id: "createdAt", display: "_created at_"}
    ]
    let currentTab = $state(page.url.searchParams.get("endpoint" || '/users'));

</script>


       <Heading tag="h2" class="text-center mb-4">_Device Booking Management_</Heading>
            <TableView 
            endpoint="/admin/item-bookings"
            headers={itembookingsHeaders}
            contentPage={data.page}
            hideAdd={true}/>



<NewUserModal bind:showModal={showNewElementModal} />
