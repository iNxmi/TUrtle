import {getPage} from "$lib/utils.js";
import {ContentTemplates} from "$lib/api/index.js";

export async function load({url}) {
    const page = await getPage(url, "/api/content-templates")
    const types = await getTypes();

    return {
        page: page,
        types: types
    };
}

async function getTypes() {
    const response = await ContentTemplates.type()
    return await response.json();
}