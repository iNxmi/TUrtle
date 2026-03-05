<script>
    import {Button, Heading, Hr, Input, Modal, Select, Spinner, Textarea} from "flowbite-svelte";
    import {m} from "$lib/paraglide/messages.js";
    import {EmailTemplates} from "$lib/api";
    import {invalidateAll} from "$app/navigation";

    let {
        open = $bindable(false),
        typeList = []
    } = $props();

    let typeItems = $derived(typeList.map((type) => ({
        value: type,
        name: type
    })));

    let name = $state("");
    let description = $state("");
    let subject = $state("");
    let content = $state("");
    let type = $state(null);

    let loading = $state(false);
    let error = $state("");

    async function submit(event) {
        event.preventDefault();
        error = "";

        const payload = {
            name: name,
            description: description,
            subject: subject,
            content: content,
            type: type || null
        };

        loading = true;
        const response = await EmailTemplates.create(payload);
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
            {m.modal_manage_create_email_template_title()}
        </Heading>

        <Hr class="m-0 p-0"/>

        <div>
            <div>{m.modal_manage_create_email_template_label_name()}</div>
            <Input bind:value={name} required/>
        </div>

        <div>
            <div>{m.modal_manage_create_email_template_label_type()}</div>
            <Select items={typeItems} bind:value={type} clearable/>
        </div>

        <div>
            <div>{m.modal_manage_create_email_template_label_description()}</div>
            <Textarea class="w-full" bind:value={description} required/>
        </div>

        <div>
            <div>{m.modal_manage_create_email_template_label_subject()}</div>
            <Input bind:value={subject} required/>
        </div>

        <div>
            <div>{m.modal_manage_create_email_template_label_content()}</div>
            <Textarea class="w-full" bind:value={content} rows={10} required/>
        </div>

        {#if error?.trim()}
            <div class="text-red-400 text-justify">{error}</div>
        {/if}

        <Button type="submit">
            {#if loading === true}
                <Spinner size="5"/>
            {:else}
                {m.modal_manage_create_email_template_button()}
            {/if}
        </Button>
    </form>
</Modal>
