<script>
    import {m} from '$lib/paraglide/messages.js';
    import TableView from "$lib/components/TableView.svelte"
    import {goto} from "$app/navigation";
    import CreateRoleModal from "$lib/components/modal/manage/CreateRoleModal.svelte";

    const {data} = $props();

    const columns = [
        {field: "id", label: m.manage_roles_label_id(), enabled: false},
        {field: "name", label: m.manage_roles_label_name()},
        {field: "type", label: m.manage_roles_label_type()},
        {
            field: "updatedAt",
            label: m.manage_roles_label_updated_at(),
            transform: (value) => new Date(value).toLocaleString(),
            enabled: false
        },
        {
            field: "createdAt",
            label: m.manage_roles_label_created_at(),
            transform: (value) => new Date(value).toLocaleString(),
            enabled: false
        }
    ]

    let modal = $state(false);
</script>

<TableView endpoint="/manage/roles"
           columns={columns}
           contentPage={data.page}
           onItemClicked={(item) => goto(`/manage/roles/${item.id}`)}
           onCreate={() => modal = true}
/>

{#if modal}
    <CreateRoleModal bind:open={modal} typeList={data.type} permissionList={data.permissions}/>
{/if}