<script>
	import { goto } from '$app/navigation';
    import { Modal, Heading, Button } from 'flowbite-svelte';
    let { lockerOpen = $bindable(), lockerNumber } = $props();
function confirm(){
    lockerOpen = false;
    goto('/user/dashboard',{invalidateAll: true});
}
</script>

<Modal open={lockerOpen}>
    <Heading tag="h3">_Your device is in Locker_ <span class="font-bold text-csw">{lockerNumber}</span>. You can take it out now_</Heading>
    <div class="flex justify-center">
        <Button onclick={confirm}>_Confirm_</Button>
    </div>
</Modal>