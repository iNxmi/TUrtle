import request from "$lib/api/api";
import {itemsBookingsPath, itemsPath, usersPath} from '$lib/backend';

export async function load({params}) {

    const devicebookingResponse = await request(itemsBookingsPath + `/${params.id}`);

    if (devicebookingResponse.ok) {
        const deviceBooking = await devicebookingResponse.json();
        console.log(JSON.stringify(deviceBooking));
        const userResponse = await request(usersPath + `/${deviceBooking.userId}`);
        let user;
        let device;
        if (userResponse.ok) {
            user = await userResponse.json();

        } else {
            user = undefined;
        }

        const deviceResponse = await request(itemsPath + `/${deviceBooking.itemId}`);

        if (deviceResponse.ok) {
            device = await deviceResponse.json();
        } else {
            device = undefined;
        }

        return {deviceBooking, bookingUser: user, device};
    }
}