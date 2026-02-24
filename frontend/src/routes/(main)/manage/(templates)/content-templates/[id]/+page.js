import {ContentTemplates} from "$lib/api";

export async function load({params}) {
    const response = await ContentTemplates.get(params.id);
    const template = await response.json();

    return {template: template};
}