<script>
    import {m} from "$lib/paraglide/messages.js";
    import {Button, ButtonGroup, Input, Spinner, Tooltip} from "flowbite-svelte";
    import Card from "$lib/components/Card.svelte";
    import {
        CloseOutline,
        EditOutline,
        FloppyDiskAltOutline,
        ShareNodesOutline,
        TrashBinOutline
    } from "flowbite-svelte-icons";
    import Calendar from "$lib/components/Calendar.svelte";

    let {data} = $props();
    let booking = $derived(data.booking);

    let sources = [];
</script>

<div class="flex-1 flex flex-col 2xl:flex-row gap-5">
    <Card class="flex-1">
        <Calendar sources={sources}/>
    </Card>

    <div class="flex-1 flex gap-5">
        <Card class="grow flex flex-col gap-5">
            <div>
                <span>{m.manage_item_bookings_label_id()}</span>
                <Input type="text" value={booking.id} disabled/>
            </div>

            <div>
                <span>{m.manage_item_bookings_label_item_id()}</span>
                <Input type="text" value={booking.itemId} disabled/>
            </div>

            <div>
                <span>{m.manage_item_bookings_label_user_id()}</span>
                <Input type="text" value={booking.userId} disabled/>
            </div>

            <div class="flex gap-5">
                <div class="flex-1">
                    <span>{m.manage_item_bookings_label_start()}</span>
                    <Input type="text" value={(new Date(booking.start)).toLocaleString()} disabled/>
                </div>
                <div class="flex-1">
                    <span>{m.manage_item_bookings_label_end()}</span>
                    <Input type="text" value={(new Date(booking.end)).toLocaleString()} disabled/>
                </div>
            </div>

            <div>
                <span>{m.manage_item_bookings_label_status()}</span>
                <Input type="text" value={booking.status} disabled/>
            </div>

            <div class="flex gap-5">
                <div class="flex-1">
                    <span>{m.manage_item_bookings_label_created_at()}</span>
                    <Input type="text" value={(new Date(booking.createdAt)).toLocaleString()} disabled/>
                </div>
                <div class="flex-1">
                    <span>{m.manage_item_bookings_label_updated_at()}</span>
                    <Input type="text" value={(new Date(booking.updatedAt)).toLocaleString()} disabled/>
                </div>
            </div>
        </Card>

        <Card class="flex lg:flex-col gap-5 justify-between">
            <div class="flex lg:flex-col gap-5">
                <ButtonGroup>
                    <Button color="alternative" class="w-full">
                        <Tooltip trigger="click">
                            _copied_
                        </Tooltip>
                        <ShareNodesOutline/>
                    </Button>
                </ButtonGroup>

                <ButtonGroup>
                    {#if false}
                        <Button color="orange">
                            {#if false}
                                <Spinner/>
                            {:else}
                                <FloppyDiskAltOutline/>
                            {/if}
                        </Button>
                        <Button>
                            <CloseOutline/>
                        </Button>
                    {:else}
                        <Button color="orange">
                            <EditOutline/>
                        </Button>
                    {/if}
                </ButtonGroup>
            </div>

            <ButtonGroup>
                <Button color="red" class="w-full">
                    <TrashBinOutline/>
                </Button>
            </ButtonGroup>
        </Card>
    </div>
</div>