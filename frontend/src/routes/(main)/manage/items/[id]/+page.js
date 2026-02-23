import request from "$lib/api/api.js";

export async function load({params}) {
    const deviceResponse = await request(`/api/items/${params.id}`);

    if (deviceResponse.ok) {
        const device = await deviceResponse.json();
        const categoryResponse = await request(`/api/item-categories/${device.category}`);

        if (categoryResponse.ok) {
            const category = await categoryResponse.json();
            return {device, category}
        }
    }
}