<script>
	import request from "$lib/api/api";
    import OpenLockerModal from "./OpenLockerModal.svelte";
    import { getContext } from "svelte";

    let {reservations, devices } = $props();
    let showOpenLockerModal = $state(false);
    let locker = $state();
    let selectedReservationIndex = $state();

    let bookingInfo = $derived(reservations ? reservations.map((reservation) => {
        const device = devices.find((device) =>  reservation.itemId === device.id);
        return { 
            deviceName: device.name, 
            start: new Date(reservation.start), 
            end: new Date(reservation.end),
            locker: device.locker,
            id: reservation.id,
            status: reservation.status
        }
    }): []);

    let sortedBookingInfo = $derived(bookingInfo.sort((x,y) => {
        if(x.status === 'PENDING_COLLECTION' && y.status === 'PENDING_RETURN') return -1;
        if((x.status === 'PENDING_COLLECTION' || x.status === 'PENDING_RETURN') && y.status !== 'PENDING_COLLECTION' && y.status !== 'PENDING_RETURN') return -1;
       /*  return x.start < y.start ? -1 : 1; */
    }));
    let dt = $derived(Intl.DateTimeFormat(getContext('locale'), {dateStyle: "short",timeStyle: "short"}));

    async function handleReservationInteraction(reservationIndex){

        if(bookingInfo[reservationIndex].needsConfirmation){
            const confirmation = getContext('confirm');
            const success = await confirmation('_This item is a high value item. Therefore you need to sign a borrowing contract before this item can be borrowed. If you have interest in signing a contract, click the button below');
            if(success){

            }
        }

        selectedReservationIndex = reservationIndex;
        locker = bookingInfo[reservationIndex].locker;
        showOpenLockerModal = true;

    }
</script>
<div class="flex flex-col gap-4 max-h-svh overflow-y-auto">
    {#each sortedBookingInfo as reservation, i (reservation.id)}
        {#if reservation.status === "PENDING_COLLECTION" || reservation.status === "PENDING_RETURN"}
            <button onclick={() => handleReservationInteraction(i)} class="hover:cursor-pointer h-20 bg-gray-50 dark:bg-gray-700 border-3 border-orange-400! rounded-lg dark:border-gray-800 shadow grid grid-flow-row grid-rows-1 grid-cols-4">
                <div class="place-self-center"><div class="flex flex-col text-center"><span class="text-sm text-muted">_Device_</span><span class="font-bold text-lg dark:text-white">{reservation.deviceName}</span></div></div>
                <div class="place-self-center"><div class="flex flex-col text-center"><span class="text-sm text-muted">_Start date_</span><span class="font-bold text-lg dark:text-white">{dt.format(reservation.start)}</span></div></div>
                <div class="place-self-center"><div class="flex flex-col text-center"><span class="text-sm text-muted">_EndDate_</span><span class="font-bold text-lg dark:text-white">{dt.format(reservation.end)}</span></div></div>
                <div class="place-self-center"><div class="flex flex-col text-center"><span class="text-sm text-muted">_Locker_</span><span class="font-bold text-lg dark:text-white">{reservation.locker}</span></div></div> 
            </button>
        {:else if reservation.status === 'COLLECTED'}
            <button onclick={() => handleReservationInteraction(i)} class="h-20 bg-gray-50 dark:bg-gray-700 border rounded-lg border-gray-100 dark:border-gray-800 shadow grid grid-flow-row grid-rows-1 grid-cols-4">
                <div class="place-self-center"><div class="flex flex-col text-center"><span class="text-sm text-muted">_Device_</span><span class="font-bold text-lg dark:text-white">{reservation.deviceName}</span></div></div>
                <div class="place-self-center"><div class="flex flex-col text-center"><span class="text-sm text-muted">_Start date_</span><span class="font-bold text-lg dark:text-white">{dt.format(reservation.start)}</span></div></div>
                <div class="place-self-center"><div class="flex flex-col text-center"><span class="text-sm text-muted">_EndDate_</span><span class="font-bold text-lg dark:text-white">{dt.format(reservation.end)}</span></div></div>
                <div class="place-self-center"><div class="flex flex-col text-center"><span class="text-sm text-muted">_Locker_</span><span class="font-bold text-lg dark:text-white">{reservation.locker}</span></div></div> 
            </button>
        {:else if reservation.status === 'REQUESTED'}
            <div class="pending-booking h-20 bg-gray-50 dark:bg-gray-700 border rounded-lg border-gray-100 dark:border-gray-800 shadow grid grid-flow-row grid-rows-1 grid-cols-4">
                <div class="place-self-center"><div class="flex flex-col text-center"><span class="text-sm text-muted">_Device_</span><span class="font-bold text-lg dark:text-white">{reservation.deviceName}</span></div></div>
                <div class="place-self-center"><div class="flex flex-col text-center"><span class="text-sm text-muted">_Start date_</span><span class="font-bold text-lg dark:text-white">{dt.format(reservation.start)}</span></div></div>
                <div class="place-self-center"><div class="flex flex-col text-center"><span class="text-sm text-muted">_EndDate_</span><span class="font-bold text-lg dark:text-white">{dt.format(reservation.end)}</span></div></div>
                <div class="place-self-center"><div class="flex flex-col text-center"><span class="text-sm text-muted">_Locker_</span><span class="font-bold text-lg dark:text-white">{reservation.locker}</span></div></div> 
            </div>
        {:else}
            <div class="h-20 bg-gray-50 dark:bg-gray-700 border rounded-lg border-gray-100 dark:border-gray-800 shadow grid grid-flow-row grid-rows-1 grid-cols-4">
                <div class="place-self-center"><div class="flex flex-col text-center"><span class="text-sm text-muted">_Device_</span><span class="font-bold text-lg dark:text-white">{reservation.deviceName}</span></div></div>
                <div class="place-self-center"><div class="flex flex-col text-center"><span class="text-sm text-muted">_Start date_</span><span class="font-bold text-lg dark:text-white">{dt.format(reservation.start)}</span></div></div>
                <div class="place-self-center"><div class="flex flex-col text-center"><span class="text-sm text-muted">_EndDate_</span><span class="font-bold text-lg dark:text-white">{dt.format(reservation.end)}</span></div></div>
                <div class="place-self-center"><div class="flex flex-col text-center"><span class="text-sm text-muted">_Locker_</span><span class="font-bold text-lg dark:text-white">{reservation.locker}</span></div></div> 
            </div>
        {/if}
    {:else}
        <div class="h-100 flex justify-center items-center"><span class="text-center font-bold text-3xl text-muted">_No reservations made_</span></div>
    {/each}
</div>
<OpenLockerModal bind:showOpenLockerModal={showOpenLockerModal} {locker} reservationIndex={selectedReservationIndex} bind:reservations={reservations}/>