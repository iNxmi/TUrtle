<script>
    import {Button, Modal, P} from "flowbite-svelte";

    let apiResponse = $state(null)
    let modal = $state(false)

    function logout() {
        apiResponse = fetch("api/auth/logout")
        modal = true;
    }
</script>

<div>
    <Button color="orange" onclick={logout}>Logout</Button>

    <Modal title="API Response" form bind:open={modal}>
        {#if apiResponse}
            <P class="whitespace-pre">{JSON.stringify(apiResponse, null, 2)}</P>
        {/if}
    </Modal>
</div>