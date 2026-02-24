import request from "$lib/api/api.js";

export async function load({params}) {
    const locker = await getLocker(params.id)
    return {locker: locker};
}

async function getLocker(id) {
    const response = await request(`/api/lockers/${id}`);
    return await response.json();
}