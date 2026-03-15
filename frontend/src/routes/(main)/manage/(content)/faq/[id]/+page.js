import {Faq} from "$lib/api";

export async function load({params}) {
    const entity = await getFAQ(params.id);
    return {entity: entity};
}

async function getFAQ(id) {
    const response = await Faq.get(id);
    return await response.json();
}