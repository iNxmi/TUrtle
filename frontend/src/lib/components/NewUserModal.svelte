<script>
    import request from '$lib/api/api';
    import { Modal, Input, Checkbox, Label, Button } from 'flowbite-svelte';
    import { EnvelopeSolid } from 'flowbite-svelte-icons';

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

    async function createNewUser(){
        if(acceptGDPR && acceptTOS && user.password === passwordRepeat){
            const userResponse = await request('/users',{
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(user)
            });
            if(userResponse.ok){
                showModal=false;
            }
        }
    }
</script>
<Modal bind:open={showModal} title='_New User_'> 
    <form>
        <div class="mb-6 grid gap-6 md:grid-cols-2">
            <div>
                <Label for="firstname">_First name_</Label>
                <Input id="firstname" required bind:value={user.firstName} />
            </div>
               <Label class="mb-2" >
                _Last name_
                    <Input  required bind:value={user.lastName} />
                </Label>
            <Label>
                _Username_
                <Input required bind:value={user.username}/>
            </Label>
            <Label>
                _Email_
                <Input class="ps-8" type="email" bind:value={user.email} required>
                    {#snippet left()}
                        <EnvelopeSolid />
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
                <Checkbox required bind:checked={acceptTOS}><span>_Accept_ <a class="text-csw" href="/tos">_Terms of Service_</a></span></Checkbox>
                <Checkbox required bind:checked={acceptGDPR}> <span>_Accept_ <a class="text-csw" href="/gdpr">_GDPR Agreement_</a></span></Checkbox>
            </div>
            <Button type="submit" onclick={createNewUser}>_Create new User_</Button>
        </div>
    </form>
</Modal>