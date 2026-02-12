<script>
    import {A, Button, ButtonGroup, Checkbox, Heading, Input, InputAddon, Label} from "flowbite-svelte";
    import {m} from '$lib/paraglide/messages.js';
    import Altcha from '$lib/components/Altcha.svelte';
    import request from "$lib/api/api.js";
    import {EyeOutline, EyeSlashOutline} from "flowbite-svelte-icons";

    let username = $state("");
    let firstName = $state("");
    let lastName = $state("");
    let email = $state("");
    let password = $state("");
    let passwordRepeat = $state("");
    let altchaToken = $state("");

    let showPassword = $state(false);
    let showPasswordRepeat = $state(false);

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
    }
</script>

<div class="flex flex-col gap-20 min-h-svh w-full bg-background justify-center items-center">
    <div class="flex justify-center bg-neutral-100 dark:bg-[#1E2939] rounded-lg p-10 mx-auto max-w-full shadow-xl border border-neutral-300 dark:border-gray-700">
        <form class="flex flex-col gap-5 w-120 border-amber-900" onsubmit={register}>
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
                    <ButtonGroup class="w-full">
                        <Input bind:value={password} type={showPassword ? "text" : "password"} required/>
                        <InputAddon>
                            <button type="button" onclick={(e) => {
                                e.preventDefault();
                                showPassword = !showPassword;
                            }}>
                                {#if showPassword}
                                    <EyeOutline class="h-6 w-6"/>
                                {:else}
                                    <EyeSlashOutline class="h-6 w-6"/>
                                {/if}
                            </button>
                        </InputAddon>
                    </ButtonGroup>
                </Label>
                <Label class="flex-1">
                    <span>{m.register__password_repeat_label()}</span>
                    <ButtonGroup class="w-full">
                        <Input bind:value={passwordRepeat} type={showPasswordRepeat ? "text" : "password"} required/>
                        <InputAddon>
                            <button type="button" onclick={(e) => {
                                e.preventDefault();
                                showPasswordRepeat = !showPasswordRepeat;
                            }}>
                                {#if showPasswordRepeat}
                                    <EyeOutline class="h-6 w-6"/>
                                {:else}
                                    <EyeSlashOutline class="h-6 w-6"/>
                                {/if}
                            </button>
                        </InputAddon>
                    </ButtonGroup>
                </Label>
            </div>

            <Checkbox id="agree_tos" required>{m.register__i_agree_to_tos()}</Checkbox>

            {#if !data.trusted}
                <Altcha bind:value={altchaToken}/>
            {/if}

            <Button type="submit" class="w-full cursor-pointer">{m.register__button()}</Button>

            <A href="/auth/login"
               class="text-sm text-blue-700 hover:underline dark:text-blue-500">{m.register__already_have_a_account()}
            </A>
        </form>
    </div>
</div>