import {Content} from "$lib/api";

export async function load() {
    const response = await Content.tos();
    const payload = await response.text();

    return {content: payload};
}