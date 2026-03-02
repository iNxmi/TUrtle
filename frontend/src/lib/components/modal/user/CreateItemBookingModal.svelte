<script>
    import {Button, Datepicker, Heading, Hr, Modal, Select, Spinner} from "flowbite-svelte";
    import {m} from "$lib/paraglide/messages.js";
    import Calendar from "$lib/components/Calendar.svelte"
    import Timepicker from "$lib/components/Timepicker.svelte";
    import {ItemBookings} from "$lib/api";
    import {invalidateAll} from "$app/navigation";

    let {
        open = $bindable(false),
        categoryList = [],
        itemList = []
    } = $props();

    let categoryItems = $derived(categoryList.map((category) => ({
        value: category.id,
        name: category.name
    })));
    let categoryId = $state(null);

    let itemItems = $derived(itemList.filter((item) => item.categoryId === categoryId).map((item) => ({
        value: item.id,
        name: item.name
    })));
    let itemId = $state(null);

    const now = new Date();
    let start = $state(new Date(now));
    let end = $state(new Date(now));

    let loading = $state(false);
    let error = $state("");

    async function getEvents(itemId) {
        if (itemId === null)
            return [];

        const response = await ItemBookings.getCollection({
            rsql: `item.id==${itemId}`
        });

        const json = await response.json();

        return json.map(event => ({
            start: event.start,
            end: event.end
        }));
    }

    let sources = $derived([{
        events: [{
            start: start.toISOString(),
            end: end.toISOString()
        }]
    }, {
        events: async (_, successCallback, failureCallback) => {
            try {
                const events = await getEvents(itemId);
                successCallback(events);
            } catch (e) {
                failureCallback(e);
            }
        },
        color: "orange"
    }])

    async function submit(event) {
        event.preventDefault();
        error = "";

        const payload = {
            itemId: itemId,
            start: start.toISOString(),
            end: end.toISOString()
        };

        loading = true;
        const response = await ItemBookings.create(payload);
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

<Modal form bind:open={open} outsideclose={false} size=xl>
    <div class="flex flex-col gap-5">
        <Heading tag="h3" class="text-center">
            {m.modal_user_create_item_booking_title()}
        </Heading>

        <Hr class="m-0 p-0"/>

        <div class="flex gap-5">
            <form class="shrink flex flex-col gap-5" onsubmit={submit}>
                <div>
                    <div>{m.modal_user_create_item_booking_label_category()}</div>
                    <Select bind:value={categoryId} items={categoryItems} onchange={() => itemId = null} required/>
                </div>

                <div>
                    <div>{m.modal_user_create_item_booking_label_item()}</div>
                    <Select disabled={categoryId === null} bind:value={itemId} items={itemItems} required/>
                </div>

                <div>
                    <div>{m.modal_user_create_item_booking_label_start()}</div>
                    <div class="flex flex-col gap-1">
                        <Datepicker disabled={itemId === null} bind:value={start}/>
                        <Timepicker disabled={itemId === null} bind:value={start}/>
                    </div>
                </div>

                <div>
                    <div>{m.modal_user_create_item_booking_label_end()}</div>
                    <div class="flex flex-col gap-1">
                        <Datepicker disabled={itemId === null} bind:value={end}/>
                        <Timepicker disabled={itemId === null} bind:value={end}/>
                    </div>
                </div>

                {#if error?.trim()}
                    <div class="text-red-400 text-justify">{error}</div>
                {/if}

                <div class="grow flex flex-col justify-end">
                    <Button class="w-full" type="submit">
                        {#if loading === true}
                            <Spinner size="5"/>
                        {:else}
                            {m.modal_user_create_item_booking_button()}
                        {/if}
                    </Button>
                </div>
            </form>

            <Calendar sources={sources} height={700} class="grow"/>
        </div>
    </div>
</Modal>