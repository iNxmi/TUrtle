import request from "$lib/api/api.js";
import {itemCategoriesPath, itemsPath} from '$lib/backend.js'

export async function load({params}) {
    const deviceResponse = await request(itemsPath + `/${params.id}`);

    if (deviceResponse.ok) {

        const device = await deviceResponse.json();
        const categoryResponse = await request(itemCategoriesPath + `/${device.category}`);

        if (categoryResponse.ok) {
            const category = await categoryResponse.json();

            return {device, category}
        }
    }
}