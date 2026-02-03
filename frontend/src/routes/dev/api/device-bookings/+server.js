import { json } from '@sveltejs/kit';
import { devicebookingDatabase  } from '$lib/server/DatabaseInitializer';

export async function GET(){
    return json(devicebookingDatabase.getDevicebookings());
}
export async function PATCH(){
    return json(true, {status:200});
}
export async function POST(){
    return json(true, {status:200, ok:true});
}