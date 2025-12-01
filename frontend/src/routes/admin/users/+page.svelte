<script>
    import {m} from '$lib/paraglide/messages.js';
    import {
        FloatingLabelInput,
        Heading,
        Table,
        TableBody,
        TableBodyCell,
        TableBodyRow,
        TableHead,
        TableHeadCell,
        Input
    } from 'flowbite-svelte';

    const headCells = [
        {id: "input_username", label: m.admin_users__username_label()},
        {id: "input_first_name", label: m.admin_users__first_name_label()},
        {id: "input_last_name", label: m.admin_users__last_name_label()},
        {id: "input_email", label: m.admin_users__email_label()},
        {id: "input_student_id", label: m.admin_users__student_id_label()},
        {id: "input_roles", label: m.admin_users__roles_label()},
        {id: "input_created_at", label: m.admin_users__created_at_label()},
    ]

    let {data} = $props();
    const page = data.page;
</script>

<div>
    <Heading tag="h3" class="text-center">{m.admin_users__title()}</Heading>

    <Table shadow hoverable={true}>
        <TableHead>
            {#each headCells as cell}
                <TableHeadCell>
                    <FloatingLabelInput id={cell.id} type="text">
                        {cell.label}
                    </FloatingLabelInput>
                </TableHeadCell>
            {/each}
        </TableHead>

        <TableBody>
            {#each page.content as user}
                <TableBodyRow onclick={() => window.location.href = `/admin/users/${user.id}`}
                              class="hover:cursor-pointer">
                    <TableBodyCell>{user.username}</TableBodyCell>
                    <TableBodyCell>{user.lastName}</TableBodyCell>
                    <TableBodyCell>{user.firstName}</TableBodyCell>
                    <TableBodyCell>{user.email}</TableBodyCell>
                    <TableBodyCell>{user.studentId}</TableBodyCell>
                    <TableBodyCell>{JSON.stringify(user.roles)}</TableBodyCell>
                    <TableBodyCell>{(new Date(user.createdAt)).toLocaleString()}</TableBodyCell>
                </TableBodyRow>
            {/each}
        </TableBody>
    </Table>
</div>