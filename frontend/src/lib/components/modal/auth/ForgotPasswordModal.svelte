<script>
    import {Button, Heading, Hr, Input, Modal, Spinner} from 'flowbite-svelte';
    import Altcha from "$lib/components/Altcha.svelte"
    import {m} from '$lib/paraglide/messages.js';

    let {
        isTrusted = false,
        open = $bindable(false)
    } = $props()

    let email = $state("");
    let altchaToken = $state("");

    let error = $state("");
    let loading = $state(false);

</script>

<Modal form bind:open={open} size="sm" outsideclose={false}>
    <div class="flex flex-col gap-5">
        <Heading tag="h3" class="text-center">
            {m.modal_forgot_password_title()}
        </Heading>

        <Hr class="m-0 p-0"/>

        <div>
            <div>{m.modal_forgot_password_label_email()}</div>
            <Input name="input_email" type="email" bind:value={email} required/>
        </div>

        {#if !isTrusted}
            <Altcha name="input_altcha" bind:value={altchaToken}/>
        {/if}

        {#if error?.trim()}
            <div class="text-red-400 text-justify">{error}</div>
        {/if}

        <Button name="button_submit" type="submit">
            {#if loading === true}
                <Spinner size="5"/>
            {:else}
                {m.modal_forgot_password_button()}
            {/if}
        </Button>
    </div>
</Modal>