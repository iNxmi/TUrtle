import request from "$lib/api/api";
import { deviceBookingsPath, usersPath, devicesPath } from '$lib/backend';

export async function load({params}){

    const devicebookingResponse = await request(deviceBookingsPath+`/${params.id}`);

    if(devicebookingResponse.ok){
        const deviceBooking = await devicebookingResponse.json();
        console.log(JSON.stringify(deviceBooking));
        const userResponse = await request(usersPath+`/${deviceBooking.userId}`);
        let user;
        let device;
        if(userResponse.ok){
            user = await userResponse.json();
            
        } else {
            user = undefined;
        }

        const deviceResponse = await request(devicesPath+`/${deviceBooking.deviceId}`);

        if(deviceResponse.ok){
            device = await deviceResponse.json();
        } else {
            device = undefined;
        }

        return {deviceBooking, bookingUser: user, device};
    }
}