<script>
    import { Modal, P } from 'flowbite-svelte';

    let {showEventDetailsModal = $bindable(), selectedEvent} = $props();

</script>
{#if selectedEvent}
    <Modal title={selectedEvent.title + " "+ (selectedEvent.extendedProps.enableWhitelist ? "(_Invitation only_)" : "")} bind:open={showEventDetailsModal}>
        <P>{selectedEvent.start.toLocaleDateString()+" "+selectedEvent.start.toLocaleTimeString()+" - "
        + selectedEvent.end.toLocaleDateString()+" "+selectedEvent.end.toLocaleTimeString()}</P>
        <P class="font-bold">_Creator_</P>
        <P>{selectedEvent.extendedProps.creator.firstName+" "+selectedEvent.extendedProps.creator.lastName}</P>
        <P class="font-bold">_Description_</P>
        <P>{selectedEvent.extendedProps.description}</P>
    </Modal>
{/if}
