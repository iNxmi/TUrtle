import { json } from '@sveltejs/kit';
import { devicebookingDatabase } from '$lib/server/DatabaseInitializer';

export async function GET({params}){
    return json(devicebookingDatabase.getDevicebookingById(params.id));
}