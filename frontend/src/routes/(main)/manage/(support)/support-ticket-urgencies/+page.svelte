<script>
    import TableView from "$lib/components/TableView.svelte";
    import {m} from "$lib/paraglide/messages.js";
    import {goto} from "$app/navigation";
    import CreateSupportTicketUrgencyModal from "$lib/components/modal/manage/CreateSupportTicketUrgencyModal.svelte";

    let {data} = $props();

    const columns = [
        {field: "id", label: m.manage_support_ticket_urgencies_label_id(), enabled: false},
        {field: "name", label: m.manage_support_ticket_urgencies_label_name()},
        {
            field: "updatedAt",
            label: m.manage_support_ticket_urgencies_label_updated_at(),
            transform: (value) => new Date(value).toLocaleString(),
            enabled: false
        },
        {
            field: "createdAt",
            label: m.manage_support_ticket_urgencies_label_created_at(),
            transform: (value) => new Date(value).toLocaleString(),
            enabled: false
        }
    ];

    let modal = $state(false);
</script>

<TableView columns={columns}
           contentPage={data.page}
           onItemClicked={(item) => goto(`/manage/support-ticket-urgencies/${item.id}`)}
           onCreate={() => modal = true}
/>

{#if modal}
    <CreateSupportTicketUrgencyModal bind:open={modal}/>
{/if}