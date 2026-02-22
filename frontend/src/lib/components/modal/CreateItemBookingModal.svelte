<script>
    import {Button, Datepicker, Heading, Hr, Label, Modal, Select} from "flowbite-svelte";
    import {m} from "$lib/paraglide/messages";
    import Calendar from "$lib/components/Calendar.svelte"
    import Timepicker from "$lib/components/Timepicker.svelte";
    import request from "$lib/api/api.js";

    let {
        open = $bindable(false),
        categories = [],
        items = []
    } = $props();

    let category = $state(null);
    let item = $state(null);

    const now = new Date();
    let start = $state(new Date(now));
    let end = $state(new Date(now));

    let event = $derived({
        title: "Item Booking",
        start: start.toISOString(),
        end: end.toISOString()
    })

    async function getEvents(itemId) {
        const response = await request(`/item-bookings?rsql=item.id==${itemId}`);
        const json = await response.json();

        return json.map(item => ({
            title: "test",
            start: item.start,
            end: item.end
        }));
    }

    let sources = $derived([{
        events: [event]
    }, {
        events: async (_, successCallback, failureCallback) => {
            try {
                const events = await getEvents(item);
                successCallback(events);
            } catch (e) {
                failureCallback(e);
            }
        },
        color: "orange"
    }])

    async function submit(event) {
        event.preventDefault();

        const payload = {
            itemId: item,
            start: start.toISOString(),
            end: end.toISOString()
        };

        const response = await request("/item-bookings", {
            method: "POST",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });

        open = false;
    }
</script>

<Modal form bind:open={open} outsideclose={false} size=xl>
    <div class="flex flex-col gap-5">

        <Heading tag="h3" class="text-center">
            {m.modal_create_item_booking_title()}
        </Heading>

        <Hr class="m-0 p-0"/>

        <div class="flex gap-5">
            <form class="shrink flex flex-col gap-5" onsubmit={submit}>
                <Label>
                    <span>{m.modal_create_item_booking_label_category()}</span>
                    <Select bind:value={category} items={categories} required/>
                </Label>

                <Label>
                    <span>{m.modal_create_item_booking_label_item()}</span>
                    <Select disabled={category === null} bind:value={item} items={items} required/>
                </Label>

                <Label>
                    <span>{m.modal_create_item_booking_label_start()}</span>
                    <div class="flex flex-col gap-1">
                        <Datepicker disabled={item === null} bind:value={start}/>
                        <Timepicker disabled={item === null} bind:value={start}/>
                    </div>
                </Label>

                <Label>
                    <span>{m.modal_create_item_booking_label_end()}</span>
                    <div class="flex flex-col gap-1">
                        <Datepicker disabled={item === null} bind:value={end}/>
                        <Timepicker disabled={item === null} bind:value={end}/>
                    </div>
                </Label>

                <div class="grow flex flex-col justify-end">
                    <Button class="w-full" type="submit">
                        {m.modal_create_item_booking_button()}
                    </Button>
                </div>
            </form>

            <Calendar sources={sources} class="grow"/>
        </div>
    </div>
</Modal>