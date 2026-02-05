import {json} from '@sveltejs/kit';
export async function GET(){

    const testDeviceCategories = [{
        id: "0", name:"Laptop"
    },
    {
        id: "1", name:"Tablet"
    }];  

    return json({content: testDeviceCategories, page: {
        size: 20,
        number: 0,
        totalElements: 4,
        totalPages: 1
    }});
}