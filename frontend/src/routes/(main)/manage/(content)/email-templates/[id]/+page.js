import {EmailTemplates} from "$lib/api";

export async function load({params}) {
    const template = await getEmailTemplate(params.id);
    const types = await getType();

    return {
        template: template,
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