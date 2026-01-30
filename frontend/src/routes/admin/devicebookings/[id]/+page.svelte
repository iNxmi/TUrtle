<script>
    import ItemDetailsPage from "$lib/components/ItemDetailsPage.svelte";
   
    let { data } = $props();
    
    let user = $derived(data.bookingUser);
    let device = $derived(data.device);
    let booking = $derived(data.deviceBooking);

    let content = $derived([
        {
            name: '_User_',
            value: user.username,
            endpoint: `/users/${user.id}`
        },
        {
            name: '_Device_',
            value: device.name,
            endpoint: `/devices/${device.id}`
        },
        {
            name: '_Start_',
            value: booking.start,
        },
        {
            name: '_End_',
            value: 'booking.end'
        },
        {
            name: '_Status_',
            value: booking.status
        },
        {
            name: '_Created At_',
            value: booking.createdAt
        }
    ]);
</script>
<ItemDetailsPage title='_Devicebookings_' itemId={booking.id} {content} />

