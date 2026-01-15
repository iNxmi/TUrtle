<script>
	import request from "$lib/api/api";
    import { getContext } from "svelte";

    let {reservations, devices } = $props();

    let bookingInfo = $derived(reservations.map((reservation) => {
        const device = devices.find((device) =>  reservation.deviceId === device.id);
        return { 
            deviceName: device.name, 
            start: new Date(reservation.start), 
            end: new Date(reservation.end),
            locker: device.locker
        }
    }));
    let dt = $derived(Intl.DateTimeFormat(getContext('locale'), {dateStyle: "medium",timeStyle: "medium"}));
</script>
<div class="flex flex-col gap-4 max-h-svh overflow-y-auto">
    {#each bookingInfo as reservation, i}
         <div class=" h-20 bg-gray-50 dark:bg-gray-700 border rounded-lg border-gray-100 dark:border-gray-800 shadow grid grid-flow-row grid-rows-1 grid-cols-4">
            <div class="place-self-center"><div class="flex flex-col text-center"><span class="text-sm text-muted">_Device_</span><span class="font-bold text-lg dark:text-white">{reservation.deviceName}</span></div></div>
            <div class="place-self-center"><div class="flex flex-col text-center"><span class="text-sm text-muted">_Start date_</span><span class="font-bold text-lg dark:text-white">{dt.format(reservation.start)}</span></div></div>
            <div class="place-self-center"><div class="flex flex-col text-center"><span class="text-sm text-muted">_EndDate_</span><span class="font-bold text-lg dark:text-white">{dt.format(reservation.end)}</span></div></div>
            <div class="place-self-center"><div class="flex flex-col text-center"><span class="text-sm text-muted">_Locker_</span><span class="font-bold text-lg dark:text-white">{reservation.locker}</span></div></div> 
        </div>
    {:else}
        <div class="h-100 flex justify-center items-center"><span class="text-center font-bold text-3xl text-muted">_No reservations made_</span></div>
    {/each}
</div>