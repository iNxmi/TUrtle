<script>
    import {setContext} from 'svelte';
    import {Button, Modal} from 'flowbite-svelte';

    let {children} = $props();
    let open = $state(false);
    let resolvePromise = $state(null);

    let title = $state('');
    let message = $state();

    function confirm(msg, ttl = '_Confirm_') {
        title = ttl;
        message = msg;
        open = true;

        return new Promise((resolve) => {
            resolvePromise = resolve;
        })
    }

    function close(result) {
        open = false;
        if (resolvePromise) {
            resolvePromise(result);
            resolvePromise = null;
        }
    }

    setContext('confirm', confirm);

</script>

{@render children?.()}

<Modal size="sm" onclose={() => close(false)} title={title} {open}>
    <span>{message}</span>

    {#snippet footer()}
        <Button class="cursor-pointer" type="submit" onclick={() => close(true)}>_Confirm_</Button>
        <Button color="red" class="cursor-pointer" onclick={() => close(false)}>__Deny_</Button>
    {/snippet}
</Modal>