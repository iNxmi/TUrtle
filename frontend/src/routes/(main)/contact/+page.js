import {Content, SupportTicketCategories, SupportTicketUrgencies} from "$lib/api";

export async function load() {
    const content = await getContent();
    const categories = await getSupportTicketCategories();
    const urgencies = await getSupportTicketUrgencies();

    return {
        content: content,
        categories: categories,
        urgencies: urgencies
    };
}

async function getContent() {
    const response = await Content.contact();
    return await response.text();
}

async function getSupportTicketCategories() {
    const response = await SupportTicketCategories.getCollection();
    return response.json();
}

async function getSupportTicketUrgencies() {
    const response = await SupportTicketUrgencies.getCollection();
    return response.json();
}