<script>
    import {onDestroy, onMount, getContext} from "svelte";

    import {Calendar} from '@fullcalendar/core';
    import dayGridPlugin from '@fullcalendar/daygrid';
    import timeGridPlugin from '@fullcalendar/timegrid';
    import listPlugin from '@fullcalendar/list';

    let {
        sources = $bindable([]),
        onEventClicked,
        class: className = "",
        aspectRatio = null,
        ...rest
    } = $props();

    let element = $state(null);
    let calendar = $state(null);

    let businessHours = getContext('businessHours');
    onMount(() => {
        calendar = new Calendar(element, {
            plugins: [timeGridPlugin, dayGridPlugin, listPlugin],
            initialView: "dayGridMonth",
            aspectRatio: aspectRatio || 1.35,
            allDaySlot: false,
            firstDay: 1,
            businessHours: {
                startTime: businessHours.start,
                endTime: businessHours.end
            },
            headerToolbar: {
                left: 'prev,next today',
                center: 'title',
                right: 'dayGridMonth,timeGridWeek'
            }
        });

        requestAnimationFrame(() => calendar.render());
    });

    let language = $state(getContext('locale'));

    $effect(() => {
        if (calendar === null) {
            return
        }

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