import request from "$lib/api/api.js";
/* import { checkAuthorization } from "$lib/utils"; */
import { permissionsPath, authPath} from '$lib/backend'

export const prerender = false;
export const ssr = false;

export async function load({url}) {

    let user = {} 
    let userPermissions = []
    if(url.pathname === '/auth/login'){
        return {userPermissions, user};
    }
    const permissionsResponse = await request(permissionsPath);

   
    if (permissionsResponse.status === 200){
        userPermissions = await permissionsResponse.json();
    }   
    const profileResponse = await request(authPath+'/me');

    if (profileResponse.status === 200)
        user = await profileResponse.json();

    return {userPermissions, user};
}