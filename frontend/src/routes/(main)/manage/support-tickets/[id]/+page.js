import request from "$lib/api/api.js";

export async function load({params}) {
    const ticket = await getSupportTicket(params.id);
    return {ticket: ticket};
}

async function getSupportTicket(id) {
    const response = await request(`/api/support-tickets/${id}`);
    return await response.json();
}