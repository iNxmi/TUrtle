<script>
    import {getContext, onMount} from 'svelte';
    import request from '$lib/api/api.js';
    import {convertEventToFrontend, fetchRoomBookings} from '$lib/utils.js'
    import EventDetailsModal from '$lib/components/EventDetailsModal.svelte';
    import {Calendar} from '@fullcalendar/core';
    import timeGridPlugin from '@fullcalendar/timegrid';
    import listPlugin from '@fullcalendar/list';
    import deLocale from '@fullcalendar/core/locales/de';

    let calendar = $state();

    let localeFunction = getContext('locale');

    let localeString = $derived(localeFunction());

    let showEventDetailsModal = $state(false);

    let selectedEvent = $state();

    let selectedEventCreatorName = $state();

    $effect(() => {

        if (calendar) {
            calendar.setOption('locale', localeString);
        }
    });
    onMount(() => {
        let calendarEl = document.getElementById('calendar');
        calendar = new Calendar(calendarEl, {
            plugins: [timeGridPlugin, listPlugin],
            locale: deLocale,
            /* aspectRatio: 2.1, */
            height: window.innerHeight - 135,
            events: async function (info, successCallback, failureCallback) {
                const fetchedData = await fetchRoomBookings(info);

                if (fetchedData) {
                    const events = fetchedData.map(event => (convertEventToFrontend(event)));
                    successCallback(events);
                } else {
                    failureCallback("Error");
                }
            },
            eventClick: async function (info) {
                info.jsEvent.preventDefault();
                selectedEvent = info.event;

                const response = await request(`/users/${selectedEvent.extendedProps.creator}`);
                selectedEventCreatorName = await response.json();
                showEventDetailsModal = true;
            },
            eventColor: 'oklch(75% 0.183 55.934)',
            slotLabelFormat: {
                hour: 'numeric',
                minute: '2-digit',
                omitZeroMinute: true,
                meridiem: 'short'
            },
            slotMinTime: '6:00:00',
            allDaySlot: false,
            weekends: false,
            initialView: 'timeGridWeek',
            headerToolbar: {
                left: 'prev,next today',
                center: 'title',
                right: 'timeGridWeek,listWeek'
            }
        });

        calendar.render();

        if (localeString === 'en') {
            calendar.setOption('locale', 'en-us');
        } else {
            calendar.setOption('locale', localeString);
        }
    });

</script>

<div id="calendar"></div>

<EventDetailsModal bind:showEventDetailsModal={showEventDetailsModal} {selectedEvent}
                   creator={selectedEventCreatorName}/>
 