import request from "$lib/api/api";

export async function load({params}){
    const deviceResponse = await request(`/devices/${params.id}`);

    if(deviceResponse.ok){

        const device = await deviceResponse.json();
        const categoryResponse = await request(`/devicecategories/${device.category}`);

        if(categoryResponse.ok){
            const category = await categoryResponse.json();

            return {device, category}
        }
    }
}