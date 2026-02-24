import {Faq} from "$lib/api";

export async function load({params}) {
    const faq = await getFAQ(params.id);
    return {faq: faq};
}

async function getFAQ(id) {
    const response = await Faq.get(id);
    return await response.json();
}