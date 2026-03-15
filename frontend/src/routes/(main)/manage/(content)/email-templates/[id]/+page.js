import {EmailTemplates} from "$lib/api";

export async function load({params}) {
    const entity = await getEmailTemplate(params.id);
    const types = await getType();

    return {
        entity: entity,
        types: types
    };
}

async function getEmailTemplate(id) {
    const response = await EmailTemplates.get(id);
    return await response.json();
}

async function getType() {
    const response = await EmailTemplates.type();
    return await response.json();
}