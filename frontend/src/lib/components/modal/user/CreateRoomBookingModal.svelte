<script>
    import {Button, Datepicker, Heading, Hr, Input, Modal, Select, Textarea} from "flowbite-svelte";
    import {m} from "$lib/paraglide/messages.js";
    import Calendar from "$lib/components/Calendar.svelte"
    import Timepicker from "$lib/components/Timepicker.svelte";
    import request from "$lib/api/api.js";
    import {invalidateAll} from "$app/navigation";

    let {
        open = $bindable(false)
    } = $props();

    let title = $state("");
    let description = $state("");

    const accessibilities = [{
        value: "LOCKED",
        name: "Locked"
    }, {
        value: "UNLOCKED",
        name: "Unlocked"
    }, {
        value: "WHITELIST",
        name: "Whitelist"
    }];
    let accessibility = $state("");

    const now = new Date();
    let start = $state(new Date(now));
    let end = $state(new Date(now));

    async function getEvents() {
        const response = await request("/api/room-bookings");
        const json = await response.json();

        return json.map(event => ({
            start: event.start,
            end: event.end
        }));
    }

    let sources = $derived([{
        events: [{
            title: title,
            start: start.toISOString(),
            end: end.toISOString()
        }]
    }, {
        events: async (_, successCallback, failureCallback) => {
            try {
                const events = await getEvents();
                successCallback(events);
            } catch (e) {
                failureCallback(e);
            }
        },
        color: "orange"
    }]);

    async function submit(event) {
        event.preventDefault();

        const payload = {
            title: title,
            description: description,
            start: start.toISOString(),
            end: end.toISOString(),
            accessibility: accessibility
        };

        const response = await request("/api/room-bookings", {
            method: "POST",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });

        if (!response.ok)
            return;

        await invalidateAll();
        open = false;
    }
</script>

<Modal form bind:open={open} outsideclose={false} size=xl>
    <div class="flex flex-col gap-5">

        <Heading tag="h3" class="text-center">
            {m.modal_user_create_room_booking_title()}
        </Heading>

        <Hr class="m-0 p-0"/>

        <div class="flex gap-5">
            <form class="shrink flex flex-col gap-5" onsubmit={submit}>
                <div>
                    <span>{m.modal_user_create_room_booking_label_title()}</span>
                    <Input bind:value={title} required/>
                </div>

                <div>
                    <span>{m.modal_user_create_room_booking_label_description()}</span>
                    <Textarea bind:value={description} required/>
                </div>

                <div>
                    <span>{m.modal_user_create_room_booking_label_start()}</span>
                    <div class="flex flex-col gap-1">
                        <Datepicker bind:value={start} required/>
                        <Timepicker bind:value={start} required/>
                    </div>
                </div>

                <div>
                    <span>{m.modal_user_create_room_booking_label_end()}</span>
                    <div class="flex flex-col gap-1">
                        <Datepicker bind:value={end} required/>
                        <Timepicker bind:value={end} required/>
                    </div>
                </div>

                <div>
                    <span>{m.modal_user_create_room_booking_label_accessibility()}</span>
                    <Select bind:value={accessibility} items={accessibilities} required/>
                </div>

                <div class="grow flex flex-col justify-end">
                    <Button class="w-full" type="submit">
                        {m.modal_user_create_room_booking_button()}
                    </Button>
                </div>
            </form>

            <Calendar sources={sources} class="grow"/>
        </div>
    </div>
</Modal>