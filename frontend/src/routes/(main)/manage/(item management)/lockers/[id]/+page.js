import {Lockers} from "$lib/api";

export async function load({params}) {
    const locker = await getLocker(params.id)
    return {locker: locker};
}

async function getLocker(id) {
    const response = await Lockers.get(id);
    return await response.json();
}