<script>
    import Calendar from "$lib/components/Calendar.svelte";
    import Card from "$lib/components/Card.svelte";
    import {m} from "$lib/paraglide/messages";
    import {ButtonGroup, CloseButton, Heading, Hr, Input} from "flowbite-svelte";
    import {ArrowRightOutline, PlusOutline} from "flowbite-svelte-icons";
    import CreateRoomBookingModal from "$lib/components/modal/user/CreateRoomBookingModal.svelte";
    import CreateItemBookingModal from "$lib/components/modal/user/CreateItemBookingModal.svelte";
    import {goto} from "$app/navigation";
    import _ from "lodash";
    import { resolve } from "$app/paths";
    import Button from "$lib/components/Button.svelte";

    let {data} = $props();

    let users = $derived(data.users);

    let roomBookings = $derived(data.roomBookings);
    let roomBookingEvents = $derived(roomBookings.map((booking) => ({
        title: booking.title,
        start: booking.start,
        end: booking.end,
        href: `/user/room-bookings/${booking.id}`
    })));
    let roomAccess = $derived(data.roomAccess);

    let items = $derived(data.items);

    let itemMap = $derived(_.keyBy(items, "id"));
    let itemCategories = $derived(data.itemCategories);

    let itemBookings = $derived(data.itemBookings);
    let itemBookingEvents = $derived(itemBookings.map((booking) => ({
        title: itemMap[booking.itemId].name,
        start: booking.start,
        end: booking.end,
        href: `/user/item-bookings/${booking.id}`
    })));

    let sources = $derived([{
        id: "itemBookings",
        events: itemBookingEvents,
        color: "cyan"
    }, {
        id: "roomBookings",
        events: roomBookingEvents,
        color: "orange"
    }]);

    let notification = $state("WARNING: dont click on any emails, phishing is active");
    let dismissed = $state(false);

    let createItemBookingModal = $state(false);
    let createRoomBookingModal = $state(false);
</script>

{#if notification?.trim() && dismissed === false}
    <Card class="flex justify-between">
        <div class="text-red-400 flex flex-col justify-center">
            {notification}
        </div>
        <div class="flex flex-col">
            <CloseButton onclick={() => dismissed = true}/>
        </div>
    </Card>
{/if}

<div class="flex-1 flex flex-col 2xl:flex-row gap-5">
    <Card class="flex-1">
        <Calendar bind:sources={sources} onEventClicked={(info) => goto(resolve(info.event.extendedProps.href))}/>
    </Card>

    <div class="flex-1 flex flex-col gap-5">
        <Card class="flex-1 flex flex-col gap-5">
            <div class="flex justify-between">
                <Heading tag="h3">
                    {m.user_dashboard_title_current_item_bookings()}
                </Heading>
                <ButtonGroup>
                    <Button onclick={() => createItemBookingModal = true}>
                        <PlusOutline/>
                    </Button>
                </ButtonGroup>
            </div>

            <Hr class="m-0 p-0"/>

            {#if itemBookings.length > 0}
                <div class="flex flex-col gap-2">
                    {#each itemBookings as booking (booking.id)}
                        <ButtonGroup>
                            <Input value={`${itemMap[booking.itemId].name} (${booking.itemId})`} disabled/>
                            <Button onclick={() => goto(resolve(`/user/item-bookings/${booking.id}`))}>
                                <ArrowRightOutline/>
                            </Button>
                        </ButtonGroup>
                    {/each}
                </div>
            {:else}
                <div class="flex flex-col justify-center h-full">
                    <Heading tag="h4" class="text-center">
                        {m.user_dashboard_content_no_current_item_bookings()}
                    </Heading>
                </div>
            {/if}
        </Card>

        <Card class="flex-1 flex flex-col gap-5">
            <div class="flex justify-between">
                <Heading tag="h3">
                    {m.user_dashboard_title_current_room_bookings()}
                </Heading>
                {#if data.permissions.includes('MANAGE_ROOM_BOOKINGS')}
                    <ButtonGroup>
                        <Button onclick={() => createRoomBookingModal = true}>
                            <PlusOutline/>
                        </Button>
                    </ButtonGroup>
                {/if}
            </div>

            <Hr class="m-0 p-0"/>

            {#if roomBookings.length > 0}
                <div class="flex flex-col gap-2">
                    {#each roomBookings as booking (booking.id)}
                        <ButtonGroup>
                            <Input value={booking.title} disabled/>
                            <Button onclick={() => goto(resolve(`/user/room-bookings/${booking.id}`))}>
                                <ArrowRightOutline/>
                            </Button>
                        </ButtonGroup>
                    {/each}
                </div>
            {:else}
                <div class="flex flex-col justify-center h-full">
                    <Heading tag="h4" class="text-center">
                        {m.user_dashboard_content_no_current_room_bookings()}
                    </Heading>
                </div>
            {/if}
        </Card>
    </div>
</div>

{#if createItemBookingModal}
    <CreateItemBookingModal bind:open={createItemBookingModal} categoryList={itemCategories} itemList={items}/>
{/if}

{#if createRoomBookingModal}
    <CreateRoomBookingModal bind:open={createRoomBookingModal} accessList={roomAccess} userList={users}/>
{/if}
