<script>
	import { getContext, onMount } from 'svelte';
	import { dev } from '$app/environment';
	import request from '$lib/api/api';
	import {Label, Input, Datepicker, Button, ThemeProvider} from 'flowbite-svelte';

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
			events: dev ? '/dev/api/events' : '/api/events',
			eventDrop: function (eventDropInfo) {
				eventDropInfo.jsEvent.preventDefault();
				request('/events', {
					method: "PATCH",
					body: JSON.stringify(eventDropInfo.event)
				});
			},
			eventResize: function (eventResizeInfo){
				eventResizeInfo.jsEvent.preventDefault();
				request('/events', {
					method: "PATCH",
					body: JSON.stringify(eventResizeInfo.event)
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
			end:  endDate
		};
		calendar.addEvent(selectedEvent);

	}
	function removeEvent() {
		request('/events', {
			method: 'DELETE',
			body: selectedEvent.id
		});
		selectedEvent.remove();
		selectedEvent = false;
	};

	function updateDate(){
		calendar.getEventById(selectedEvent.id).setProp('title', eventTitle);
		calendar.getEventById(selectedEvent.id).setStart(newStartDate);
		calendar.getEventById(selectedEvent.id).setEnd(newEndDate);
		request('/events', {
			method: 'PATCH',
			body : JSON.stringify(calendar.getEventById(selectedEvent.id))
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
					<input type="test" class="text-2xl h-full mr-auto rounded-lg focus:ring-2 focus:ring-csw focus:outline-hidden" bind:value={eventTitle} /> 
					<button class="h-full w-10 inline-flex justify-end items-center" onclick={removeEvent}>
						<TrashBinSolid class="text-red-500 h-6/10 w-6/10 hover:text-red-700"></TrashBinSolid>
					</button>
				</div>
				<Label class="space-y-2"> <span>_Start_</span>
					<Datepicker inputClass="focus:ring-csw! focus:border-csw!" monthBtn="hover:text-csw!" monthBtnSelected="bg-csw! hover:text-white!" classes={{
						dayButton:"aria-selected:bg-csw! aria-selected:text-white! hover:text-csw!"
					}} bind:value={startDate}></Datepicker>
					<Input class="focus:ring-csw! focus:border-csw!" type="time" bind:value={startTime}/>
				</Label>
				<Label class="space-y-2"> <span>_End_</span>
					<Datepicker inputClass="focus:ring-csw! focus:border-csw!" classes={{dayButton:"aria-selected:bg-csw! aria-selected:hover:bg-csw!"}} bind:value={endDate}></Datepicker>
					<Input class="focus:ring-csw! focus:border-csw!" type="time" bind:value={endTime}/>
				</Label>
				<ThemeProvider {theme}>
					<Button onclick={updateDate}>_Speichern_</Button>
				</ThemeProvider>
				{:else}
				<div class="flex flex-col h-full justify-center items-center">
					<h1 class="text-2xl text-gray-500">_Select Event_</h1>
					<span class="mb-2.5">_or_</span>
					<Button class="bg-csw!" onclick={createEvent}>_Create new Event_</Button>
				</div>
				{/if}
		</div>
</div>
<svelte:window on:click={onPageClick} />