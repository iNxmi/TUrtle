import {Exceptions} from "$lib/api";

export async function load({params}) {
    const entity = await getException(params.id);
    return {entity: entity};
}

async function getException(id) {
    const response = await Exceptions.get(id);
    return await response.json();
}