import {getPage} from "$lib/utils.js";

export async function load({url}) {
    const page = await getPage(url, "/item-categories")
    return {page: page};
}