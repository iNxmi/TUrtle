import {json} from '@sveltejs/kit';
export async function GET(){

    const testDeviceCategories = [{
        id: "0", name:"Laptop"
    },
    {
        id: "1", name:"Tablet"
    }];  

    return json(testDeviceCategories);
}