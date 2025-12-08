<script>
	import { getContext, onMount } from 'svelte';
/* 	import { dev } from '$app/environment'; */
	import request from '$lib/api/api';
	import { convertEventToBackend, convertEventToFrontend, fetchRoomBookings } from '$lib/utils';
	import {Label, Input, Datepicker, Button, ThemeProvider} from 'flowbite-svelte';
	import {m} from '$lib/paraglide/messages.js';

	import {TrashBinSolid} from 'flowbite-svelte-icons';

	import { Calendar } from '@fullcalendar/core';

	import timeGridPlugin from '@fullcalendar/timegrid';
	import listPlugin from '@fullcalendar/list';
	import interactionPlugin from '@fullcalendar/interaction';

	let contextMenu;
	let calendar;
	let eventCard;

	let clientX;
	let clientY;

	function onPageClick(e) {
		if (e.clientX !== clientX && e.clientY + window.scrollY !== clientY && !eventCard.contains(e.target)) {
			selectedEvent = false;
		}
	};

	let localeFunction = getContext('locale');

	let localeString = $derived(localeFunction());
	let selectedEvent = $state();

	let startDate = $derived(selectedEvent.start);
	let endDate = $derived(selectedEvent.end);

	let eventTitle = $derived(selectedEvent.title);
	
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
	let newStartDate = $derived.by(() => {
		
		let tempStartDate = startDate;
		 tempStartDate.setHours(parseInt(startTime.substring(0,2)));
		 tempStartDate.setMinutes(parseInt(startTime.substring(3)));
		
		return tempStartDate;
	}); 
	
	let newEndDate = $derived.by(() => {
		
		let tempEndDate = endDate;
		 tempEndDate.setHours(parseInt(endTime.substring(0,2)));
		 tempEndDate.setMinutes(parseInt(endTime.substring(3)));
		
		return tempEndDate;
	}); 

	onMount(() => {
		let calendarEl = document.getElementById('calendar');
		calendar = new Calendar(calendarEl, {
			plugins: [timeGridPlugin, listPlugin, interactionPlugin],
			locale: 'de',
			aspectRatio: 2.1,
			editable: true,
			events: /* dev ? '/dev/api/events' : '/api/roombookings/page' */async function(info, successCallback, failureCallback) {
				const fetchedData = await fetchRoomBookings(info);
				if(fetchedData){
					const events = fetchedData.map(event => (convertEventToFrontend(event)));
					successCallback(events);
				} else {
					failureCallback("Error");
				}

			},
			eventDrop: function (eventDropInfo) {
				eventDropInfo.jsEvent.preventDefault();
				request(`/roombookings/${eventDropInfo.event.id}`, {
					method: "PATCH",
					body: JSON.stringify(convertEventToBackend(eventDropInfo.event)),
					headers: {'Content-Type': 'application/json'}
				});
			},
			eventResize: function (eventResizeInfo){
				eventResizeInfo.jsEvent.preventDefault();
				request(`/roombookings/${eventResizeInfo.event.id}`, {
					method: "PATCH",
					body: JSON.stringify(convertEventToBackend(eventResizeInfo.event)),
					headers: {'Content-Type': 'application/json'}
				});
			},
			eventClick: function (info) {
				info.jsEvent.preventDefault();
				selectedEvent = info.event;
				clientX = info.jsEvent.clientX;
				clientY = info.jsEvent.clientY + window.scrollY;
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

	function createEvent(e){
		e.stopPropagation();
		const date = new Date();
		const endDate = new Date();
		endDate.setHours(endDate.getHours()+1);
		selectedEvent = {
			title: "_New Event_",
			start: date,
			end:  endDate,
			new: true
		};
		/* calendar.addEvent(selectedEvent); */
		selectedEvent.new = true;

	}
	function removeEvent() {
		request(`/roombookings/${selectedEvent.id}`, {
			method: 'DELETE'
		});
		selectedEvent.remove();
		selectedEvent = false;
	};

	function updateDate(){
		if(selectedEvent.new){
			calendar.addEvent({
				title: eventTitle,
				start: newStartDate,
				end: newEndDate
			})
		} else {
			calendar.getEventById(selectedEvent.id).setProp('title', eventTitle);
			calendar.getEventById(selectedEvent.id).setStart(newStartDate);
			calendar.getEventById(selectedEvent.id).setEnd(newEndDate);
		}
		request( selectedEvent.new ? '/roombookings' : `/roombookings/${selectedEvent.id}`, {
			method: selectedEvent.new ? 'POST': 'PATCH',
			body : JSON.stringify( selectedEvent.new ? {title: eventTitle, startTime: newStartDate, endTime: newEndDate, creator: 1, description: ""} : convertEventToBackend(calendar.getEventById(selectedEvent.id))),
			headers: {'Content-Type': 'application/json'}
		});
	};
	
	const theme = {
		button: {
			base: "bg-csw hover:bg-orange-800"
		}
	};
	/* $effect(() => {
		if(selectedEvent && calendar) console.log("Test "+ "EventDate: "+JSON.stringify($state.snapshot(eventDate)));
		
			calendar.getEventById(selectedEvent.id).setEnd(eventDate);
	}); */
	
	$effect(() => {
		if(calendar){
			if (localeString === 'en') {
				calendar.setOption('locale', 'en-us');
			} else {
				calendar.setOption('locale', localeString);
			}
		}
	});
</script>
<div class="flex flex-col md:flex-row  gap-2">
	<div class="grow" id="calendar"></div>
		<div bind:this={eventCard} class="w-full bg-white border rounded-lg max-w-sm border-gray-200 dark:bg-gray-800 dark:border-gray-700 flex flex-col mt-17 p-5 gap-5">
				{#if selectedEvent}
				<div class="flex flex-row justify-between h-10">
					<input type="text" class="text-2xl w-9/10 h-full mr-auto rounded-lg focus:ring-2 focus:ring-csw border-hidden outline-hidden focus:outline-hidden" bind:value={eventTitle} /> 
					<button class="h-full w-10 inline-flex justify-end items-center" onclick={removeEvent}>
						<TrashBinSolid class="text-red-500 h-6/10 w-6/10 hover:text-red-700"></TrashBinSolid>
					</button>
				</div>
				<Label class="space-y-2"> <span>_Start_</span>
					<Datepicker  monthBtnSelected="bg-csw! hover:text-white!" bind:value={startDate}></Datepicker>
					<Input type="time" bind:value={startTime}/>
				</Label>
				<Label class="space-y-2"> <span>_End_</span>
					<Datepicker monthBtnSelected="bg-csw! hover:text-white!" bind:value={endDate}></Datepicker>
					<Input type="time" bind:value={endTime}/>
				</Label>
				<ThemeProvider {theme}>
					<Button onclick={updateDate}>{m.save()}</Button>
				</ThemeProvider>
				{:else}
				<div class="flex flex-col h-full justify-center items-center">
					<h1 class="text-2xl text-gray-500">{m.admin_bookings__select_event()}</h1>
					<span class="mb-2.5">{m.or()}</span>
					<Button onclick={createEvent}>{m.admin_bookings__create_new_event()}</Button>
				</div>
				{/if}
		</div>
</div>
<svelte:window on:click={onPageClick} />