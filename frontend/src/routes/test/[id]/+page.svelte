<script>
    import ItemDetailsPage from "$lib/components/ItemDetailsPage.svelte";

    let { data } = $props();

let itemBooking = $derived(data.itemBooking);

let itemBookingDetails = $derived( {
    endpoint:'/api/item-bookings',
    content: [
    {
        name: 'Item Name', 
        value: data.item.name,
        endpoint: `/items/${itemBooking.itemId}`
    },{
        name: 'Username',
        value: data.user.username,
        endpoint: `/users/${itemBooking.userId}`
    },[{
        name: 'Start',
        value: new Date(itemBooking.start).toLocaleString()
    },{
        name: 'End',
        value: new Date(itemBooking.end).toLocaleString(),
        isEditable: true,
        key: 'end',
        inputType: "datetime-local"
    }], {
        name: 'Status',
        value: itemBooking.status,
        isEditable:true,
        key: 'status',
        enum: [{
            name: 'REQUESTED', value: 'REQUESTED'},
            {name:'APPROVED', value: 'APPROVED'},
            {name: 'REJECTED', value: 'REJECTED'},
            {name: 'CANCELLED', value: 'CANCELLED'},
            {name: 'PENDING_COLLECTION', value: 'PENDING_COLLECTION'},
            {name: 'COLLECTED', value: 'COLLECTED'},
            {name: 'PENDING_RETURN', value: 'PENDING_RETURN'},
            {name: 'RETURNED', value: 'RETURNED'},
            {name: 'COMPLETED', value: 'COMPLETED'}
        ]
    }, 
    [ {   
        name: 'Created At',
        value: new Date(itemBooking.createdAt).toLocaleString()
    }, {
        
        name: 'Updated At',
        value: new Date(itemBooking.updatedAt).toLocaleString()
        }]
    ]});

</script>
<ItemDetailsPage title="_Item Booking_" entity={itemBookingDetails} />