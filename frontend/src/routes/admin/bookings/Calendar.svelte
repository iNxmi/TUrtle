<script>
	import { getContext, onMount } from 'svelte';
	import { dev, browser } from '$app/environment';
	import request from '$lib/api/api';
	import ContextMenu from './ContextMenu.svelte';
	import {Card, Label, Input, Datepicker, Button, ThemeProvider} from 'flowbite-svelte';

	import { Calendar } from '@fullcalendar/core';

	import timeGridPlugin from '@fullcalendar/timegrid';
	import listPlugin from '@fullcalendar/list';
	import interactionPlugin from '@fullcalendar/interaction';


	let { data } = $props();
	let contextMenu;
	let calendar;
	let localeFunction = getContext('locale');

	let localeString = $derived(localeFunction());
	let selectedEvent = $state();

	let startDate = $derived(selectedEvent.start);
	let endDate = $derived(selectedEvent.end);

	
	
	let startTime = $derived.by(() => {
		const tempHour = selectedEvent.start.getHours() < 10  ? "0"+selectedEvent.start.getHours().toString() : selectedEvent.start.getHours().toString();
		const tempMinutes = selectedEvent.start.getMinutes() < 10 ? "0"+selectedEvent.start.getMinutes().toString() : selectedEvent.start.getMinutes().toString();
		return tempHour+":"+tempMinutes;
	});
	let endTime = $derived.by(() => {
		const tempHour = selectedEvent.end.getHours() < 10 ? "0"+selectedEvent.end.getHours().toString() : selectedEvent.end.getHours().toString();
		const tempMinutes = selectedEvent.end.getMinutes() < 10 ? "0"+selectedEvent.end.getMinutes().toString() : selectedEvent.end.getMinutes().toString();
		return tempHour+":"+tempMinutes;
	});
	
	let eventDate = $derived.by(() => {
		
		let tempEndDate = endDate;
		tempEndDate = endDate.setHours(parseInt(endTime.substring(0,2)));
		tempEndDate = endDate.setMinutes(parseInt(endTime.substring(3)));
		
		return tempEndDate;
	}); 

	
	onMount(() => {
		let calendarEl = document.getElementById('calendar');
		calendar = new Calendar(calendarEl, {
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
				selectedEvent = info.event;
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
		
	});

	function updateDate(){

		calendar.getEventById(selectedEvent.id).setEnd(eventDate);
		request('/events', {
			method: 'PATCH',
			body : JSON.stringify(calendar.getEventById(selectedEvent.id))
		});
	}
	
	const theme = {
		button: {
			base: "bg-csw"
		}
	};
	/* $effect(() => {
		if(selectedEvent && calendar) console.log("Test "+ "EventDate: "+JSON.stringify($state.snapshot(eventDate)));
		
			calendar.getEventById(selectedEvent.id).setEnd(eventDate);
	}); */
	
	if(calendar){
		if (() => localeString === 'en') {
			calendar.setOption('locale', 'en-us');
		} else {
			calendar.setOption('locale', () => localeString);
		}
}
</script>

	<div class="grow" id="calendar"> </div>
<ContextMenu bind:this={contextMenu} />