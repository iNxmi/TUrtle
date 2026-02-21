import request from "$lib/api/api";
import { itemCategoriesPath } from "$lib/backend";

export async function load({params}){

    const deviceCategoryResponse = await request(itemCategoriesPath+`/${params.id}`);

    if(deviceCategoryResponse.ok){

        const deviceCategory = await deviceCategoryResponse.json();

        return {deviceCategory};
    }
}