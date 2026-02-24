import {SupportTickets} from "$lib/api";

export async function load({params}) {
    const ticket = await getSupportTicket(params.id);
    return {ticket: ticket};
}

async function getSupportTicket(id) {
    const response = await SupportTickets.get(id);
    return await response.json();
}