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
        TableHeadCell
    } from 'flowbite-svelte';

    let {data} = $props();
    const page = data.page;
</script>

<div>
    <Heading tag="h3" class="text-center">{m.admin_users__title()}</Heading>

    <Table hoverable={true}>
        <TableHead>
            <TableHeadCell>
                <FloatingLabelInput id="input_username" type="text">
                    {m.admin_users__username_label()}
                </FloatingLabelInput>
            </TableHeadCell>
            <TableHeadCell>
                <FloatingLabelInput id="input_first_name" type="text">
                    {m.admin_users__first_name_label()}
                </FloatingLabelInput>
            </TableHeadCell>
            <TableHeadCell>
                <FloatingLabelInput id="input_last_name" type="text">
                    {m.admin_users__last_name_label()}
                </FloatingLabelInput>
            </TableHeadCell>
            <TableHeadCell>
                <FloatingLabelInput id="input_email" type="text">
                    {m.admin_users__email_label()}
                </FloatingLabelInput>
            </TableHeadCell>
            <TableHeadCell>
                <FloatingLabelInput id="input_student_id" type="text">
                    {m.admin_users__student_id_label()}
                </FloatingLabelInput>
            </TableHeadCell>
            <TableHeadCell>
                <FloatingLabelInput id="input_role" type="text">
                    {m.admin_users__role_label()}
                </FloatingLabelInput>
            </TableHeadCell>
            <TableHeadCell>
                <FloatingLabelInput id="input_created_at" type="text">
                    {m.admin_users__created_at_label()}
                </FloatingLabelInput>
            </TableHeadCell>
        </TableHead>

        <TableBody>
            {#each page.content as user}
                <TableBodyRow onclick={() => window.location.href = `/admin/users/${user.username}`} class="hover:cursor-pointer">
                    <TableBodyCell>{user.username}</TableBodyCell>
                    <TableBodyCell>{user.lastName}</TableBodyCell>
                    <TableBodyCell>{user.firstName}</TableBodyCell>
                    <TableBodyCell>{user.email}</TableBodyCell>
                    <TableBodyCell>{user.studentId}</TableBodyCell>
                    <TableBodyCell>{user.role}</TableBodyCell>
                    <TableBodyCell>{(new Date(user.createdAt)).toLocaleString()}</TableBodyCell>
                </TableBodyRow>
            {/each}
        </TableBody>
    </Table>
</div>