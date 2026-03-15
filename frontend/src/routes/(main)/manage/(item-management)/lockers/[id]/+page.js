import {Lockers} from "$lib/api";

export async function load({params}) {
    const entity = await getLocker(params.id)
    return {entity: entity};
}

async function getLocker(id) {
    const response = await Lockers.get(id);
    return await response.json();
}