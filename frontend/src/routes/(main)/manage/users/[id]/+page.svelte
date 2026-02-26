<script>
    import {Button, ButtonGroup, Input, MultiSelect, Spinner, Tooltip} from "flowbite-svelte";
    import {
        CloseOutline,
        EditOutline,
        FloppyDiskAltOutline,
        ShareNodesOutline,
        UndoOutline
    } from "flowbite-svelte-icons";
    import {m} from '$lib/paraglide/messages.js';
    import Card from "$lib/components/Card.svelte";
    import {Users} from "$lib/api";
    import {invalidateAll} from "$app/navigation";
    import isEqual from "lodash/isEqual";

    let {data} = $props();
    let user = $derived(data.user);
    let roles = $derived(data.roles);

    let roleItems = $derived(roles.map((role) => ({
        value: role.id,
        name: role.name
    })));

    let input = $state({
        username: user.username,
        firstName: user.firstName,
        lastName: user.lastName,
        email: user.email,
        emojis: user.emojis,
        roleIds: user.roleIds
    });

    function cancel(event) {
        event.preventDefault();

        input.username = user.username;
        input.firstName = user.firstName;
        input.lastName = user.lastName;
        input.email = user.email;
        input.emojis = user.emojis;
        input.roleIds = user.roleIds;

        edit = false;
    }

    let edit = $state(false);

    async function clipboard(event) {
        event.preventDefault();

        const url = window.location.href;
        await navigator.clipboard.writeText(url);
    }

    function difference(dominant, submissive) {
        const result = {};

        for (const key in submissive)
            if (!isEqual(submissive[key], dominant[key]))
                result[key] = dominant[key];

        return result;
    }

    let saveLoading = $state(false);

    async function save(event) {
        event.preventDefault();

        const payload = difference(input, user);

        saveLoading = true;
        const response = await Users.patch(user.id, payload);
        saveLoading = false;

        if (!response.ok)
            return;

        await invalidateAll();
        edit = false;
    }
</script>

<div class="flex flex-col lg:flex-row gap-5">
    <Card class="grow">
        <form class="flex flex-col gap-5">
            <div>
                <div>{m.manage_users_label_id()}</div>
                <Input type="text" value={user.id} disabled/>
            </div>

            <div class="flex flex-col">
                <div>{m.manage_users_label_username()}</div>
                <ButtonGroup>
                    <Input type="text" bind:value={input.username} disabled={edit === false}/>
                    {#if edit === true}
                        <Button onclick={() => input.username = user.username}
                                disabled={input.username === user.username}>
                            <UndoOutline/>
                        </Button>
                    {/if}
                </ButtonGroup>
            </div>

            <div class="flex gap-5">
                <div class="flex-1 flex flex-col">
                    <div>{m.manage_users_label_first_name()}</div>
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
                    <div>{m.manage_users_label_last_name()}</div>
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

            <div class="flex flex-col">
                <div>{m.manage_users_label_roles()}</div>
                <ButtonGroup>
                    <MultiSelect class="rounded-l-lg" items={roleItems} bind:value={input.roleIds}
                                 disabled={edit === false}/>
                    {#if edit === true}
                        <Button onclick={() => input.roleIds = user.roleIds}
                                disabled={isEqual(input.roleIds,user.roleIds)}>
                            <UndoOutline/>
                        </Button>
                    {/if}
                </ButtonGroup>
            </div>

            <div class="flex gap-5">
                <div class="flex-1">
                    <div>{m.manage_users_label_created_at()}</div>
                    <Input type="text" value={(new Date(user.createdAt)).toLocaleString()} disabled/>
                </div>
                <div class="flex-1">
                    <div>{m.manage_users_label_updated_at()}</div>
                    <Input type="text" value={(new Date(user.updatedAt)).toLocaleString()} disabled/>
                </div>
            </div>
        </form>
    </Card>

    <Card class="flex lg:flex-col gap-5">
        <ButtonGroup>
            <Button color="alternative" class="w-full" onclick={clipboard}>
                <Tooltip trigger="click">
                    _copied_
                </Tooltip>
                <ShareNodesOutline/>
            </Button>
        </ButtonGroup>

        <ButtonGroup>
            {#if edit === true}
                <Button color="orange" onclick={save} disabled={saveLoading === true}>
                    {#if saveLoading === true}
                        <Spinner/>
                    {:else}
                        <FloppyDiskAltOutline/>
                    {/if}
                </Button>
                <Button onclick={cancel}>
                    <CloseOutline/>
                </Button>
            {:else}
                <Button color="orange" onclick={() => edit = true}>
                    <EditOutline/>
                </Button>
            {/if}
        </ButtonGroup>
    </Card>
</div>