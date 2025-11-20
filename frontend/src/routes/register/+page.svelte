<script>
    import {Button, Checkbox, Label, Input, Heading, A, P, Modal} from "flowbite-svelte";
    import {m} from '$lib/paraglide/messages.js';
    import ReCAPTCHA from '../../components/ReCAPTCHA.svelte';
    import request from "$lib/api/api.js";

    let apiResponse = $state(null);
    let modal = $state(false);

    async function register(event) {
        event.preventDefault();

        const payload = {
            username:  document.getElementById('username').value,
            firstName: document.getElementById('first_name').value,
            lastName: document.getElementById('last_name').value,
            email: document.getElementById('email').value,
            studentId: document.getElementById('student_id').value,
            password: document.getElementById('password').value
        };

        alert(JSON.stringify(payload, null, 2));

        const response = await request('/auth/register', {
            method: 'POST',
            body: JSON.stringify(payload),
            headers: {'Content-Type': 'application/json'}
        });

        apiResponse = await response.json();
        modal = true;
    }
</script>

<div>
    <form class="flex flex-col gap-5" onsubmit={register}>
        <Heading tag="h3" class="text-center">
            {m.register__title()}
        </Heading>

        <Label>
            <span>{m.register__username_label()}</span>
            <Input id="username" type="text" required/>
        </Label>

        <div class="flex gap-5">
            <Label class="flex-1">
                <span>{m.register__first_name_label()}</span>
                <Input id="first_name" type="text" required/>
            </Label>
            <Label class="flex-1">
                <span>{m.register__last_name_label()}</span>
                <Input id="last_name" type="text" required/>
            </Label>
        </div>

        <Label>
            <span>{m.register__email_label()}</span>
            <Input id="email" type="email" required/>
        </Label>

        <Label>
            <span>{m.register__student_id_label()}</span>
            <Input id="student_id" type="text" required/>
        </Label>

        <div class="flex gap-5">
            <Label class="flex-1">
                <span>{m.register__password_label()}</span>
                <Input id="password" type="password" required/>
            </Label>
            <Label class="flex-1">
                <span>{m.register__password_repeat_label()}</span>
                <Input id="password_repeat" type="password" required/>
            </Label>
        </div>

        <div class="flex items-start gap-5 justify-between">
            <Checkbox id="agree_tos">{m.register__i_agree_to_tos()}</Checkbox>
            <ReCAPTCHA/>
        </div>

        <Button type="submit" class="w-full1">{m.register__button()}</Button>

        <A href="/login"
           class="text-sm text-blue-700 hover:underline dark:text-blue-500">{m.register__already_have_a_account()}</A>
    </form>

    <Modal title="API Response" bind:open={modal}>
        {#if apiResponse}
            <P class="whitespace-pre">{JSON.stringify(apiResponse, null, 2)}</P>
        {/if}
    </Modal>
</div>