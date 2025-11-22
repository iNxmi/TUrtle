import { eventDatabase } from '$lib/server/DatabaseInitializer';
import { json } from '@sveltejs/kit';

export async function GET({ request }) {
	return json(eventDatabase.getEvents());
}

export async function DELETE({ request }) {
	const id = await request.json();
	eventDatabase.deleteEventById(id);

	return new Response(null, { status: 204 });
}
