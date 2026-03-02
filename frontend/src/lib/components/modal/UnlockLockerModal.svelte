<script>
    import {Button, Modal, Spinner} from 'flowbite-svelte';
    import {Hardware} from '$lib/api';
    import {QuestionCircleSolid} from "flowbite-svelte-icons";

    let {
        open = $bindable(false),
        locker
    } = $props();

    let loading = $state(false);
    let error = $state("");

    async function unlock(event) {
        event.preventDefault();
        error = "";

        loading = true;
        const response = await Hardware.lockerOpen(locker.id);
        loading = false;

        if (!response.ok) {
            const json = await response.json();
            error = json.message;
            return;
        }

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

        {#if error.trim()}
            <div class="text-red-400 text-justify">{error}</div>
        {/if}

        //TODO change button text
        <div class="flex gap-2">
            <Button color="red" onclick={unlock}>
                {#if loading === true}
                    <Spinner size="5"/>
                {:else}
                    _Unlock_
                {/if}
            </Button>
            <Button color="alternative" onclick={() => open = false}>
                _Cancel_
            </Button>
        </div>
    </div>
</Modal>