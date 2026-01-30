import request from "$lib/api/api";
import { checkAuthorization } from "$lib/utils";

export async function load({ url, params}){

    const actionResponse = await request(`/auditlogs/${params.id}`);

    checkAuthorization(actionResponse, url.pathname);

    if(actionResponse.ok){

        const action = await actionResponse.json();

        return {action};
    }
}