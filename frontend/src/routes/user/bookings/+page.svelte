<script>
	import { getContext, onMount } from 'svelte';
	import { dev } from '$app/environment';
	/* import { Calendar } from '@fullcalendar/core';
	import timeGridPlugin from '@fullcalendar/timegrid';
	import listPlugin from '@fullcalendar/list';
	import deLocale from '@fullcalendar/core/locales/de'; */
	import { Calendar, TimeGrid, List } from '@event-calendar/core';

	let options = $state({
		aspectRatio: 2.1,
		eventSources:[ {
			url: dev ? '/dev/api/events' : '/api/events'
		}
	],
		eventColor: 'oklch(75% 0.183 55.934)',
		slotLabelFormat: {
			hour: 'numeric',
			minute: '2-digit',
			omitZeroMinute: true,
			meridiem: 'short'
		},
		slotMinTime: '6:00:00',
		allDaySlot: false,
		view: 'timeGridWeek',
		headerToolbar: {
			start: 'prev,next today',
			center: 'title',
			end: 'timeGridWeek,listWeek'
		}

	})

	let calendar = $state();

	let localeFunction = getContext('locale');

	let localeString = $derived(localeFunction());

	$effect(() => {

		if(calendar){
			calendar.setOption('locale', localeString);
		}

	})
	/* onMount(() => {
		let calendarEl = document.getElementById('calendar');
		calendar = new Calendar(calendarEl, {
			plugins: [timeGridPlugin, listPlugin],
			locale: deLocale,
			aspectRatio: 2.1,
			events: dev ? '/dev/api/events' : '/api/events',
			eventColor: 'oklch(75% 0.183 55.934)',
			slotLabelFormat: {
				hour: 'numeric',
				minute: '2-digit',
				omitZeroMinute: true,
				meridiem: 'short'
			},
			slotMinTime: '6:00:00',
			allDaySlot: false,
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
	}); */

</script>

<Calendar bind:this={calendar} plugins={[TimeGrid, List]} {options} />

<div id="calendar"></div>
