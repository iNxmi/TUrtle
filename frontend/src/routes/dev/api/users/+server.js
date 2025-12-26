import { userDatabase } from '$lib/server/DatabaseInitializer';
import { json } from '@sveltejs/kit';

export async function GET({url}) {
	return json(url.searchParams.get('pageNumber') ? {content: userDatabase.getUsers(), page:{size: 10, number: 0, totalElements:10, totalPages:1} }:userDatabase.getUsers());
}
