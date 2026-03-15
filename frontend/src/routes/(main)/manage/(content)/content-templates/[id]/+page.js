import {ContentTemplates} from "$lib/api";

export async function load({params}) {
    const entity = await getContentTemplate(params.id);
    const types = await getTypes();

    return {
        entity: entity,
        types: types
    };
}

async function getTypes() {
    const response = await ContentTemplates.type()
    return await response.json();
}

async function getContentTemplate(id) {
    const response = await ContentTemplates.get(id);
    return await response.json();
}