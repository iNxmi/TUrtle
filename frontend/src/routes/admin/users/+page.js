import request from "$lib/api/api"
import { checkAuthorization } from "$lib/utils"
export async function load({url}) {
    const page = url.searchParams.get("page") || "0";
    const filter = url.searchParams.get("filter") || "";
    const response = await request(`/users/page?page=${page}&filter=${filter}`);

    checkAuthorization(response, url.pathname);
    const payload = await response.json();

    return {page: payload};
}