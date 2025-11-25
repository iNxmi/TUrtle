<script>
	import { getContext, onMount } from 'svelte';

	import { Calendar } from '@fullcalendar/core';
	import timeGridPlugin from '@fullcalendar/timegrid';
	import listPlugin from '@fullcalendar/list';
	import deLocale from '@fullcalendar/core/locales/de';



	let localeFunction = getContext('locale');

	let localeString = $derived(localeFunction());

	let calendar;
	onMount(() => {
		let calendarEl = document.getElementById('calendar');
		calendar = new Calendar(calendarEl, {
			plugins: [timeGridPlugin, listPlugin],
			locale: deLocale,
			aspectRatio: 2.1,
			events: '/dev/api/events',
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
	});

</script>

<div id="calendar"></div>
