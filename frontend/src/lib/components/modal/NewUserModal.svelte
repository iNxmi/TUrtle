<script>
    import request from '$lib/api/api.js';
    import {Button, Checkbox, Input, Label, Modal} from 'flowbite-svelte';
    import {EnvelopeSolid} from 'flowbite-svelte-icons';

    let {showModal = $bindable()} = $props();

    let user = $state({
        username: "",
        firstName: "",
        lastName: "",
        email: "",
        password: "",
        roleIDs: []
    });

    let acceptTOS = $state(false);
    let acceptGDPR = $state(false);
    let passwordRepeat = $state('');

    let hasAccepted = $derived(acceptGDPR && acceptTOS);

    async function createNewUser() {
        if (acceptGDPR && acceptTOS && user.password === passwordRepeat) {
            const userResponse = await request('/users', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(user)
            });
            if (userResponse.ok) {
                showModal = false;
            }
        }
    }
</script>
<Modal bind:open={showModal} title='_New User_'>
    <form>
        <div class="mb-6 grid gap-6 md:grid-cols-2">
            <Label for="firstname">_First name_
                <Input id="firstname" required placeholder="Max" bind:value={user.firstName}/>
            </Label>
            <Label>
                _Last name_
                <Input required placeholder="Mustermann" bind:value={user.lastName}/>
            </Label>
            <Label>
                _Username_
                <Input required placeholder="ProMax" bind:value={user.username}/>
            </Label>
            <Label>
                _Email_
                <Input class="ps-8" type="email" placeholder="max.mustermann@stud.tu-darmstadt.de"
                       bind:value={user.email} required>
                    {#snippet left()}
                        <EnvelopeSolid/>
                    {/snippet}
                </Input>
            </Label>
            <Label>
                _password_
                <Input required bind:value={user.password}/>
            </Label>
            <Label>
                _password repeat_
                <Input pattern={user.password} required bind:value={passwordRepeat}/>
            </Label>
            <div class="flex flex-col gap-2">
                <Checkbox required bind:checked={acceptTOS}><span>_Accept_ <a class="text-csw" href="/tos">_Terms of Service_</a></span>
                </Checkbox>
                <Checkbox required bind:checked={acceptGDPR}><span>_Accept_ <a class="text-csw" href="/gdpr">_GDPR Agreement_</a></span>
                </Checkbox>
            </div>
            <Button disabled={!hasAccepted} type="submit" onclick={createNewUser}>_Create new User_</Button>
        </div>
    </form>
</Modal>