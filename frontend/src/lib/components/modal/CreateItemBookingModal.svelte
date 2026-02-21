<script>
    import {Button, Datepicker, Heading, Label, Modal, Select} from "flowbite-svelte";
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

<Modal form bind:open={open} outsideclose={false}>
    <form class="flex flex-col gap-5" onsubmit={submit}>
        <Heading tag="h3" class="text-center">
            _Create Item Booking_
        </Heading>

        <Label>
            <span>_Category_</span>
            <Select bind:value={category} items={categories} required/>
        </Label>

        <Label>
            <span>_Item_</span>
            <Select disabled={category === null} bind:value={item} items={items} required/>
        </Label>

        <div class="flex gap-10 justify-between">
            <Label class="flex-1">
                <span>_Start_</span>
                <div class="flex flex-col gap-1">
                    <Datepicker disabled={item === null} bind:value={start}/>
                    <Timepicker disabled={item === null} bind:value={start}/>
                </div>
            </Label>

            <Label class="flex-1">
                <span>_End_</span>
                <div class="flex flex-col gap-1">
                    <Datepicker disabled={item === null} bind:value={end}/>
                    <Timepicker disabled={item === null} bind:value={end}/>
                </div>
            </Label>
        </div>

        <Button type="submit">_Submit_</Button>
    </form>
</Modal>