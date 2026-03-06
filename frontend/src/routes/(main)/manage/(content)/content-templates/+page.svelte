<script>
    import {m} from '$lib/paraglide/messages.js';
    import TableView from "$lib/components/TableView.svelte"
	import { goto } from '$app/navigation';
	import { resolve } from '$app/paths';
    import CreateContentTemplateModal from "$lib/components/modal/manage/CreateContentTemplateModal.svelte";

    const {data} = $props();

    const columns = [
        {field: "id", label: m.manage_content_templates_label_id(), enabled: false},
        {field: "name", label: m.manage_content_templates_label_name()},
        {field: "type", label: m.manage_content_templates_label_type()},
        {field: "description", label: m.manage_content_templates_label_description()},
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

    let modal =$state(false);
</script>

<TableView columns={columns}
           contentPage={data.page}
           onItemClicked={(item) => goto(resolve(`/manage/content-templates/${item.id}`))}
           onCreate={() => modal = true}
/>

{#if modal}
    <CreateContentTemplateModal bind:open={modal}/>
{/if}
