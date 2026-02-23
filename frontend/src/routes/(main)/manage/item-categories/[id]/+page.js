import request from "$lib/api/api.js";

export async function load({params}) {

    const deviceCategoryResponse = await request(`/api/item-categories/${params.id}`);

    if (deviceCategoryResponse.ok) {
        const deviceCategory = await deviceCategoryResponse.json();
        return {deviceCategory};
    }
}