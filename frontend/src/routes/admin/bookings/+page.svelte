<script>
	import { getContext, onMount } from 'svelte';
	import { dev } from '$app/environment';
	import { fade } from 'svelte/transition';
	import request from '$lib/api/api';
	import { convertEventToBackend, convertEventToFrontend, fetchRoomBookings } from '$lib/utils';
	import {Label, Input, Datepicker, Button, Textarea, Toggle, MultiSelect, Timepicker} from 'flowbite-svelte';
	import WhitelistDropdown from '$lib/components/WhitelistDropdown.svelte';
	import {m} from '$lib/paraglide/messages.js';

	import {TrashBinSolid} from 'flowbite-svelte-icons';

	import { Calendar } from '@fullcalendar/core';

	import timeGridPlugin from '@fullcalendar/timegrid';
	import listPlugin from '@fullcalendar/list';
	import interactionPlugin from '@fullcalendar/interaction';

	let { data } = $props();

	const users = $derived(data.users);
	const dropdownUsers = $derived(users.map(user =>  ({
		firstName: user.firstName,
		lastName: user.lastName,
		value: user.id
	})));

	let successRequest = $state(false);
	let calendar;
	let eventCard;

	let whitelistDisableOverride = $state(false);

	let clientX;
	let clientY;
	const participants = [
		{value: 0, name: "Jan"},
		{value: 1, name: "Memphis"},
		{value: 2, name: "Sanel"},
		{value: 3, name: "Tanh Lam"},
		{value: 4, name: "Lucas"}
	];

	function onPageClick(e) {
		if (e.clientX !== clientX && e.clientY + window.scrollY !== clientY && !eventCard.contains(e.target)) {
			selectedEvent = false;
		}
	};

	function setOpenToEveryone(){
		useWhitelist = false;
	}

	let localeFunction = getContext('locale');

	let localeString = $derived(localeFunction());
	let selectedEvent = $state();

	let startDate = $derived(selectedEvent.start);
	let endDate = $derived(selectedEvent.end);

	let eventTitle = $derived(selectedEvent.title);
	let eventDescription = $derived(selectedEvent.extendedProps.description);
	let eventWhitelist = $derived(selectedEvent.extendedProps.whitelist || []);
	let useWhitelist = $derived(selectedEvent.extendedProps.enableWhitelist);
	
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
			height: window.innerHeight - 80,
			width: window.innerWidth,
			editable: true,
			events: async function(info, successCallback, failureCallback) {
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
			weekends: false,
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
			new: true,
			extendedProps: {
				creator: 1,
				description: "",
				enableWhitelist: false,
				whitelist: []
			}
		};

	}
	function removeEvent() {
		const response = request(`/roombookings/${selectedEvent.id}`, {
			method: 'DELETE'
		});
		selectedEvent.remove();
		selectedEvent = false;
	};

	function createNewBackendEvent(){
		if(dev) {
			return {
				title: eventTitle, 
				start: newStartDate, 
				end: newEndDate,
				extendedProps: {
					creator: {
						id: 1
					},
					description: "", 
					enableWhitelist: useWhitelist, 
					whitelist: eventWhitelist
				}
			}
		}
		return {
			title: eventTitle, 
			start: newStartDate, 
			end: newEndDate, 
			creator: 1, 
			description: "", 
			enableWhitelist: useWhitelist, 
			whitelist: eventWhitelist
		}
	};
	async function saveEvent(){
		if(selectedEvent.new){
			calendar.addEvent({
				title: eventTitle,
				start: newStartDate,
				end: newEndDate,
				description: eventDescription,
				enableWhitelist: useWhitelist,
				whitelist: eventWhitelist
			});
		} else {
			calendar.getEventById(selectedEvent.id).setProp('title', eventTitle);
			calendar.getEventById(selectedEvent.id).setStart(newStartDate);
			calendar.getEventById(selectedEvent.id).setEnd(newEndDate);
			calendar.getEventById(selectedEvent.id).setExtendedProp('description', eventDescription);
			calendar.getEventById(selectedEvent.id).setExtendedProp('enableWhitelist', useWhitelist);
			calendar.getEventById(selectedEvent.id).setExtendedProp('whitelist', eventWhitelist);
			}
		const response = await request( selectedEvent.new ? '/roombookings' : `/roombookings/${selectedEvent.id}`, {
			method: selectedEvent.new ? 'POST': 'PATCH',
			body : JSON.stringify( selectedEvent.new ? createNewBackendEvent() : convertEventToBackend(calendar.getEventById(selectedEvent.id))),
			headers: {'Content-Type': 'application/json'}
		});

		successRequest = {
			ok: response.ok
		}

		setTimeout(() => successRequest = undefined, 1500);
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
<div class="flex flex-col xl:flex-row  gap-2">
	<div class="grow" id="calendar"></div>
		<div bind:this={eventCard} class=" bg-white border rounded-lg min-w-sm border-gray-200 dark:bg-gray-800 dark:border-gray-700 flex flex-row xl:flex-col mt-17 p-5 gap-5">
				{#if selectedEvent}
				<div class="flex flex-col sm:flex-row justify-between h-10">
					<input type="text" class="text-2xl w-9/10 h-full mr-auto rounded-lg focus:ring-2 focus:ring-csw border-hidden outline-hidden focus:outline-hidden" bind:value={eventTitle} /> 
					<button class="h-full w-10 inline-flex justify-end items-center" onclick={removeEvent}>
						<TrashBinSolid class="text-red-500 h-6/10 w-6/10 hover:text-red-700"></TrashBinSolid>
					</button>
				</div>
				<Label class="space-y-2"> <span>_Start_</span>
					<Datepicker monthBtnSelected="bg-csw! hover:text-white!" bind:value={startDate}></Datepicker>
					<Timepicker divClass="shadow-none!" bind:value={startTime}/>
				</Label>
				<Label class="space-y-2"> <span>_End_</span>
					<Datepicker monthBtnSelected="bg-csw! hover:text-white!" bind:value={endDate}></Datepicker>
					<Timepicker divClass="shadow-none!" bind:value={endTime}/>
				</Label>
				<Label for="description" class="mb-0">_Description_
					<Textarea id="description" placeholder="_Sample description_" rows={3} class="w-full" bind:value={eventDescription} />
				</Label>
				<Toggle size="small" bind:checked={whitelistDisableOverride} onchange={setOpenToEveryone}>_Open to everyone_</Toggle>
				<Toggle disabled={whitelistDisableOverride} size="small" bind:checked={useWhitelist}>_Use whitelist_</Toggle>
				{#if useWhitelist}
				<WhitelistDropdown users={dropdownUsers} bind:value={eventWhitelist} />
			
				{/if}
				<Button onclick={saveEvent}>{m.save()}</Button>
				{#if successRequest}
					<p out:fade class={(successRequest.ok ? "text-green-600" : "text-red-500")+" text-xs"}>
						{successRequest.ok ? "_Event successfully saved_": "An error uccured, please try again"}
					</p>
				{/if}
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