import {paraglideMiddleware} from '$lib/paraglide/server';
import {dev} from "$app/environment";
import {error} from "@sveltejs/kit";

export async function handle({event, resolve}) {
    if (!dev && event.url.pathname.startsWith("/dev"))
        throw error(404, "Not Found")

    return paraglideMiddleware(event.request, ({request, locale}) => {
        event.request = request;

        return resolve(event, {
            transformPageChunk: ({html}) => html.replace('%lang%', locale)
        });
    });
}
