import {SupportTicketCategories} from "$lib/api";

export async function load({params}) {
    const category = await getSupportTicketCategory(params.id);
    return {category: category};
}

async function getSupportTicketCategory(id) {
    const response = await SupportTicketCategories.get(id);
    return await response.json()
}