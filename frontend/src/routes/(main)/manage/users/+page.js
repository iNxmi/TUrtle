import {getPage} from "$lib/utils.js";
import {Users} from "$lib/api";

export async function load({url}) {
    const page = await getPage(url, "/api/users")
    const status = await getStatus();

    return {
        page: page,
        status: status
    };
}

async function getStatus() {
    const response = await Users.status();
    return await response.json();
}