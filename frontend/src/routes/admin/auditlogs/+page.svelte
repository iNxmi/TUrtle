<script>
    import {m} from '$lib/paraglide/messages.js';
    import {
        Table,
        TableBody,
        TableBodyCell,
        TableBodyRow,
        TableHead,
        TableHeadCell,
        Heading,
        FloatingLabelInput
    } from 'flowbite-svelte';

    let {data} = $props();
    const page = data.page;
</script>

<div>
    <Heading tag="h3" class="text-center">{m.admin_auditlogs__title()}</Heading>

    <Table hoverable={true}>
        <TableHead>
            <TableHeadCell>
                <FloatingLabelInput id="input_id" type="text">
                    {m.admin_auditlogs__id_label()}
                </FloatingLabelInput>
            </TableHeadCell>
            <TableHeadCell>
                <FloatingLabelInput id="input_ip_address" type="text">
                    {m.admin_auditlogs__ip_address_label()}
                </FloatingLabelInput>
            </TableHeadCell>
            <TableHeadCell>
                <FloatingLabelInput id="input_username" type="text">
                    {m.admin_auditlogs__username_label()}
                </FloatingLabelInput>
            </TableHeadCell>
            <TableHeadCell>
                <FloatingLabelInput id="input_endpoint" type="text">
                    {m.admin_auditlogs__endpoint_label()}
                </FloatingLabelInput>
            </TableHeadCell>
            <TableHeadCell>
                <FloatingLabelInput id="input_http_method" type="text">
                    {m.admin_auditlogs__http_method_label()}
                </FloatingLabelInput>
            </TableHeadCell>
            <TableHeadCell>
                <FloatingLabelInput id="input_created_at" type="text">
                    {m.admin_auditlogs__created_at_label()}
                </FloatingLabelInput>
            </TableHeadCell>
        </TableHead>

        <TableBody>
            {#each page.content as log}
                <TableBodyRow>
                    <TableBodyCell>{log.id}</TableBodyCell>
                    <TableBodyCell>{log.ipAddress}</TableBodyCell>
                    <TableBodyCell>{log.username}</TableBodyCell>
                    <TableBodyCell>{log.endpoint}</TableBodyCell>
                    <TableBodyCell>{log.httpMethod}</TableBodyCell>
                    <TableBodyCell>{(new Date(log.createdAt)).toLocaleString()}</TableBodyCell>
                </TableBodyRow>
            {/each}
        </TableBody>
    </Table>
</div>