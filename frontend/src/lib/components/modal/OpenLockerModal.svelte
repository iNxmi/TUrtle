<script>
    import {Button, Modal} from 'flowbite-svelte';
    import request from '$lib/api/api.js';
    import {QuestionCircleSolid} from "flowbite-svelte-icons";

    let {
        open = $bindable(false),
        locker
    } = $props();

    async function unlock(event) {
        event.preventDefault()

        const response = await request(`/hardware/locker/open?id=${locker.id}`, {method: "POST"});

        open = false;
    }

</script>

<Modal form bind:open={open} size="sm">
    <div class="flex flex-col gap-5 items-center">
        <div class="flex gap-5 justify-center">
            <QuestionCircleSolid class="h-12 w-12"/>
            <div class="flex flex-col justify-center">
                <h1 class="text-lg font-bold">
                    _unlock locker <span class="text-csw font-bold">{locker.index}</span>?_
                </h1>
            </div>
        </div>

        <h3 class="text-lg text-center">
            by unlocking you agree to the following tos
        </h3>

        <div class="flex gap-2">
            <Button type="submit" value="unlock" color="red" onclick={unlock}>_Unlock_</Button>
            <Button type="submit" value="cancel" color="alternative" onclick={() => open = false}>_Cancel_</Button>
        </div>
    </div>
</Modal>