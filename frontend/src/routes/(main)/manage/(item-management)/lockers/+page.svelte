<script>
    import TableView from "$lib/components/TableView.svelte"
    import {m} from '$lib/paraglide/messages.js';
    import {goto} from "$app/navigation";
    import CreateLockerModal from "$lib/components/modal/manage/CreateLockerModal.svelte";

    const {data} = $props();

    const columns = [
        {field: "id", label: m.manage_lockers_label_id(), enabled: false},
        {field: "name", label: m.manage_lockers_label_name()},
        {field: "index", label: m.manage_lockers_label_index()},
        {field: "softwareUnlockable", label: m.manage_lockers_label_software_unlockable()},
        {field: "locked", label: m.manage_lockers_label_locked()},
        {
            field: "updatedAt",
            label: m.manage_lockers_label_updated_at(),
            transform: (value) => new Date(value).toLocaleString(),
            enabled: false
        },
        {
            field: "createdAt",
            label: m.manage_lockers_label_created_at(),
            transform: (value) => new Date(value).toLocaleString(),
            enabled: false
        }
    ];

    let modal = $state(false);
</script>

<TableView columns={columns}
           contentPage={data.page}
           onItemClicked={(item) => goto(`/manage/lockers/${item.id}`)}
           onCreate={() => modal = true}
/>

{#if modal}
    <CreateLockerModal bind:open={modal}/>
{/if}