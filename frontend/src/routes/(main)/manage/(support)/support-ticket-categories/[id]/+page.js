import {SupportTicketCategories} from "$lib/api";

export async function load({params}) {
    const entity = await getSupportTicketCategory(params.id);
    return {entity: entity};
}

async function getSupportTicketCategory(id) {
    const response = await SupportTicketCategories.get(id);
    return await response.json()
}