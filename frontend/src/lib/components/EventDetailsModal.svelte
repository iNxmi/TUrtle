<script>
    import { Modal, P, Label } from 'flowbite-svelte';
    import {CalendarMonthOutline, ClockOutline, ClockArrowOutline} from 'flowbite-svelte-icons';

    import { getContext } from 'svelte';

    let {showEventDetailsModal = $bindable(), selectedEvent, creator} = $props();

    let durationInNanos = $derived(Math.abs(selectedEvent.end - selectedEvent.start));
    let durationInHours = $derived(Math.floor( durationInNanos / (1000 * 60 * 60)));
    let durationInMinutes = $derived.by(() => {
    
        const duration = Math.floor(durationInNanos / (1000 * 60)) - durationInHours * 60;
        return duration < 10 ? '0'+duration : duration;
    });
</script>
{#if selectedEvent}
    <Modal title={selectedEvent.title + " "+ (selectedEvent.extendedProps.accessibility === "WHITELIST" ? "(_Invitation only_)" : "")} bind:open={showEventDetailsModal}>
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
                {`${durationInHours}:${durationInMinutes}`}
            </div>
        </div>
        <Label  class="font-bold">_Creator_</Label>
        <P>{creator.firstName+" "+creator.lastName}</P>
        <P class="font-bold">_Description_</P>
        <P>{selectedEvent.extendedProps.description}</P>
    </Modal>
{/if}
