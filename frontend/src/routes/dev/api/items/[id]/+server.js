import { json } from '@sveltejs/kit';
import { itemDatabase } from '$lib/server/DatabaseInitializer';

export async function GET({params}){

    return json(itemDatabase.getItemById(params.id));
}