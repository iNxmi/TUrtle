import {getPage} from "$lib/utils.js";
import {SupportTicketCategories, SupportTicketUrgencies} from "$lib/api/index.js";

export async function load({url}) {
    const page = await getPage(url, "/api/support-tickets");
    const urgencies = await getSupportTicketUrgencies();
    const categories = await getSupportTicketCategories();

    return {
        page: page,
        categories: categories,
        urgencies: urgencies
    };
}

async function getSupportTicketCategories() {
    const response = await SupportTicketCategories.getCollection();
    return response.json();
}

async function getSupportTicketUrgencies() {
    const response = await SupportTicketUrgencies.getCollection();
    return response.json();
}