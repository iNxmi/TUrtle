<script>
    import {Button, Heading, Hr, Input, Modal, Spinner, Textarea, Select} from "flowbite-svelte";
    import {m} from "$lib/paraglide/messages.js";
    import {StatisticQueries} from "$lib/api";
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
    let query = $state("");
    let type = $state("");

    let loading = $state(false);
    let error = $state("");

    async function submit(event) {
        event.preventDefault();
        error = "";

        const payload = {
            name: name,
            description: description,
            query: query,
            type: type,
        };

        loading = true;
        const response = await StatisticQueries.create(payload);
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
            {m.modal_manage_create_statistic_query_title()}
        </Heading>

        <Hr class="m-0 p-0"/>

        <div>
            <div>{m.modal_manage_create_statistic_query_label_name()}</div>
            <Input bind:value={name} required/>
        </div>

        <div>
            <div>{m.modal_manage_create_statistic_query_label_description()}</div>
            <Input bind:value={description} required/>
        </div>

        <div>
            <div>{m.modal_manage_create_statistic_query_label_query()}</div>
            <Textarea class="w-full" bind:value={query} required/>
        </div>

        <div>
            <div>{m.modal_manage_create_statistic_query_label_type()}</div>
            <Select bind:value={type} items={typeItems} required/>
        </div>

        {#if error?.trim()}
            <div class="text-red-400 text-justify">{error}</div>
        {/if}

        <Button type="submit">
            {#if loading === true}
                <Spinner size="5"/>
            {:else}
                {m.modal_manage_create_statistic_query_button()}
            {/if}
        </Button>
    </form>
</Modal>
