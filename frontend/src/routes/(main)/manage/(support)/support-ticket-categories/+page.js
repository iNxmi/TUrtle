import {getPage} from "$lib/utils.js";

export async function load({url}) {
    const page = await getPage(url, "/api/support-ticket-categories")
    return {page: page};
}