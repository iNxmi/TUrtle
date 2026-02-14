<script>
	import { getContext, onMount } from 'svelte';
	import { dev } from '$app/environment';
	import { fade } from 'svelte/transition';
	import request from '$lib/api/api';
	import { convertEventToBackend, convertEventToFrontend, fetchRoomBookings, between } from '$lib/utils';
	import {Label, Input, Datepicker, Button, Textarea, Toggle, MultiSelect, Timepicker,Heading, P, Tabs, TabItem} from 'flowbite-svelte';
	import WhitelistDropdown from '$lib/components/WhitelistDropdown.svelte';
	import TableView from '$lib/components/TableView.svelte';
	import {m} from '$lib/paraglide/messages.js';

	import {TrashBinSolid} from 'flowbite-svelte-icons';

	import { Calendar } from '@fullcalendar/core';

	import timeGridPlugin from '@fullcalendar/timegrid';
	import listPlugin from '@fullcalendar/list';
	import interactionPlugin from '@fullcalendar/interaction';
	import {roomBookingsPath} from '$lib/backend';
	import { page } from '$app/state';

	let { data } = $props();

	let creator = $derived(selectedEvent ? [selectedEvent.extendedProps.creator]: data.user.id);
	let currentUser = $derived(data.user);
	let users = $derived(data.page[1]);
	let dropdownUsers = $derived(users.map((user) =>  ({
		firstName: user.firstName,
		lastName: user.lastName,
		selected: selectedEvent.extendedProps.whitelistedUserIds.includes(user.id),
		value: user.id
	})));

	let successRequest = $state(false);
	let calendar = $state();
	let eventCard;

	let whitelistDisableOverride = $derived(selectedEvent.extendedProps.openToEveryone);

	let currentTab = $state(page.url.searchParams.get("tab" )|| '/room-bookings');
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

	let status = $derived(selectedEvent.extendedProps.status);
	
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

	let calendarEl = $state();
	$effect(() => {
		if(currentTab === '/room-bookings'){
	/* function initCalendar(){ */
		 calendarEl = document.getElementById('calendar');
		calendar = new Calendar(calendarEl, {
			plugins: [timeGridPlugin, listPlugin, interactionPlugin],
			locale: 'de',
			height: window.innerHeight - 135,
			editable: true,
			events: async function(info, successCallback, failureCallback) {
				const fetchedData = await fetchRoomBookings(info);
				if(fetchedData){
					const events = fetchedData.map(event => (convertEventToFrontend(event, currentUser)));
					successCallback(events);
				} else {
					failureCallback("Error");
				}

			},
			eventDrop: function (eventDropInfo) {
				eventDropInfo.jsEvent.preventDefault();
				request(roomBookingsPath+`/${eventDropInfo.event.id}`, {
					method: "PATCH",
					body: JSON.stringify(convertEventToBackend(eventDropInfo.event)),
					headers: {'Content-Type': 'application/json'}
				});
			},
			eventResize: function (eventResizeInfo){
				eventResizeInfo.jsEvent.preventDefault();
				request(roomBookingsPath+`/${eventResizeInfo.event.id}`, {
					method: "PATCH",
					body: JSON.stringify(convertEventToBackend(eventResizeInfo.event)),
					headers: {'Content-Type': 'application/json'}
				});
			},
			eventClick: function (info) {
				info.jsEvent.preventDefault();
				if(info.event.extendedProps.isAuthor || user.roleIds.includes(3)){
					selectedEvent = info.event;
					clientX = info.jsEvent.clientX;
					clientY = info.jsEvent.clientY + window.scrollY;
				}
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
	}
		/* calendar.render();
		calendar.getEvents().forEach((event) => event.backgroundColor(event.extendedProps.status === 'REQUESTED' ? '#FFF500': event.extendedProps.status === 'REJECTED' ?  '#CC0000': '#FF8C1E')); */
	}
);

$effect( () => {
	if(calendar && currentTab === '/room-bookings'){
	 calendar.render();

	 setTimeout(() => {
		calendar.getEvents().forEach((event) => event.setProp('color', event.extendedProps.status === 'REQUESTED' ? '#7600ff': event.extendedProps.status === 'REJECTED' ?  'red': 'oklch(75% 0.183 55.934)'));
	}, 300);
	
	}
})

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
				creator: currentUser.id,
				description: "",
				enableWhitelist: false,
				openToEveryone: false,
				whitelistedUserIds: []
			}
		};

	}
	function removeEvent() {
		const response = request(roomBookingsPath+`/${selectedEvent.id}`, {
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
						firstName: "Admin",
						lastName: "",
						id: 1
					},
					description: "", 
					enableWhitelist: useWhitelist, 
					whitelistedUserIds: eventWhitelist,
					openToEveryone: whitelistDisableOverride
				}
			}
		}
		return {
			title: eventTitle, 
			start: newStartDate, 
			end: newEndDate, 
			creator: 129, 
			description: eventDescription, 
			accessibility: useWhitelist ? "WHITELIST" : whitelistDisableOverride ? "UNLOCKED" : "LOCKED",
			whitelistedUserIds: eventWhitelist
		}
	};

	const confirm = getContext('confirm');

	async function approveEvent(){
			
		calendar.getEventById(selectedEvent.id).setExtendedProp('status', 'APPROVED');
		calendar.getEventById(selectedEvent.id).setProp('color', 'oklch(75% 0.183 55.934)');
		
		const response = await request(roomBookingsPath+`/${selectedEvent.id}`, {
			method: 'PATCH',
			body : JSON.stringify(convertEventToBackend({status: 'APPROVED'})),
			headers: {'Content-Type': 'application/json'}
		});

		successRequest = {
			ok: response.ok
		}

		setTimeout(() => successRequest = undefined, 1500);

	};

	async function denyEvent(){
		calendar.getEventById(selectedEvent.id).setExtendedProp('status', 'REJECTED');
		calendar.getEventById(selectedEvent.id).setProp('color', 'red');
		
		const response = await request(roomBookingsPath+`/${selectedEvent.id}`, {
			method: 'PATCH',
			body : JSON.stringify(convertEventToBackend({status: 'REJECTED'})),
			headers: {'Content-Type': 'application/json'}
		});

		successRequest = {
			ok: response.ok
		}

		setTimeout(() => successRequest = undefined, 1500);

	};

	async function saveEvent(){
		if(between(newStartDate.getHours(), 6, 18) || between(newEndDate.getHours(), 6, 18)){            //TODO: Fix times
			const confirmed = await confirm('_The event is scheduled inside business hours. It has to be confirmed by CSW officials first. Do you want to continue?_');
			if(!confirmed){
				return;
			}
		}
		if(selectedEvent.new){
			calendar.addEvent({
				title: eventTitle,
				start: newStartDate,
				end: newEndDate,
				description: eventDescription,
				enableWhitelist: useWhitelist,
				whitelistedUserIds: eventWhitelist,
				openToEveryone: whitelistDisableOverride
			});
		} else {
			calendar.getEventById(selectedEvent.id).setProp('title', eventTitle);
			calendar.getEventById(selectedEvent.id).setStart(newStartDate);
			calendar.getEventById(selectedEvent.id).setEnd(newEndDate);
			calendar.getEventById(selectedEvent.id).setExtendedProp('description', eventDescription);
			calendar.getEventById(selectedEvent.id).setExtendedProp('enableWhitelist', useWhitelist);
			calendar.getEventById(selectedEvent.id).setExtendedProp('whitelistedUserIds', eventWhitelist);
			calendar.getEventById(selectedEvent.id).setExtendedProp('openToEveryone', whitelistDisableOverride);
			}
		const response = await request( selectedEvent.new ? roomBookingsPath : roomBookingsPath+`/${selectedEvent.id}`, {
			method: selectedEvent.new ? 'POST': 'PATCH',
			body : JSON.stringify( selectedEvent.new ? createNewBackendEvent() : convertEventToBackend(calendar.getEventById(selectedEvent.id))),
			headers: {'Content-Type': 'application/json'}
		});

		successRequest = {
			ok: response.ok
		}

		setTimeout(() => successRequest = undefined, 1500);
	};

	function sortFunction(x,y, selectUsers) {
        if(selectUsers.includes(x) && !selectUsers.includes(y)) return -1;
        if(selectUsers.includes(y) && !selectUsers.includes(x)) return 1;

        if(x.lastName && y.lastName){
            if(x.lastName > y.lastName) return 1;
            if(x.lastName < y.lastName) return -1;
        }
    }
	function displayFunction(user){
		return user.firstName+", "+user.lastName;
	}
	function filterFunction(user, searchTerm){
		return !searchTerm || 
		user.firstName.toLowerCase().includes(searchTerm.toLowerCase()) || 
		user.lastName.toLowerCase().includes(searchTerm.toLowerCase())
	}
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
	const itembookingsHeaders = [
        {id: "id", display: "_ID_"},
        {id: "start", display: "_Start Date_"},
        {id: "end", display: "_End Date_"},
        {id: "itemId", display: "_Device_"},
        {id: "userId", display:"_User Name_"},
        {id: "status", display: "_Status_"},
        {id: "createdAt", display: "_created at_"}
    ]
