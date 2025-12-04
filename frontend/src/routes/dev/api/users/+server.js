import { userDatabase } from '$lib/server/DatabaseInitializer';
import { json } from '@sveltejs/kit';

export async function GET({ request }) {
	return json({ content: userDatabase.getUsers() });
}
