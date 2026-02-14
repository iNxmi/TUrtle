import request from "$lib/api/api";
import { checkAuthorization } from "$lib/utils";
import { rolesPath, lockersPath, permissionsPath, systemSettingsPath} from '$lib/backend'

export async function load(){

    let roles;
    let lockers;
    let permissions;
    let systemSettings;
    const rolesResponse = await request(rolesPath+'?pageNumber=0');

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
    
    const systemSettingsResponse = await request(systemSettingsPath);

    if(systemSettingsResponse.ok){
        systemSettings = await systemSettingsResponse.json();
    }
    return {page:roles, lockers, allPermissions: permissions, systemSettings};

    
}