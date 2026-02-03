import request from "$lib/api/api";
import { deviceCategoriesPath } from "$lib/backend";

export async function load({params}){

    const deviceCategoryResponse = await request(deviceCategoriesPath+`/${params.id}`);

    if(deviceCategoryResponse.ok){

        const deviceCategory = await deviceCategoryResponse.json();

        return {deviceCategory};
    }
}