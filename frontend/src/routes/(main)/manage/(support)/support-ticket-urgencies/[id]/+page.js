import {SupportTicketUrgencies} from "$lib/api";

export async function load({params}) {
    const entity = await getSupportTicketUrgency(params.id);
    return {entity: entity};
}

async function getSupportTicketUrgency(id) {
    const response = await SupportTicketUrgencies.get(id);
    return await response.json()
}