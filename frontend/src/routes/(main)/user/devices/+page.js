import request from "$lib/api/api.js";
import { checkAuthorization } from "$lib/utils";
import {deviceCategoriesPath, deviceBookingsPath, devicesPath} from '$lib/backend';
/* import { json } from "@sveltejs/kit";
 */
export async function load({url}){

    let deviceCategories;
    let devices;
    const categories = await request(deviceCategoriesPath);
    checkAuthorization(categories, url.pathname);
    if(categories.ok){
        const categoryData = await categories.json();
        deviceCategories = categoryData;
       }
    
    const deviceResponse = await request(devicesPath);

    if(deviceResponse.ok){
        const deviceData = await deviceResponse.json();
        devices = deviceData;
    }

    const now = new Date(Date.now());
    now.setDate(now.getDate() + 14);
    const reservationResponse = await request(deviceBookingsPath+`?rsql=end<${now.toISOString()},status!='ITEM_RETURNED'`); //TODO
    if(reservationResponse.ok){
        const reservationData = await reservationResponse.json();
        return {deviceCategories, devices, reservations: reservationData};
    }
   
}