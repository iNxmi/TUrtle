import {Exceptions} from "$lib/api";

export async function load({params}) {
    const exception = await getException(params.id);
    return {exception: exception};
}

async function getException(id) {
    const response = await Exceptions.get(id);
    return await response.json();
}