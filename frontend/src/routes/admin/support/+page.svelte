<script>
    import {
        Table,
        TableBody,
        TableBodyCell,
        TableBodyRow,
        TableHead,
        TableHeadCell,
        Heading
    } from 'flowbite-svelte';
    import {m} from '$lib/paraglide/messages.js';

    let {data} = $props();
    const page = data.page
</script>

<div>
    <Heading tag="h3">{m.admin_support__title()}</Heading>

    <Table hoverable={true}>
        <TableHead>
            <TableHeadCell class="bg-secondary">{m.admin_support__id_label()}</TableHeadCell>
            <TableHeadCell class="bg-secondary">{m.admin_support__urgency_label()}</TableHeadCell>
            <TableHeadCell class="bg-secondary">{m.admin_support__category_label()}</TableHeadCell>
            <TableHeadCell class="bg-secondary">{m.admin_support__subject_label()}</TableHeadCell>
            <TableHeadCell class="bg-secondary">{m.admin_support__email_label()}</TableHeadCell>
            <TableHeadCell class="bg-secondary">{m.admin_support__created_at_label()}</TableHeadCell>
        </TableHead>

        <TableBody>
            {#each page.content as ticket}
                <TableBodyRow onclick={() => window.location.href = `/admin/support/${ticket.id}`} class="hover:cursor-pointer">
                    <TableBodyCell>{ticket.id}</TableBodyCell>
                    <TableBodyCell>{ticket.urgency}</TableBodyCell>
                    <TableBodyCell>{ticket.category}</TableBodyCell>
                    <TableBodyCell>{ticket.subject}</TableBodyCell>
                    <TableBodyCell>{ticket.email}</TableBodyCell>
                    <TableBodyCell>{(new Date(ticket.createdAt)).toLocaleString()}</TableBodyCell>
                </TableBodyRow>
            {/each}
        </TableBody>
    </Table>
</div>