import { userDatabase } from '$lib/server/DatabaseInitializer';
import {json} from '@sveltejs/kit'

export async function GET({params}) {
   return json(userDatabase.getUser(params.id));
}
