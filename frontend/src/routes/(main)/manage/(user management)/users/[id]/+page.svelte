<script>
    import {Button, ButtonGroup, Input, MultiSelect} from "flowbite-svelte";
    import {UndoOutline} from "flowbite-svelte-icons";
    import {m} from '$lib/paraglide/messages.js';
    import Card from "$lib/components/Card.svelte";

    let {data} = $props();
    let user = $derived(data.user);
    let roles = $derived(data.roles);

    let input = $state({
        username: user.username,
        firstName: user.firstName,
        lastName: user.lastName,
        email: user.email,
        emojis: user.emojis,
        roleIds: user.roleIds
    });

    let edit = $state(false);
</script>

<Card>
    <form class="flex flex-col gap-5">
        <div>
            <span>{m.manage_users_label_id()}</span>
            <Input type="text" value={user.id} disabled/>
        </div>

        <div class="flex flex-col">
            <span>{m.manage_users_label_username()}</span>
            <ButtonGroup>
                <Input type="text" bind:value={input.username} disabled={edit === false}/>
                {#if edit === true}
                    <Button onclick={() => input.username = user.username} disabled={input.username === user.username}>
                        <UndoOutline/>
                    </Button>
                {/if}
            </ButtonGroup>
        </div>

        <div class="flex gap-5">
            <div class="flex-1 flex flex-col">
                <span>{m.manage_users_label_first_name()}</span>
                <ButtonGroup>
                    <Input type="text" bind:value={input.firstName} disabled={edit === false}/>
                    {#if edit === true}
                        <Button onclick={() => input.firstName = user.firstName}
                                disabled={input.firstName === user.firstName}>
                            <UndoOutline/>
                        </Button>
                    {/if}
                </ButtonGroup>
            </div>

            <div class="flex-1 flex flex-col">
                <span>{m.manage_users_label_last_name()}</span>
                <ButtonGroup>
                    <Input type="text" bind:value={input.lastName} disabled={edit === false}/>
                    {#if edit === true}
                        <Button onclick={() => input.lastName = user.lastName}
                                disabled={input.lastName === user.lastName}>
                            <UndoOutline/>
                        </Button>
                    {/if}
                </ButtonGroup>
            </div>
        </div>

        <div class="flex flex-col">
            <div>{m.manage_users_label_email()}</div>
            <ButtonGroup>
                <Input type="text" bind:value={input.email} disabled={edit === false}/>
                {#if edit === true}
                    <Button onclick={() => input.email = user.email}
                            disabled={input.email === user.email}>
                        <UndoOutline/>
                    </Button>
                {/if}
            </ButtonGroup>
        </div>

        <div class="flex flex-col">
            <div>{m.manage_users_label_emojis()}</div>
            <ButtonGroup>
                <Input type="text" bind:value={input.emojis} disabled={edit === false}/>
                {#if edit === true}
                    <Button onclick={() => input.emojis = user.emojis}
                            disabled={input.emojis === user.emojis}>
                        <UndoOutline/>
                    </Button>
                {/if}
            </ButtonGroup>
        </div>

        <div>
            <span>{m.manage_users_label_roles()}</span>
            <MultiSelect items={roles.map((role) => ({value: role.id, name: role.name}))} value={input.roleIds}
                         disabled={edit === false}/>
        </div>

        <div class="flex gap-5">
            <div class="flex-1">
                <span>{m.manage_users_label_created_at()}</span>
                <Input type="text" value={(new Date(user.createdAt)).toLocaleString()} disabled/>
            </div>
            <div class="flex-1">
                <span>{m.manage_users_label_updated_at()}</span>
                <Input type="text" value={(new Date(user.updatedAt)).toLocaleString()} disabled/>
            </div>
        </div>

        {#if edit === true}
            <div class="flex gap-5">
                <Button class="flex-1" onclick={() => edit = !edit}>_save_</Button>
                <Button class="flex-1" color="alternative" onclick={() => edit = !edit}>_cancel_</Button>
            </div>
        {:else}
            <Button onclick={() => edit = !edit}>_edit_</Button>
        {/if}

    </form>
</Card>