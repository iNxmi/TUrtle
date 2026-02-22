<script>
    import TableView from "$lib/components/TableView.svelte";
    import {m} from "$lib/paraglide/messages.js";

    const {data} = $props();

    const headers = [
        {id: "id", display: m.manage_users_label_id()},
        {id: "username", display: m.manage_users_label_username()},
        {id: "firstName", display: m.manage_users_label_first_name()},
        {id: "lastName", display: m.manage_users_label_last_name()},
        {id: "email", display: m.manage_users_label_email()},
        {id: "createdAt", display: m.manage_users_label_created_at()},
    ];
</script>

<TableView endpoint="/manage/users"
           headers={headers}
           contentPage={data.page}
/>