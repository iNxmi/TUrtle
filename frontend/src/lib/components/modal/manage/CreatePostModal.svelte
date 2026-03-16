<script>
    import {Button, Heading, Hr, Input, Modal, Spinner, Textarea} from "flowbite-svelte";
    import {m} from "$lib/paraglide/messages.js";
    import {Posts} from "$lib/api";
    import {invalidateAll} from "$app/navigation";

    let {
        open = $bindable(false)
    } = $props();

    let name = $state("");
    let description = $state("");
    let title = $state("");
    let content = $state("");
    let enabled = $state(true);

    let loading = $state(false);
    let error = $state("");

    async function submit(event) {
        event.preventDefault();
        error = "";

        const payload = {
            name: name,
            description: description,
            title: title,
            content: content,
            enabled: enabled
        };

        loading = true;
        const response = await Posts.create(payload);
        loading = false;

        if (!response.ok) {
            const json = await response.json();
            error = json.message;
            return;
        }

        await invalidateAll();
        open = false;
    }
</script>

<Modal form bind:open={open} outsideclose={false}>
    <form onsubmit={submit} class="flex flex-col gap-5">
        <Heading tag="h3" class="text-center">
            {m.modal_manage_create_post_title()}
        </Heading>

        <Hr class="m-0 p-0"/>

        <div>
            <div>{m.modal_manage_create_post_label_name()}</div>
            <Input bind:value={name} required/>
        </div>

        <div>
            <div>{m.modal_manage_create_post_label_description()}</div>
            <Textarea bind:value={description} required/>
        </div>

        <div>
            <div>{m.modal_manage_create_post_label_title()}</div>
            <Input bind:value={title} required/>
        </div>

        <div>
            <div>{m.modal_manage_create_post_label_content()}</div>
            <Textarea class="w-full" bind:value={content} required/>
        </div>

        <div>
            <div>{m.modal_manage_create_post_label_enabled()}</div>
            <Input bind:value={enabled} required/>
        </div>

        {#if error?.trim()}
            <div class="text-red-400 text-justify">{error}</div>
        {/if}

        <Button type="submit">
            {#if loading === true}
                <Spinner size="5"/>
            {:else}
                {m.modal_manage_create_post_button()}
            {/if}
        </Button>
    </form>
</Modal>
