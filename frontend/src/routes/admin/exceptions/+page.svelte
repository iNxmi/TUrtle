<script>
    import {m} from '$lib/paraglide/messages.js';
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
    <Heading tag="h3">{m.admin_exceptions__title()}</Heading>

    <Table hoverable={true}>
        <TableHead>
            <TableHeadCell>{m.admin_exceptions__id_label()}</TableHeadCell>
            <TableHeadCell>{m.admin_exceptions__endpoint_label()}</TableHeadCell>
            <TableHeadCell>{m.admin_exceptions__exception_label()}</TableHeadCell>
            <TableHeadCell>{m.admin_exceptions__message_label()}</TableHeadCell>
            <TableHeadCell>{m.admin_exceptions__created_at_label()}</TableHeadCell>
        </TableHead>

        <TableBody>
            {#each page.content as exception}
                <TableBodyRow onclick={() => window.location.href = `/admin/exceptions/${exception.id}`} class="hover:cursor-pointer">
                    <TableBodyCell>{exception.id}</TableBodyCell>
                    <TableBodyCell>{exception.endpoint}</TableBodyCell>
                    <TableBodyCell>{exception.exception}</TableBodyCell>
                    <TableBodyCell>{exception.message}</TableBodyCell>
                    <TableBodyCell>{(new Date(exception.createdAt)).toLocaleString()}</TableBodyCell>
                </TableBodyRow>
            {/each}
        </TableBody>
    </Table>
</div>