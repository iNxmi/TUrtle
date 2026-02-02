import request from "$lib/api/api.js";
/* import { checkAuthorization } from "$lib/utils"; */
import { permissionsPath, authPath} from '$lib/backend'

export const prerender = false;
export const ssr = false;

export async function load() {

    const permissionsResponse = await request(permissionsPath);

     const itemsPublic = [
        "VIEW__SIDEBAR_CATEGORY__PUBLIC",

        "VIEW__SIDEBAR_ITEM__HOME",
    
        "VIEW__SIDEBAR_ITEM__LOGIN",
    
        "VIEW__SIDEBAR_ITEM__REGISTER",

        "VIEW__SIDEBAR_ITEM__SUPPORT",

        "VIEW__SIDEBAR_ITEM__FAQ",

        "VIEW__SIDEBAR_ITEM__ABOUT"
     ];
  /*  await checkAuthorization(permissionsResponse); */
    let permissions = {}
    if (permissionsResponse.status === 200){
        permissions = await permissionsResponse.json();
    } else {
        permissions = itemsPublic;
    }
        

    const profileResponse = await request(authPath+'/me');
    let user = {}
    if (profileResponse.status === 200)
        user = await profileResponse.json();

    return {permissions, user};
}