</script>
<Tabs tabStyle="underline">
	<TabItem onclick={() => setTimeout(() => initCalendar(), 10)} title="_Room Bookings_" open={currentTab === '/room-bookings'}>
		<div class="flex flex-col xl:flex-row  gap-2">
			<div bind:this={calendarEl} class="grow" id="calendar"></div>
				<div bind:this={eventCard} class=" bg-white border rounded-lg w-sm border-gray-200 dark:bg-gray-800 dark:border-gray-700 flex flex-row xl:flex-col mt-17 p-5 gap-5">
						{#if selectedEvent}
						<div class="flex flex-col sm:flex-row justify-between h-10">
							<input type="text" class="text-2xl w-9/10 h-full mr-auto dark:bg-gray-800 dark:text-white rounded-lg focus:ring-2
							 focus:ring-csw border-hidden outline-hidden focus:outline-hidden" bind:value={eventTitle} disabled={creator[0] !== currentUser.id}/> 
							<button class="h-full w-10 inline-flex justify-end items-center cursor-pointer" onclick={removeEvent}>
								<TrashBinSolid class="text-red-500 h-6/10 w-6/10 hover:text-red-700"></TrashBinSolid>
							</button>
						</div>
						<Label>_Creator_
							<WhitelistDropdown disabled={creator[0] !== currentUser.id} users={dropdownUsers} bind:value={creator} single {displayFunction} {sortFunction} {filterFunction}/>
						</Label>
						<Label class="space-y-2"> <span>_Start_</span>
							<Datepicker disabled={creator[0] !== currentUser.id} monthBtnSelected="bg-csw! hover:text-white!" bind:value={startDate}></Datepicker>
							<Timepicker disabled={creator[0] !== currentUser.id} divClass="shadow-none!" bind:value={startTime}/>
						</Label>
						<Label class="space-y-2"> <span>_End_</span>
							<Datepicker disabled={creator[0] !== currentUser.id} monthBtnSelected="bg-csw! hover:text-white!" bind:value={endDate}></Datepicker>
							<Timepicker disabled={creator[0] !== currentUser.id} divClass="shadow-none!" bind:value={endTime}/>
						</Label>
						<Label for="description" class="mb-0">_Description_
							<Textarea disabled={creator[0] !== currentUser.id} id="description" placeholder="_Sample description_" rows={3} class="w-full" bind:value={eventDescription} />
						</Label>
						<Toggle disabled={creator[0] !== currentUser.id} size="small" bind:checked={whitelistDisableOverride} onchange={setOpenToEveryone}>_Open to everyone_</Toggle>
						<Toggle disabled={whitelistDisableOverride || creator[0] !== currentUser.id} size="small" bind:checked={useWhitelist}>_Use whitelist_</Toggle>
						{#if useWhitelist}
						<WhitelistDropdown disabled={creator[0] !== currentUser.id} users={dropdownUsers} bind:value={eventWhitelist} {sortFunction} {displayFunction} {filterFunction}/>
					
						{/if}
						{#if selectedEvent.extendedProps.status === 'REQUESTED'}
							<Button color='green' onclick={approveEvent}>_Approve</Button>
							<Button color='red' onclick={denyEvent}>_Deny</Button>
						{:else}
							<Button class="cursor-pointer" onclick={saveEvent} disabled={creator[0] !== currentUser.id}>{m.save()} </Button>
						{/if}
						{#if successRequest}
							<p out:fade class={(successRequest.ok ? "text-green-600" : "text-red-500")+" text-xs"}>
								{successRequest.ok ? "_Event successfully saved_": "An error uccured, please try again"}
							</p>
						{/if}
						{:else}
						<div class="flex flex-col h-full justify-center items-center">
							<Heading tag="h4" class="text-gray-500! dark:text-gray-400!">{m.admin_bookings__select_event()}</Heading>
							<P class="text-gray-400 dark:text-gray-500! mb-2.5">{m.or()}</P>
							<Button onclick={createEvent}>{m.admin_bookings__create_new_event()}</Button>
						</div>
						{/if}
				</div>
		</div>
	</TabItem>
	<TabItem title='_Item Booking_'>
		<TableView headers={itembookingsHeaders} contentPage={data.page[0]} endpoint="/admin/item-bookings"/>
	</TabItem>
</Tabs>
<svelte:window on:click={onPageClick} />