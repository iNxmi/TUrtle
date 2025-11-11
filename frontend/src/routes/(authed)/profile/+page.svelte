<script>
    import {Button, Checkbox, Label, Input, Heading, A, P} from "flowbite-svelte";
    import {m} from '$lib/paraglide/messages.js';

    let apiResponse = $state(null);
</script>

<form class="flex flex-col gap-5" onsubmit={login}>
    <Heading tag="h3" class="text-center">{m.login__title()}</Heading>

    <Label>
        <span>_profile__username</span>
        <Input name="username_or_email" type="text" value="MaxMusterMannHD" required disabled/>
    </Label>

    <Label>
        <span>_profile__name</span>
        <Input name="password" type="text" required/>
    </Label>

    <Checkbox>_remember_me_text</Checkbox>

    <div class="border border-dashed">
        <P class="text-center m-8">I am not a Robot ✅</P>
    </div>

    <Button type="submit">{m.login__button()}</Button>

    <div class="flex gap-5 justify-between">
        <A href="/register" class="text-sm text-blue-700 hover:underline dark:text-blue-500">{m.login__no_account()}</A>
        <A href="/" class="text-sm text-blue-700 hover:underline dark:text-blue-500">{m.login__forgot_password()}</A>
    </div>
</form>

{#if apiResponse}
    <p class="whitespace-pre">{JSON.stringify(apiResponse, null, 2)}</p>
{/if}
