import request from "$lib/api/api.js";

export async function load({params}) {
    const faq = await getFAQ(params.id);
    return {faq: faq};
}

async function getFAQ(id) {
    const response = await request(`/api/faq/${id}`);
    return await response.json();
}