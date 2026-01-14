<script>

    import { Heading, Card, Select, Checkbox, Datepicker, Label, Timepicker, Button, Modal } from "flowbite-svelte";
    import {onMount, getContext} from 'svelte';
    import { fade } from 'svelte/transition';
    import { Calendar } from '@fullcalendar/core';
    import timeGridPlugin from '@fullcalendar/timegrid';
	import listPlugin from '@fullcalendar/list';
	import deLocale from '@fullcalendar/core/locales/de';
    import request from '$lib/api/api';
    import LockerOpenModal from "$lib/components/LockerOpenModal.svelte";
    import { fetchDeviceBookings, convertEventToFrontend } from "$lib/utils";
    let { data } = $props();
    
    let reservations = $derived(data.reservations);
    let deviceCategories = $derived(data.deviceCategories);
    let devices = $derived(data.devices);
    let calendar = $state();
    let localeFunction = getContext('locale');
    
    let filteredDevices = $derived(devices.filter((device) => device.category === selectedCategory));
    let formattedDevices = $derived(filteredDevices.map((device) =>  ({name: device.name, value: device.id, description: device.description})));

    let formattedDeviceCategories = $derived(deviceCategories.map((category) => ({name: category.name, value: category.id})));
	
    let localeString = $derived(localeFunction());

    let showCreateNewReservationModal = $state(false);

    let showLockerOpenModal = $state(false);

    $effect(() => {

		if(calendar){
			calendar.setOption('locale', localeString);
		}
	});

    function initializeCalendar(){
		let calendarEl = document.getElementById('calendar');
		calendar = new Calendar(calendarEl, {
			plugins: [timeGridPlugin, listPlugin],
			locale: deLocale,
			/* aspectRatio: 2.1, */
			height: window.innerHeight - 80,
			events:async function(info, successCallback, failureCallback) {
				const fetchedData = await fetchDeviceBookings(info);

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
    }
    
    function openNewBookingModal(){
        showCreateNewReservationModal = true;
        
        setTimeout(() => initializeCalendar());
       /*  initializeCalendar(); */
    }

    let selectedCategory = $state();
   /*  let selectedType = $state(false); */
    let selectedDevice = $state();
    let bookingSuccess = $state();
    let lockerOpen = $state();

    let instantBooking = $state(true); //Instant Booking
    
    let bookingStartTime = $state("");
    let bookingEndTime = $state("");
    let bookingRange = $state({
        from: new Date(Date.now()),
        to: new Date(Date.now())
  });
    function resetForm(){
        selectedDevice = "";
        selectedCategory = "";
        bookingSuccess = false;
        instantBooking = true;
    }
  /* let newBooking = $state(); */

  function createNewBooking(){
    const actualSelectedDevice = filteredDevices.find((device) => device.id === selectedDevice);
    const newBooking = {
            deviceName: actualSelectedDevice.name,
            start: instantBooking ? new Date(Date.now()) : bookingRange.from,
            end: bookingRange.to,
            locker: actualSelectedDevice.locker
        }
        if(!instantBooking){
            newBooking.start.setHours(parseInt(bookingStartTime.substring(0,2)));
            newBooking.start.setMinutes(parseInt(bookingStartTime.substring(3)));
        }
        newBooking.end.setHours(parseInt(bookingEndTime.substring(0,2)));
        newBooking.end.setMinutes(parseInt(bookingEndTime.substring(3)));

    /* calendar.addEvent(newBooking);

    const response = request('/TODO', {
        method: 'POST',
        headers: {'Content-Type' : 'application/json'},
        body: JSON.stringify(newBooking)
    });
 */
    bookingSuccess = true;  //Test only
           /* if(response.ok){
        bookingSuccess = true;
            } */
           
           if(!instantBooking){
               showCreateNewReservationModal = false;
               resetForm();
    }

           reservations = [...reservations, newBooking];
  }

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
/* function getDevices(){ */
       /*  switch(selectedType){
            case "acerLaptop": return testLaptopDevicesAcer;
            case "asusLaptop": return testLaptopDevicesAsus;
            case "samsungTablet": return testTabletDevicesSamsung;
            case "iPad": return testTabletDevicesApple;
        } */
      /*  return formattedDevices; */
/* }; */

function openLocker(){
    showCreateNewReservationModal = false;
    showLockerOpenModal = true;
               resetForm();
    //TODO 
}

 $effect(() => {
    if(calendar){
        if (localeString === 'en') {
            calendar.setOption('locale', 'en-us');
        } else {
            calendar.setOption('locale', localeString);
        }
    }
});

 $effect(() => {
    if(calendar){
    if(selectedDevice){
        document.getElementById('calendar').hidden = false;
        calendar.render();
    } else {
         document.getElementById('calendar').hidden = true;
    }
    }
 });

let dt = $derived(Intl.DateTimeFormat(localeString, {dateStyle: "medium",timeStyle: "medium"}));
</script>
<Heading class="text-center mb-5" tag="h2">_Device Booking_</Heading>
<div class="flex justify-end pb-5">
    <Button onclick={openNewBookingModal}>_Create new Reservation_</Button>
</div>
<div class="flex flex-col gap-4">
    {#each reservations as reservation, i}
         <div class=" h-20 bg-gray-50 dark:bg-gray-700 border rounded-lg border-gray-100 dark:border-gray-800 shadow grid grid-flow-row grid-rows-1 grid-cols-4">
            <div class="place-self-center"><span class="font-bold text-lg dark:text-white">{reservation.deviceName}</span></div>
            <div class="place-self-center"><span class="font-bold text-lg dark:text-white">{dt.format(reservation.start)}</span></div>
            <div class="place-self-center"><span class="font-bold text-lg dark:text-white">{dt.format(reservation.end)}</span></div>
            <div class="place-self-center"><span class="font-bold text-lg dark:text-white">{reservation.locker}</span></div> 
        </div>
    {:else}
        <div class="h-100 flex justify-center items-center"><span class="text-center font-bold text-3xl text-muted">_No reservations made_</span></div>
    {/each}
            
    <Modal classes={{body:"mr-10"}} size="xl" bind:open={showCreateNewReservationModal}>
        <div class="flex flex-row gap-2 h-[calc(100svh-79px)]">
            <div class="flex flex-col">
                <Card shadow={selectedCategory ? false : "md"} class="m-4 p-4"> 
                    <Heading tag="h4" class="text-center mb-2">_Choose the device category_</Heading>
                    <Select bind:value={selectedCategory} items={formattedDeviceCategories} onchange={() => selectedDevice= ""}></Select>
                </Card>
                {#if selectedCategory}
                <!--  <Card class="m-4 p-4">  -->
                    <!--  <Heading tag="h4" class="text-center mb-2">_Choose the device type_</Heading> -->
                        <!-- <Select bind:value={selectedType} items={getDeviceType()}></Select> -->
                <!--  </Card> -->
                    <!-- {#if selectedType} -->
                        <Card shadow={selectedDevice ? false : "md"} class="m-4 p-4"> 
                            <Heading tag="h4" class="text-center mb-2">_Choose the device_</Heading>
                            <Select se bind:value={selectedDevice} items={formattedDevices}></Select>
                        </Card>
                        {#if selectedDevice && !bookingSuccess}
                            <Card class="m-4 p-4"> 
                                <Heading tag="h4" class="text-center mb-2">_Book Device_</Heading>
                                <Checkbox class="mb-2" bind:checked={instantBooking}>_Instant Booking_</Checkbox>
                                {#if instantBooking}
                                <Datepicker bind:value={bookingRange.to} />
                                <div class="flex flex-row w-full">
                                    <Label>
                                        _End Time_
                                        <Timepicker bind:value={bookingEndTime} divClass="shadow-none!" />
                                    </Label>
                                </div>
                                {:else if !instantBooking && ! bookingSuccess}
                                    <Label class= "text-gray-700 mb-2">
                                        _Selected Range_
                                        <Datepicker disabled={instantBooking} range bind:rangeFrom={bookingRange.from} bind:rangeTo={bookingRange.to} />  
                                    </Label>
                                    <div class="flex flex-row">
                                        <Label class="text-gray-700">
                                            _Start Time_
                                            <Timepicker bind:value={bookingStartTime} divClass="shadow-none!" disabled={instantBooking} />  
                                        </Label>
                                        <Label class="text-gray-700">
                                            _End Time_
                                            <Timepicker bind:value={bookingEndTime} divClass="shadow-none!" disabled={instantBooking} />  
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
            <!--  {/if} -->
            </div>
            <div class="grow" id="calendar" hidden={true}></div>
        </div>
    </Modal>
</div>
<LockerOpenModal lockerOpen={showLockerOpenModal} lockerNumber={5} />