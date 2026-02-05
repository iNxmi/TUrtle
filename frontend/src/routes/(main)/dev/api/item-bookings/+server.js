import { json } from '@sveltejs/kit';
import { devicebookingDatabase  } from '$lib/server/DatabaseInitializer';

export async function GET(){
    return json({content: devicebookingDatabase.getDevicebookings(), page: {
        size: 20,
        number: 0,
        totalElements: 4,
        totalPages: 1
    }});
}
export async function PATCH(){
    return json(true, {status:200});
}
export async function POST(){
    return json(true, {status:200, ok:true});
}