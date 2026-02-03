import request from "$lib/api/api";
import { checkAuthorization } from "$lib/utils";
import { auditLogsPath } from '$lib/backend';

export async function load({ url, params}){

    const actionResponse = await request(auditLogsPath+`/${params.id}`);

    checkAuthorization(actionResponse, url.pathname);

    if(actionResponse.ok){

        const action = await actionResponse.json();

        return {action};
    }
}