import request from "$lib/api/api.js";
import { checkAuthorization } from "$lib/utils";
/* import { json } from "@sveltejs/kit";
 */
export async function load({url}){

    let deviceCategories;
    let devices;
    const categories = await request('/devicecategories');
    checkAuthorization(categories, url.pathname);
    if(categories.ok){
        const categoryData = await categories.json();
        deviceCategories = categoryData;
       }
    
    const deviceResponse = await request('/devices');

    if(deviceResponse.ok){
        const deviceData = await deviceResponse.json();
        devices = deviceData;
    }

    const reservationResponse = await request('/devicebookings?rsql='); //TODO
    if(reservationResponse.ok){
        const reservationData = await reservationResponse.json();
        return {deviceCategories, devices, reservations: reservationData.content};
    }
   
}