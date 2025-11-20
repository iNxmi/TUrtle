import * as db from '$lib/server/UserDatabase';
import { json } from '@sveltejs/kit';

export async function GET({request}){
    return json({content: db.getUsers()});
}