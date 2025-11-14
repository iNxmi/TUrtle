<script>
    import {m} from '$lib/paraglide/messages';
    import {
        Table,
        TableBody,
        TableBodyCell,
        TableBodyRow,
        TableHead,
        TableHeadCell,
        Heading
    } from 'flowbite-svelte';

    let {data} = $props();
    const page = data.page;
</script>

<div>
    <Heading tag="h3">{m.admin_users__title()}</Heading>

    <Table hoverable={true}>
        <TableHead>
            <TableHeadCell class="bg-secondary">{m.admin_users__student_id_label()}</TableHeadCell>
            <TableHeadCell class="bg-secondary">{m.admin_users__username_label()}</TableHeadCell>
            <TableHeadCell class="bg-secondary">{m.admin_users__last_name_label()}</TableHeadCell>
            <TableHeadCell class="bg-secondary">{m.admin_users__first_name_label()}</TableHeadCell>
            <TableHeadCell class="bg-secondary">{m.admin_users__email_label()}</TableHeadCell>
            <TableHeadCell class="bg-secondary">{m.admin_users__created_at_label()}</TableHeadCell>
        </TableHead>

        <TableBody>
            {#each page.content as user}
                <TableBodyRow onclick={() => window.location.href = `/admin/users/${user.username}`} class="hover:cursor-pointer">
                    <TableBodyCell>{user.studentId}</TableBodyCell>
                    <TableBodyCell>{user.username}</TableBodyCell>
                    <TableBodyCell>{user.firstName}</TableBodyCell>
                    <TableBodyCell>{user.lastName}</TableBodyCell>
                    <TableBodyCell>{user.email}</TableBodyCell>
                    <TableBodyCell>{(new Date(user.createdAt)).toLocaleString()}</TableBodyCell>
                </TableBodyRow>
            {/each}
        </TableBody>
    </Table>
</div>