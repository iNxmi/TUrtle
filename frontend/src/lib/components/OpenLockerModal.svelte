<script>
    import {Modal, Button} from 'flowbite-svelte';
    import {openLocker} from '$lib/utils'
    import {slide} from 'svelte/transition'
    import request from '$lib/api/api';
    let { showOpenLockerModal = $bindable(false), locker, reservationIndex, reservations = $bindable()} = $props();

    async function handleOpenLocker(){

        showOpenLockerModal = false;
        const updatedStatus = await openLocker(locker, reservations[reservationIndex]);
        const reservationResponse = await request('/devicebookings', {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({status: updatedStatus})
        });

        const tempReservations = [...reservations];
        tempReservations[reservationIndex].status = updatedStatus;
        reservations = tempReservations;



   }

</script>
<Modal transition={slide} bind:open={showOpenLockerModal} size="xs" permanent>
    <div class="text-center">
       <h3 class="mb-5 text-lg"> _Do you want to open locker <span class="text-csw font-bold">{locker}</span> now?_</h3>
        <div class="space-x-2">
            <Button class="hover:cursor-pointer" onclick={() => handleOpenLocker()}>_Yes_</Button>
            <Button class="hover:cursor-pointer" color="red" onclick={() => showOpenLockerModal = false}>_No_</Button>
        </div>
    </div>
</Modal>