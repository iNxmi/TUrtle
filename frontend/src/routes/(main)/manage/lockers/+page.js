import {getPage} from "$lib/utils.js";

export async function load({url}) {
    const page = await getPage(url, "/lockers")
    return {page: page};
}