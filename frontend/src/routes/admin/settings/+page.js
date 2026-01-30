import request from "$lib/api/api";
import { checkAuthorization } from "$lib/utils";

export async function load(){

    let roles;
    let lockers;
    let permissions;
    const rolesResponse = await request('/roles');

    checkAuthorization(rolesResponse, '/admin/settings');
    if(rolesResponse.ok){
        roles = await rolesResponse.json();
    }

    
    const lockerResponse = await request('/lockers');

    if(lockerResponse.ok){
        lockers = await lockerResponse.json();
    }

    const permissionsResponse = await request('/permissions');

    if(permissionsResponse.ok)
        permissions = await permissionsResponse.json();
    return {page:roles, lockers, allPermissions: permissions};
}