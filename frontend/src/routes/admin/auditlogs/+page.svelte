<script>
    import {m} from '$lib/paraglide/messages.js';
    import TableView from "$lib/components/TableView.svelte"

    import {Heading, Tabs, TabItem, button} from "flowbite-svelte";
	import { page } from '$app/state';
	import { goto, invalidateAll } from '$app/navigation';
	import { BugSolid, BookOpenSolid } from 'flowbite-svelte-icons';

    const {data} = $props();
    
    let currentTab = $state(page.url.searchParams.get("endpoint" || '/auditlogs'));

    const headers = [
        {id: "id", display: m.admin_auditlogs__id_label()},
        {id: "username", display: m.admin_auditlogs__username_label()},
        {id: "ipAddress", display: m.admin_auditlogs__ip_address_label()},
        {id: "endpoint", display: m.admin_auditlogs__endpoint_label()},
        {id: "httpMethod", display: m.admin_auditlogs__http_method_label()},
        {id: "createdAt", display: m.admin_auditlogs__created_at_label()}
    ]
</script>

<Tabs tabStyle="underline">
    <TabItem onclick={() => goto('?endpoint=/auditlogs', {invalidateAll: true})} classes={{button:"cursor-pointer"}} open={currentTab === '/auditlogs'}>
        {#snippet titleSlot()}
        <div class="flex items-center gap-2">
            <BookOpenSolid size="md"/>
            <span>_Auditlogs_</span>
        </div>
        {/snippet}
        <div class="flex flex-col gap-10">
            <Heading tag="h2" class="text-center">{m.admin_auditlogs__title()}</Heading>
        
            <TableView
                    endpoint="/admin/auditlogs"
                    headers={headers}
                    contentPage={data.page}
            />
        </div>

    </TabItem>
    <TabItem onclick={() => goto('?endpoint=/exceptions', {invalidateAll: true})} classes={{button:"cursor-pointer"}} open={currentTab === '/exceptions'}>
        {#snippet titleSlot()}
            <div class="flex items-center gap-2">
                <BugSolid size="md"/>
                <span>_Exceptions_</span>
            </div>
        {/snippet}
        <div class="flex flex-col gap-10">
            <Heading tag="h2" class="text-center">{m.admin_exceptions__title()}</Heading>
        
            <TableView
                    endpoint="/admin/auditlogs"
                    headers={headers}
                    contentPage={data.page}
            />
        </div>

    </TabItem>

</Tabs>
