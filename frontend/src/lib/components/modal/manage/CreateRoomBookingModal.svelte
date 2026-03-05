<script>
    import {
        Button,
        Datepicker,
        Heading,
        Hr,
        Input,
        Modal,
        MultiSelect,
        Select,
        Spinner,
        Textarea
    } from "flowbite-svelte";
    import {m} from "$lib/paraglide/messages.js";
    import Calendar from "$lib/components/Calendar.svelte"
    import TimePicker from "$lib/components/TimePicker.svelte";
    import {RoomBookings} from "$lib/api";
    import {invalidateAll} from "$app/navigation";

    let {
        open = $bindable(false),
        accessList = [],
        userList = [],
        statusList = []
    } = $props();

    let title = $state("");
    let description = $state("");

    let accessItems = $state(accessList.map((access) => ({
        value: access,
        name: access
    })));
    let access = $state("");

    let statusItems = $state(statusList.map((status) => ({
        value: status,
        name: status
    })));
    let status = $state("");

    let userItems = $state(userList.map((user) => ({
        value: user.id,
        name: user.username
    })));
    let userId = $state(0);
    let whitelistedUserIds = $state([]);

    const now = new Date();
    let start = $state(new Date(now));
    let end = $state(new Date(now));

    let loading = $state(false);
    let error = $state("");

    async function getEvents() {
        const response = await RoomBookings.getCollection();
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
        error = "";

        const payload = {
            title: title,
            description: description,
            start: start.toISOString(),
            end: end.toISOString(),
            access: access,
            status: status,
            whitelistedUserIds: whitelistedUserIds,
            userId: userId
        };

        loading = true;
        const response = await RoomBookings.create(payload);
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
            {m.modal_manage_create_room_booking_title()}
        </Heading>

        <Hr class="m-0 p-0"/>

        <div class="flex gap-5">
            <form class="shrink flex flex-col gap-5" onsubmit={submit}>
                <div>
                    <div>{m.modal_manage_create_room_booking_label_user()}</div>
                    <Select bind:value={userId} items={userItems} required/>
                </div>

                <div>
                    <div>{m.modal_manage_create_room_booking_label_title()}</div>
                    <Input bind:value={title} required/>
                </div>

                <div>
                    <div>{m.modal_manage_create_room_booking_label_description()}</div>
                    <Textarea bind:value={description} required/>
                </div>

                <div>
                    <div>{m.modal_manage_create_room_booking_label_start()}</div>
                    <div class="flex flex-col gap-1">
                        <Datepicker bind:value={start} required/>
                        <TimePicker bind:value={start} required/>
                    </div>
                </div>

                <div>
                    <div>{m.modal_manage_create_room_booking_label_end()}</div>
                    <div class="flex flex-col gap-1">
                        <Datepicker bind:value={end} required/>
                        <TimePicker bind:value={end} required/>
                    </div>
                </div>

                <div>
                    <div>{m.modal_manage_create_room_booking_label_access()}</div>
                    <Select bind:value={access} items={accessItems} required/>
                </div>

                <div>
                    <div>{m.modal_manage_create_room_booking_label_whitelist()}</div>
                    <MultiSelect disabled={access !== "WHITELIST"} bind:value={whitelistedUserIds} items={userItems}
                                 required/>
                </div>

                <div>
                    <div>{m.modal_manage_create_room_booking_label_status()}</div>
                    <Select bind:value={status} items={statusItems} required/>
                </div>

                {#if error.trim()}
                    <div class="text-red-400 text-justify">{error}</div>
                {/if}

                <div class="grow flex flex-col justify-end">
                    <Button class="w-full" type="submit">
                        {#if loading === true}
                            <Spinner size="5"/>
                        {:else}
                            {m.modal_manage_create_room_booking_button()}
                        {/if}
                    </Button>
                </div>
            </form>

            <Calendar sources={sources} class="grow"/>
        </div>
    </div>
</Modal>