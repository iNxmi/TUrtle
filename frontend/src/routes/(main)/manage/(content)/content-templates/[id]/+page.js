import {ContentTemplates} from "$lib/api";

export async function load({params}) {
    const template = await getContentTemplate(params.id);

    return {
        template: template
    };
}

async function getContentTemplate(id) {
    const response = await ContentTemplates.get(id);
    return await response.json();
}