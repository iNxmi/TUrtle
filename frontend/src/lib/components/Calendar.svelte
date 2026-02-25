<script>
    import {onDestroy, onMount} from "svelte";

    import {Calendar} from '@fullcalendar/core';
    import dayGridPlugin from '@fullcalendar/daygrid';
    import timeGridPlugin from '@fullcalendar/timegrid';
    import listPlugin from '@fullcalendar/list';

    let {
        sources = $bindable([])
    } = $props();

    let element = $state(null);
    let calendar = $state(null);
    onMount(() => {
        calendar = new Calendar(element, {
            plugins: [timeGridPlugin, dayGridPlugin, listPlugin],
            initialView: "dayGridMonth",
            headerToolbar: {
                left: 'prev,next today',
                center: 'title',
                right: 'dayGridMonth,timeGridWeek'
            }
        });

        requestAnimationFrame(() => calendar.render());
    });

    $effect(() => {
        if (calendar === null)
            return

        calendar.getEventSources().forEach(source => source.remove());
        sources.forEach(source => calendar.addEventSource(source));
    });

    onDestroy(() => {
        calendar?.destroy();
    });
</script>

<div bind:this={element} class="w-full h-full"></div>