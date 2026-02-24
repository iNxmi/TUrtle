<script>
    import TableView from "$lib/components/TableView.svelte";
    import {m} from "$lib/paraglide/messages.js";
    import {goto} from "$app/navigation";
    import CreateUserModal from "$lib/components/modal/manage/CreateUserModal.svelte";

    const {data} = $props();

    const columns = [
        {field: "id", label: m.manage_users_label_id(), enabled: false},
        {field: "status", label: m.manage_users_label_status()},
        {field: "username", label: m.manage_users_label_username()},
        {field: "firstName", label: m.manage_users_label_first_name()},
        {field: "lastName", label: m.manage_users_label_last_name()},
        {field: "email", label: m.manage_users_label_email()},
        {
            field: "updatedAt",
            label: m.manage_users_label_updated_at(),
            transform: (value) => new Date(value).toLocaleString(),
            enabled: false
        },
        {
            field: "createdAt",
            label: m.manage_users_label_created_at(),
            transform: (value) => new Date(value).toLocaleString(),
            enabled: false
        }
    ];

    let modal = $state(false);
</script>

<TableView columns={columns}
           contentPage={data.page}
           onItemClicked={(item) => goto(`/manage/users/${item.id}`)}
           onCreate={() => modal = true}
/>

{#if modal}
    <CreateUserModal bind:open={modal}/>
{/if}