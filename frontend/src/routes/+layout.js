import request from "$lib/api/api.js";
/* import { checkAuthorization } from "$lib/utils"; */
import { permissionsPath, authPath} from '$lib/backend'

export const prerender = false;
export const ssr = false;

export async function load() {

    const permissionsResponse = await request(permissionsPath);

  /*  await checkAuthorization(permissionsResponse); */
    let userPermissions = []
    if (permissionsResponse.status === 200){
        userPermissions = await permissionsResponse.json();
    }
        

    const profileResponse = await request(authPath+'/me');
    let user = {}
    if (profileResponse.status === 200)
        user = await profileResponse.json();

    return {userPermissions, user};
}