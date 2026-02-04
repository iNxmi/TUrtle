<script>
    import {A, Button, Checkbox, Heading, Input, Label, Modal, P} from "flowbite-svelte";
    import {m} from '$lib/paraglide/messages.js';
    import Altcha from '$lib/components/Altcha.svelte';
    import request from "$lib/api/api.js";

    let apiResponse = $state(null);
    let modal = $state(false);
    let username = $state('');
    let firstName = $state('');
    let lastName = $state('');
    let email = $state('');
    let password = $state('');
    let passwordRepeat = $state('');
    let altchaToken = $state("");

    const {data} = $props();

    async function register(event) {
        event.preventDefault();

        const payload = {
            username: $state.snapshot(username),
            firstName: $state.snapshot(firstName),
            lastName: $state.snapshot(lastName),
            email: $state.snapshot(email),
            password: $state.snapshot(password),
            altchaToken: $state.snapshot(altchaToken)
        };

        const response = await request("/users", {
            method: 'POST',
            body: JSON.stringify(payload),
            headers: {'Content-Type': 'application/json'}
        });

        apiResponse = await response.json();
        modal = true;
    }
</script>

<div class="flex justify-center w-180 h-200 border border-neutral-300 dark:border-gray-700 bg-neutral-100 dark:bg-[#1E2939] rounded-xl p-20 pt-20 mx-auto max-w-full shadow-xl">
    <form class="flex flex-col gap-5" onsubmit={register}>
        <Heading tag="h3" class="text-center">
            {m.register__title()}
        </Heading>

        <Label>
            <span>{m.register__username_label()}</span>
            <Input bind:value={username} type="text" required/>
        </Label>

        <div class="flex gap-5">
            <Label class="flex-1">
                <span>{m.register__first_name_label()}</span>
                <Input bind:value={firstName} type="text" required/>
            </Label>
            <Label class="flex-1">
                <span>{m.register__last_name_label()}</span>
                <Input bind:value={lastName} type="text" required/>
            </Label>
        </div>

        <Label>
            <span>{m.register__email_label()}</span>
            <Input bind:value={email} type="email" required/>
        </Label>

        <div class="flex gap-5">
            <Label class="flex-1">
                <span>{m.register__password_label()}</span>
                <Input bind:value={password} type="password" required/>
            </Label>
            <Label class="flex-1">
                <span>{m.register__password_repeat_label()}</span>
                <Input bind:value={passwordRepeat} type="password" required/>
            </Label>
        </div>

        <Checkbox id="agree_tos" required>{m.register__i_agree_to_tos()}</Checkbox>

        <Altcha bind:value={altchaToken}/>

        <Button type="submit" class="w-full1">{m.register__button()}</Button>

        <A href="/auth/login"
           class="text-sm text-blue-700 hover:underline dark:text-blue-500">{m.register__already_have_a_account()}</A>
    </form>

    <Modal title="API Response" bind:open={modal}>
        {#if apiResponse}
            <P class="whitespace-pre">{JSON.stringify(apiResponse, null, 2)}</P>
        {/if}
    </Modal>
</div>