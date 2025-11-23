<script>
	import { getContext } from 'svelte';
	import { dev } from '$app/environment';
	import request from '$lib/api/api';
	import ContextMenu from './ContextMenu.svelte';

	import { Calendar } from '@fullcalendar/core';

	import timeGridPlugin from '@fullcalendar/timegrid';
	import listPlugin from '@fullcalendar/list';
	import interactionPlugin from '@fullcalendar/interaction';

	let { data } = $props();
	let contextMenu;

	let localeFunction = getContext('locale');

	let localeString = $derived(localeFunction());

	$effect(() => {
		let calendarEl = document.getElementById('calendar');
		let calendar = new Calendar(calendarEl, {
			plugins: [timeGridPlugin, listPlugin, interactionPlugin],
			locale: 'de',
			aspectRatio: 2.1,
			editable: true,
			events: dev ? '/dev/api/events' : '/api/events',
			eventDrop: function (eventDropInfo) {
				eventDropInfo.jsEvent.preventDefault();
				request('/events', {
					method: "PATCH",
					body: JSON.stringify(eventDropInfo.event)
				})
			},
			eventResize: function (eventResizeInfo){
				eventResizeInfo.jsEvent.preventDefault();
				request('/events', {
					method: "PATCH",
					body: JSON.stringify(eventResizeInfo.event)
				})
			},
			eventClick: function (info) {
				info.jsEvent.preventDefault();

				contextMenu.rightClickContextMenu(info.jsEvent, info.event);
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
<ContextMenu bind:this={contextMenu} />
