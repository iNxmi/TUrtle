import request from "$lib/api/api";
import { checkAuthorization } from "$lib/utils";
import { rolesPath, lockersPath, permissionsPath} from '$lib/backend'

export async function load(){

    let roles;
    let lockers;
    let permissions;
    const rolesResponse = await request(rolesPath);

    checkAuthorization(rolesResponse, '/admin/settings');
    if(rolesResponse.ok){
        roles = await rolesResponse.json();
    }

    
    const lockerResponse = await request(lockersPath);

    if(lockerResponse.ok){
        lockers = await lockerResponse.json();
    }

    const permissionsResponse = await request(permissionsPath);

    if(permissionsResponse.ok)
        permissions = await permissionsResponse.json();
    return {page:roles, lockers, allPermissions: permissions};
}