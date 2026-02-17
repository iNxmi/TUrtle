import {json} from '@sveltejs/kit';
import { itemDatabase } from '$lib/server/DatabaseInitializer';
export async function GET({url}){

    const devices  = itemDatabase.getItems();

    if(url.searchParams.pageNumber){
        return json({content: devices, page: {
            size: 20,
            number: 0,
            totalElements: 4,
            totalPages: 1
        }});
    } else {
        return json(devices);
    }
}