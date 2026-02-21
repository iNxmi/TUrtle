import request from "$lib/api/api.js";
import { checkAuthorization } from "$lib/utils.js";
import {itemCategoriesPath, itemsBookingsPath, itemsPath} from '$lib/backend.js';
/* import { json } from "@sveltejs/kit";
 */
export async function load({url}){

    let deviceCategories;
    let devices;
    const categories = await request(itemCategoriesPath);
    /* checkAuthorization(categories, url.pathname); */
    if(categories.ok){
        const categoryData = await categories.json();
        deviceCategories = categoryData;
       }
    
    const deviceResponse = await request(itemsPath);

    if(deviceResponse.ok){
        const deviceData = await deviceResponse.json();
        devices = deviceData;
    }

    const now = new Date(Date.now());
    now.setDate(now.getDate() + 14);
    const reservationResponse = await request(itemsBookingsPath+`?rsql=end<${now.toISOString()},status!=RETURNED`); //TODO
    if(reservationResponse.ok){
        const reservationData = await reservationResponse.json();
        return {deviceCategories, devices, reservations: reservationData};
    }
   
}