import { userDatabase } from '$lib/server/DatabaseInitializer';
import { json } from '@sveltejs/kit';

export async function GET() {
	return json(userDatabase.getUsers());
}
