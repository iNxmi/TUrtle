<script>

    import { Heading, Card, Select, Checkbox, Datepicker, Label, Timepicker, Button } from "flowbite-svelte";
    import {onMount, getContext} from 'svelte';
    import { fade } from 'svelte/transition';
    import { Calendar } from '@fullcalendar/core';
    import timeGridPlugin from '@fullcalendar/timegrid';
	import listPlugin from '@fullcalendar/list';
	import deLocale from '@fullcalendar/core/locales/de';
    import request from '$lib/api/api';
    import LockerOpenModal from "$lib/components/LockerOpenModal.svelte";
    let { data } = $props();

    let calendar = $state();
    let localeFunction = getContext('locale');

	let localeString = $derived(localeFunction());

    let showLockerOpenModal = $state(false);

    $effect(() => {

		if(calendar){
			calendar.setOption('locale', localeString);
		}
	});
	onMount(() => {
		let calendarEl = document.getElementById('calendar');
		calendar = new Calendar(calendarEl, {
			plugins: [timeGridPlugin, listPlugin],
			locale: deLocale,
			/* aspectRatio: 2.1, */
			height: window.innerHeight - 80,
			width: window.innerWidth,
			events:async function(info, successCallback, failureCallback) {
				const fetchedData = await fetchRoomBookings(info);

				if(fetchedData){
					const events = fetchedData.map(event => (convertEventToFrontend(event)));
					successCallback(events);
				} else {
					failureCallback("Error");
				}	
			},
			eventClick: async function (info){
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

		if (localeString === 'en') {
			calendar.setOption('locale', 'en-us');
		} else {
			calendar.setOption('locale', localeString);
		}
	});

    let selectedCategory = $state(false);
    let selectedType = $state(false);
    let selectedDevice = $state(false);
    let bookingSuccess = $state(false);
    let lockerOpen = $state(false);

    let bookingType = $state(true); //Instant Booking
    
    let bookingRange = $state({
    from: undefined,
    to: undefined
  });

  /* let newBooking = $state(); */

  function createNewBooking(){
    const newBooking = {
        start: bookingRange.from,
        end : bookingRange.to,
    }

    calendar.addEvent(newBooking);

    const response = request('/TODO', {
        method: 'POST',
        headers: {'Content-Type' : 'application/json'},
        body: JSON.stringify(newBooking)
    });

    bookingSuccess = true;  //Test only
    if(response.ok){
        bookingSuccess = true;
    }

  }


    let testDeviceCategories = [{
        value: "laptop", name:"Laptop"
    },
    {
        value: "tablet", name:"Tablet"
    }];  
    let testLaptopTypes = [{
        value: "acerLaptop", name:"Acer Laptop"
    },
    {
        value: "asusLaptop", name:"Asus Laptop"
    }];
    let testLaptopDevicesAcer = [{
        value: "acerNitro", name:"Acer Nitro"
    },
    {
        value: "acerPredator", name:"Acer Predator"
    }];
    let testLaptopDevicesAsus = [{
        value: "asusVivobook", name:"Asus Vivobook"
    },
    {
        value: "asusZephyrus", name:"Asus ROG Zephyrus"
    }];

    let testTabletTypes = [{
        value: "samsungTablet", name:"Samsung Tablet"
    },
    {
        value: "iPad", name:"Apple iPad"
    }];  
    let testTabletDevicesSamsung = [{
        value: "samsungS10", name:"Samsung Tab S10"
    },
    {
        value: "samsungA10", name:"Samsung Tab A 10"
    }];
    let testTabletDevicesApple = [{
        value: "iPad", name:"iPad"
    },
    {
        value: "iPadPro", name:"IPad Pro"
    }];

    function getDeviceType(){ 
        if(selectedCategory){
           
            switch(selectedCategory){
                case "laptop": return testLaptopTypes;
                case "tablet": return testTabletTypes;
            }
        }
        return [{value:"error", name:"Fehler"}];
    }
;
function getDevices(){
    if(selectedType){
        switch(selectedType){
            case "acerLaptop": return testLaptopDevicesAcer;
            case "asusLaptop": return testLaptopDevicesAsus;
            case "samsungTablet": return testTabletDevicesSamsung;
            case "iPad": return testTabletDevicesApple;
        }
    }
};

function openLocker(){
    showLockerOpenModal = true;
    //TODO 
}

 $effect(() => {
    if(selectedDevice){
        document.getElementById('calendar').hidden = false;
        calendar.render();
    } else {
         document.getElementById('calendar').hidden = true;
    }
   
 });

</script>

<Heading class="text-center" tag="h2">_Device Booking_</Heading>
<div class="flex flex-row gap-10">
    <div class="flex flex-col ml-10">
        <Card class="m-4 p-4"> 
            <Heading tag="h4" class="text-center mb-2">_Choose the device category_</Heading>
            <Select bind:value={selectedCategory} items={testDeviceCategories}></Select>
        </Card>
        {#if selectedCategory}
            <Card class="m-4 p-4"> 
                <Heading tag="h4" class="text-center mb-2">_Choose the device type_</Heading>
                <Select bind:value={selectedType} items={getDeviceType()}></Select>
            </Card>
            {#if selectedType}
                <Card class="m-4 p-4"> 
                    <Heading tag="h4" class="text-center mb-2">_Choose the device_</Heading>
                    <Select  bind:value={selectedDevice} items={getDevices()}></Select>
                </Card>
                {#if selectedDevice && !bookingSuccess}
                    <Card class="m-4 p-4"> 
                        <Heading tag="h4" class="text-center mb-2">_Book Device_</Heading>
                        <Checkbox class="mb-2" bind:checked={bookingType}>_Instant Booking_</Checkbox>
                        {#if bookingType}
                        <div class="flex flex-row w-full">
                            <Label>
                                _End Time_
                                <Timepicker divClass="shadow-none!" />
                            </Label>
                        </div>
                        {:else if !bookingType && ! bookingSuccess}
                            <Label class={bookingType ? "text-gray-300 mb-2": "text-gray-700 mb-2"}>
                                _Selected Range_
                                <Datepicker disabled={bookingType} range bind:rangeFrom={bookingRange.from} bind:rangeTo={bookingRange.to} />  
                            </Label>
                            <div class="flex flex-row">
                                <Label class={bookingType ? "text-gray-300": "text-gray-700"}>
                                    _Start Time_
                                    <Timepicker divClass="shadow-none!" disabled={bookingType} />  
                                </Label>
                                <Label class={bookingType ? "text-gray-300": "text-gray-700"}>
                                    _End Time_
                                    <Timepicker divClass="shadow-none!" disabled={bookingType} />  
                                </Label>
                            </div>
                        {/if}
                        <Button class="mt-2" onclick={createNewBooking}>_Confirm Reservation_</Button>
                    </Card>
                {:else if bookingSuccess}
                    <div in:fade>
                        <Card class="m-4 p-4">
                            <Button onclick={openLocker}>_Open Locker_</Button>
                        </Card>
                    </div>
                {/if}
            {/if}
        {/if}
    </div>
        <div class="grow" id="calendar" hidden={true}></div>
</div>
<LockerOpenModal lockerOpen={showLockerOpenModal} lockerNumber={5} />