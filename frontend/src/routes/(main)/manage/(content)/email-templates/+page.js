import { getPage } from "$lib/utils.js";
import { EmailTemplates } from "$lib/api";

export async function load({ url }) {
    const page = await getPage(url, "/api/email-templates")
    const type = await getType();

    return {
        page: page,
        type: type
    };
}

async function getType() {
    const response = await EmailTemplates.type();
    return await response.json();
}