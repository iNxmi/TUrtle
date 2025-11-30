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
    <Heading tag="h3" class="text-center">{m.admin_roles__title()}</Heading>

    <Table hoverable={true}>
        <TableHead>
            <TableHeadCell>
                <FloatingLabelInput id="input_id" type="text">
                    {m.admin_roles__id_label()}
                </FloatingLabelInput>
            </TableHeadCell>
            <TableHeadCell>
                <FloatingLabelInput id="input_name" type="text">
                    {m.admin_roles__name_label()}
                </FloatingLabelInput>
            </TableHeadCell>
            <TableHeadCell>
                <FloatingLabelInput id="input_color" type="text">
                    {m.admin_roles__color_label()}
                </FloatingLabelInput>
            </TableHeadCell>
            <TableHeadCell>
                <FloatingLabelInput id="input_created_at" type="text">
                    {m.admin_roles__created_at_label()}
                </FloatingLabelInput>
            </TableHeadCell>
        </TableHead>

        <TableBody>
            {#each page.content as role}
                <TableBodyRow onclick={() => window.location.href = `/admin/roles/${role.id}`} class="hover:cursor-pointer">
                    <TableBodyCell>{role.id}</TableBodyCell>
                    <TableBodyCell>{role.name}</TableBodyCell>
                    <TableBodyCell>{role.color}</TableBodyCell>
                    <TableBodyCell>{(new Date(role.createdAt)).toLocaleString()}</TableBodyCell>
                </TableBodyRow>
            {/each}
        </TableBody>
    </Table>
</div>