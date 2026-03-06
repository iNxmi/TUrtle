<script>
    import {m} from '$lib/paraglide/messages.js';
    import TableView from "$lib/components/TableView.svelte"
    import {goto} from "$app/navigation";
    import {resolve} from '$app/paths';
    import CreateFaqModal from "$lib/components/modal/manage/CreateFaqModal.svelte";

    const {data} = $props();

    const columns = [
        {field: "id", label: m.manage_faq_label_id(), enabled: false},
        {field: "name", label: m.manage_faq_label_name()},
        {field: "title", label: m.manage_faq_label_title()},
        {field: "enabled", label: m.manage_faq_label_enabled()},
        {
            field: "updatedAt",
            label: m.manage_faq_label_updated_at(),
            transform: (value) => new Date(value).toLocaleString(),
            enabled: false
        },
        {
            field: "createdAt",
            label: m.manage_faq_label_created_at(),
            transform: (value) => new Date(value).toLocaleString(),
            enabled: false
        }
    ];

    let modal = $state(false);
</script>

<TableView columns={columns}
           contentPage={data.page}
           onItemClicked={(item) => goto(resolve(`/manage/faq/${item.id}`))}
           onCreate={() => modal = true}
/>

{#if modal === true}
    <CreateFaqModal bind:open={modal}/>
{/if}