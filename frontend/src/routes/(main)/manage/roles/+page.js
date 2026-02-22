import {getPage} from "$lib/utils.js";

export async function load({url}) {
    const page = await getPage(url, "/roles")
    return {page: page};
}