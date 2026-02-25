<script>
    import {getContext, onMount} from 'svelte';
    import {dev} from '$app/environment';
    import {fade} from 'svelte/transition';
    import request from '$lib/api/api.js';
    import {between, convertEventToBackend, convertEventToFrontend, fetchRoomBookings} from '$lib/utils.js';
    import {Button, Datepicker, Heading, P, Textarea, Timepicker, Toggle} from 'flowbite-svelte';
    import WhitelistDropdown from '$lib/components/WhitelistDropdown.svelte';
    import {TrashBinSolid} from 'flowbite-svelte-icons';

    import {Calendar} from '@fullcalendar/core';

    import timeGridPlugin from '@fullcalendar/timegrid';
    import listPlugin from '@fullcalendar/list';
    import interactionPlugin from '@fullcalendar/interaction';
    import {page} from '$app/state';

    let {data} = $props();

    let creator = $derived(selectedEvent ? [selectedEvent.extendedProps.creator] : data.user.id);
    let currentUser = $derived(data.user);
    let users = $derived(data.users);
    let dropdownUsers = $derived(users.map((user) => ({
        firstName: user.firstName,
        lastName: user.lastName,
        selected: selectedEvent.extendedProps.whitelistedUserIds.includes(user.id),
        value: user.id
    })));

    let successRequest = $state(false);
    let calendar = $state(undefined);
    let eventCard;

    let whitelistDisableOverride = $derived(selectedEvent.extendedProps.openToEveryone);

    let currentTab = $state(page.url.searchParams.get("tab") || '/room-bookings');
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
    }

    function setOpenToEveryone() {
        useWhitelist = false;
    }

    /* let localeFunction =  */

    let localeString = getContext('locale');/* $derived(localeFunction()) */;
    let selectedEvent = $state(null);

    let startDate = $derived(selectedEvent.start);
    let endDate = $derived(selectedEvent.end);

    let eventTitle = $derived(selectedEvent.title);
    let eventDescription = $derived(selectedEvent.extendedProps.description);
    let eventWhitelist = $derived(selectedEvent.extendedProps.whitelist || []);
    let useWhitelist = $derived(selectedEvent.extendedProps.enableWhitelist);

    let status = $derived(selectedEvent.extendedProps.status);

    let startTime = $derived.by(() => {
        const tempHour = selectedEvent.start.getHours() < 10 ? "0" + selectedEvent.start.getHours().toString() : selectedEvent.start.getHours().toString();
        const tempMinutes = selectedEvent.start.getMinutes() < 10 ? "0" + selectedEvent.start.getMinutes().toString() : selectedEvent.start.getMinutes().toString();
        return tempHour + ":" + tempMinutes;
    });
    let endTime = $derived.by(() => {
        const tempHour = selectedEvent.end.getHours() < 10 ? "0" + selectedEvent.end.getHours().toString() : selectedEvent.end.getHours().toString();
        const tempMinutes = selectedEvent.end.getMinutes() < 10 ? "0" + selectedEvent.end.getMinutes().toString() : selectedEvent.end.getMinutes().toString();
        return tempHour + ":" + tempMinutes;
    });
    let newStartDate = $derived.by(() => {

        let tempStartDate = startDate;
        tempStartDate.setHours(parseInt(startTime.substring(0, 2)));
        tempStartDate.setMinutes(parseInt(startTime.substring(3)));

        return tempStartDate;
    });

    let newEndDate = $derived.by(() => {

        let tempEndDate = endDate;
        tempEndDate.setHours(parseInt(endTime.substring(0, 2)));
        tempEndDate.setMinutes(parseInt(endTime.substring(3)));

        return tempEndDate;
    });

    let calendarEl = $state();
    onMount(() => {
            if (currentTab === '/room-bookings') {
                /* function initCalendar(){ */
                calendarEl = document.getElementById('calendar');
                calendar = new Calendar(calendarEl, {
                    plugins: [timeGridPlugin, listPlugin, interactionPlugin],
                    locale: 'de',
                    height: window.innerHeight - 135,
                    editable: true,
                    events: async function (info, successCallback, failureCallback) {
                        const fetchedData = await fetchRoomBookings(info);
                        if (fetchedData) {
                            const events = fetchedData.map(event => (convertEventToFrontend(event, currentUser)));
                            successCallback(events);
                        } else {
                            failureCallback("Error");
                        }

                    },
                    eventDrop: function (eventDropInfo) {
                        eventDropInfo.jsEvent.preventDefault();
                        request(`/api/room-bookings/${eventDropInfo.event.id}`, {
                            method: "PATCH",
                            body: JSON.stringify(convertEventToBackend(eventDropInfo.event)),
                            headers: {'Content-Type': 'application/json'}
                        });
                    },
                    eventResize: function (eventResizeInfo) {
                        eventResizeInfo.jsEvent.preventDefault();
                        request(`/api/room-bookings/${eventResizeInfo.event.id}`, {
                            method: "PATCH",
                            body: JSON.stringify(convertEventToBackend(eventResizeInfo.event)),
                            headers: {'Content-Type': 'application/json'}
                        });
                    },
                    eventClick: function (info) {
                        info.jsEvent.preventDefault();
                        if (info.event.extendedProps.isAuthor || user.roleIds.includes(3)) {
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

    $effect(() => {
        if (calendar && currentTab === '/room-bookings') {
            calendar.render();

            setTimeout(() => {
                calendar.getEvents().forEach((event) => event.setProp('color', event.extendedProps.status === 'REQUESTED' ? '#7600ff' : event.extendedProps.status === 'REJECTED' ? 'red' : 'oklch(75% 0.183 55.934)'));
            }, 300);

        }
    })

    function createEvent(e) {
        e.stopPropagation();
        const date = new Date();
        const endDate = new Date();
        endDate.setHours(endDate.getHours() + 1);
        selectedEvent = {
            title: "_New Event_",
            start: date,
            end: endDate,
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
        const response = request(`/api/room-bookings/${selectedEvent.id}`, {
            method: 'DELETE'
        });
        selectedEvent.remove();
        selectedEvent = false;
    }

    function createNewBackendEvent() {
        if (dev) {
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
    }

    const confirm = getContext('confirm');

    async function approveEvent() {

        calendar.getEventById(selectedEvent.id).setExtendedProp('status', 'APPROVED');
        calendar.getEventById(selectedEvent.id).setProp('color', 'oklch(75% 0.183 55.934)');

        const response = await request(`/api/room-bookings/${selectedEvent.id}`, {
            method: 'PATCH',
            body: JSON.stringify(convertEventToBackend({status: 'APPROVED'})),
            headers: {'Content-Type': 'application/json'}
        });

        successRequest = {
            ok: response.ok
        }

        setTimeout(() => successRequest = undefined, 1500);

    }

    async function denyEvent() {
        calendar.getEventById(selectedEvent.id).setExtendedProp('status', 'REJECTED');
        calendar.getEventById(selectedEvent.id).setProp('color', 'red');

        const response = await request(`/api/room-bookings/${selectedEvent.id}`, {
            method: 'PATCH',
            body: JSON.stringify(convertEventToBackend({status: 'REJECTED'})),
            headers: {'Content-Type': 'application/json'}
        });

        successRequest = {
            ok: response.ok
        }

        setTimeout(() => successRequest = undefined, 1500);

    }

    async function saveEvent() {
        if (between(newStartDate.getHours(), 6, 18) || between(newEndDate.getHours(), 6, 18)) {            //TODO: Fix times
            const confirmed = await confirm('_The event is scheduled inside business hours. It has to be confirmed by CSW officials first. Do you want to continue?_');
            if (!confirmed) {
                return;
            }
        }
        if (selectedEvent.new) {
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
        const response = await request(selectedEvent.new ? "/api/room-bookings" : `/api/room-bookings/${selectedEvent.id}`, {
            method: selectedEvent.new ? 'POST' : 'PATCH',
            body: JSON.stringify(selectedEvent.new ? createNewBackendEvent() : convertEventToBackend(calendar.getEventById(selectedEvent.id))),
            headers: {'Content-Type': 'application/json'}
        });

        successRequest = {
            ok: response.ok
        }

        setTimeout(() => successRequest = undefined, 1500);
    }

    function sortFunction(x, y, selectUsers) {
        if (selectUsers.includes(x) && !selectUsers.includes(y)) return -1;
        if (selectUsers.includes(y) && !selectUsers.includes(x)) return 1;

        if (x.lastName && y.lastName) {
            if (x.lastName > y.lastName) return 1;
            if (x.lastName < y.lastName) return -1;
        }
    }

    function displayFunction(user) {
        return user.firstName + ", " + user.lastName;
    }

    function filterFunction(user, searchTerm) {
        return !searchTerm ||
            user.firstName.toLowerCase().includes(searchTerm.toLowerCase()) ||
            user.lastName.toLowerCase().includes(searchTerm.toLowerCase())
    }

    $effect(() => {
        if(/* selectedEvent & & */calendar) {

            /* calendar.getEventById(selectedEvent.id).setEnd(eventDate);  */
            calendar.setOption('locale', localeString === 'en' ? 'en-us' : localeString);
    }
    });

     /* $effect(() => {
        if(calendar){
           
        } 
            
            if (localeString === 'en') {
                calendar.setOption('locale', 'en-us');
            } else {
                calendar.setOption('locale', localeString);
            } 
    }); */
</script>

<TableView columns={columns}
           contentPage={data.page}
           onCreate={() => modal = true}
           onItemClicked={(item) => goto(`/manage/room-bookings/${item.id}`)}
/>

<CreateRoomBookingModal bind:open={modal} accessList={data.access} statusList={data.status}/>