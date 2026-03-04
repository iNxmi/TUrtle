<script>
    import ItemDetailsPage from "$lib/components/ItemDetailsPage.svelte";

    let { data } = $props();
    let entity = $derived({
        endpoint: '/api/item-bookings',
        content: [
            {
                name: "User Name",
                value: data.user.username,
                href: `/manage/users/${data.itemBooking.userId}`
            },
            {
                name: "Item Name",
                value: data.item.name,
                href: `/manage/items/${data.itemBooking.itemId}`
            },[
                {
                    name: "Start",
                    value: new Date(data.itemBooking.start).toLocaleDateString(),
                    isEditable: true,
                    inputType: "datetime-local",
                    key: "start"
                },
                {
                    name: "End",
                    value: new Date(data.itemBooking.end).toLocaleDateString()
                }
            ],
            {
                name: "Status",
                value: data.itemBooking.status,
                isEditable: true,
                key: "status",
                enum: [
                    {name: "REQUESTED", value: "REQUESTED"},
                    {name: "APPROVED", value: "APPROVED"},
                    {name: "CANCELLED", value: "CANCELLED"},
                    {name: "COLLECTION_READY", value: "COLLECTION_READY"},
                    {name: "COLLECTED", value: "COLLECTED"},
                    {name: "RESERVATION_ENDED", value: "RESERVATION_ENDED"}
                ]
            },[
                {
                    name: "Created At",
                    value: data.itemBooking.createdAt
                },
                {
                    name: "Updated At",
                    value: data.itemBooking.updatedAt
                },
            ]
        ]
    })

</script>
<ItemDetailsPage title="_ITEM BOOKING_" {entity} />