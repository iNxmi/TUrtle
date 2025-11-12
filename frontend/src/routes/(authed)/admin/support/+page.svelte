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
    import {m} from '$lib/paraglide/messages';

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
        </TableHead>

        <TableBody>
            {#each page.content as ticket}
                <TableBodyRow>
                    <TableBodyCell>{ticket.id}</TableBodyCell>
                    <TableBodyCell>{ticket.urgency}</TableBodyCell>
                    <TableBodyCell>{ticket.category}</TableBodyCell>
                    <TableBodyCell>{ticket.subject}</TableBodyCell>
                    <TableBodyCell>{ticket.email}</TableBodyCell>
                </TableBodyRow>
            {/each}
        </TableBody>
    </Table>
</div>