import {SupportTicketUrgencies} from "$lib/api";

export async function load({params}) {
    const category = await getSupportTicketUrgency(params.id);
    return {category: category};
}

async function getSupportTicketUrgency(id) {
    const response = await SupportTicketUrgencies.get(id);
    return await response.json()
}