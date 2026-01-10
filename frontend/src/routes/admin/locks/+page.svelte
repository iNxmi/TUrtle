<script>
    import {Label, Button, Card, Heading} from 'flowbite-svelte';
	import { request } from '$lib/api/api';

    let {data} = $props();

    let lockerIndex = $derived.by(() => {
        return data.locker.map((locker) => locker.index);
        });
    
    async function openLocker(index){
        const response = await request(`/hardware/locker`);
    }
    async function lockLocker(index){
        //TODO: API/Backend Implementation
    }
    async function openDoor(){
        const response = await request(`/hardware/door`);
        if(response.ok){
            return true;
        }
    }
    async function lockDoor(){
        //TODO: API/Backend Implementation
    }
</script>
<div class="flex flex-row gap-5">
    <div class="rounded-lg border-gray-200 p-1">
        <Heading class="mb-5" tag='h2'>_Locker_</Heading>
        {#each lockerIndex as index, i}
            <span class="mb-2">_Locker_ {index}_</span>
                <div class="flex flex-row gap-2 mb-1">
                    <Button onclick={openLocker(i)}>Open</Button>
                    <Button onclick={lockLocker(i)} class="bg-red-600">Lock</Button>
                </div>
        {/each}
    </div>
        <div class=" rounded-lg border-gray-200 p-1 text-center">
            <Heading class="mb-5" tag="h2">_Door_</Heading>
            <Button onclick={openDoor}>Open</Button>
            <Button onclick={lockDoor} class="bg-red-600">Lock</Button>
        </div>
</div>
<span class="text-red-500 font-bold">TODO: API/Backend Integration</span>
