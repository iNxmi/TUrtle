<script>
    import { Modal, P, Label } from 'flowbite-svelte';
    import {CalendarMonthOutline, ClockOutline, ClockArrowOutline} from 'flowbite-svelte-icons';

    let {showEventDetailsModal = $bindable(), selectedEvent} = $props();

</script>
{#if selectedEvent}
    <Modal title={selectedEvent.title + " "+ (selectedEvent.extendedProps.enableWhitelist ? "(_Invitation only_)" : "")} bind:open={showEventDetailsModal}>
        <div class="flex flex-wrap gap-4">
            <div class="flex items-center"> 
                <CalendarMonthOutline class="size-5 mr-2 text-csw" />
                    {selectedEvent.start.toLocaleDateString()}
            </div>
            <div class="flex items-center">
                <ClockOutline class="size-5 mr-2 text-csw"/>
                {selectedEvent.start.toLocaleTimeString().substring(0, 5) + " _Uhr_"}
            </div>
            <div class="flex items-center">
                <ClockArrowOutline class="size-5 mr-2 text-csw"/>
                {new Date(selectedEvent.end.getTime() - selectedEvent.start.getTime()).toLocaleTimeString().substring(0,5)}
            </div>
        </div>
        <Label  class="font-bold">_Creator_</Label>
        <P>{selectedEvent.extendedProps.creator.firstName+" "+selectedEvent.extendedProps.creator.lastName}</P>
        <P class="font-bold">_Description_</P>
        <P>{selectedEvent.extendedProps.description}</P>
    </Modal>
{/if}
