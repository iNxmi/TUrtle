<script>
    import {Label, Button, Card, Heading} from 'flowbite-svelte';
    import request from '$lib/api/api';
    import {openLocker} from '$lib/utils';

    let {data} = $props();

    // let lockerIndex = $derived.by(() => {
    //     return data.locker.map((locker) => locker.id);
    // });

    async function lockLocker(locker) {
        const payload = {
            locked: locker.locked !== true
        }

        await request(`/lockers/${locker.id}`, {
            method: "PATCH",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(payload)
        });

        location.reload()
    }

    async function openDoor() {
        const response = await request(`/hardware/door?seconds=3`);
        if (response.ok) {
            return true;
        }
    }

    async function lockDoor() {
        //TODO: API/Backend Implementation
    }
</script>

<div class="flex flex-row gap-5">
    <div class="rounded-lg border-gray-200 p-1">
        <Heading class="mb-5" tag='h2'>_Locker_</Heading>
        {#each data.lockers as locker, index}
            {#if locker.isSoftwareUnlockable}
                <span class="mb-2">_Locker_ {locker.name}_</span>
                <div class="flex flex-row gap-2 mb-1">
                    <Button onclick={() => openLocker(locker.id)}>_Open_</Button>
                    <Button onclick={() => lockLocker(locker)} class="bg-red-600">
                        {#if locker.locked === true}_Unlock_{:else}_Lock_{/if}
                    </Button>
                </div>
            {/if}
        {/each}
    </div>
    <div class=" rounded-lg border-gray-200 p-1 text-center">
        <Heading class="mb-5" tag="h2">_Door_</Heading>
        <Button onclick={openDoor}>Open</Button>
        <Button onclick={lockDoor} class="bg-red-600">Lock</Button>
    </div>
</div>

<!-- TODO -->
<span class="text-red-500 font-bold">TODO: API/Backend Integration</span>
