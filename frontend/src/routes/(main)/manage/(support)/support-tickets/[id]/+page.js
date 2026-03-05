import {SupportTicketCategories, SupportTickets, SupportTicketUrgencies} from "$lib/api";

export async function load({params}) {
    const ticket = await getSupportTicket(params.id);
    const urgencies = await getSupportTicketUrgencies();
    const categories = await getSupportTicketCategories();
    const statuses = await getSupportTicketStatuses();

    return {
        ticket: ticket,
        urgencies: urgencies,
        categories: categories,
        statuses: statuses
    };
}

async function getSupportTicket(id) {
    const response = await SupportTickets.get(id);
    return await response.json();
}

async function getSupportTicketCategories() {
    const response = await SupportTicketCategories.getCollection();
    return response.json();
}

async function getSupportTicketUrgencies() {
    const response = await SupportTicketUrgencies.getCollection();
    return response.json();
}

async function getSupportTicketStatuses() {
    const response = await SupportTickets.status();
    return response.json();
}