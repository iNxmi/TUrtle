import {SupportTicketUrgencies} from "$lib/api";

export async function load({params}) {
    const urgency = await getSupportTicketUrgency(params.id);
    return {urgency: urgency};
}

async function getSupportTicketUrgency(id) {
    const response = await SupportTicketUrgencies.get(id);
    return await response.json()
}