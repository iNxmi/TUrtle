import {Content} from "$lib/api";

export async function load() {
    const response = await Content.contact();
    const payload = await response.text();

    return {content: payload};
}