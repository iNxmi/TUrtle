import request from "$lib/api/api";
import { devicesPath, deviceCategoriesPath} from '$lib/backend'

export async function load({params}){
    const deviceResponse = await request(devicesPath+`/${params.id}`);

    if(deviceResponse.ok){

        const device = await deviceResponse.json();
        const categoryResponse = await request(deviceCategoriesPath+`/${device.category}`);

        if(categoryResponse.ok){
            const category = await categoryResponse.json();

            return {device, category}
        }
    }
}