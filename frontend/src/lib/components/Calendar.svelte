<script>
    import {getContext, onDestroy, onMount} from "svelte";

    import {Calendar} from '@fullcalendar/core';
    import dayGridPlugin from '@fullcalendar/daygrid';
    import timeGridPlugin from '@fullcalendar/timegrid';

    let {
        sources = $bindable([]),
        onEventClicked,
        aspectRatio = 1.35,
        class: className = "",
        ...rest
    } = $props();

    let element = $state(null);
    let calendar = $state(null);

    let businessHours = getContext('businessHours');
    onMount(() => {
        calendar = new Calendar(element, {
            plugins: [timeGridPlugin, dayGridPlugin],
            initialView: "dayGridMonth",
            aspectRatio: aspectRatio,
            allDaySlot: false,
            firstDay: 1,
            businessHours: {
                startTime: businessHours.start,
                endTime: businessHours.end
            },
            headerToolbar: {
                left: 'prev,today,next',
                center: 'title',
                right: 'dayGridMonth,timeGridWeek'
            }
        });

        requestAnimationFrame(() => calendar.render());
    });

    let language = $state(getContext('locale'));

    $effect(() => {
        if (calendar === null)
            return

        calendar.setOption('locale', language);
    })

    $effect(() => {
        if (calendar === null)
            return

        calendar.setOption("eventClick", (info) => {
            onEventClicked?.(info);
        });
    });

    $effect(() => {
        if (calendar === null)
            return

        calendar.removeAllEventSources();
        sources.forEach(source => calendar.addEventSource(source));
    });

    onDestroy(() => {
        calendar?.destroy();
    });
</script>

<div bind:this={element} class={`w-full h-full ${className}`} {...rest}></div>