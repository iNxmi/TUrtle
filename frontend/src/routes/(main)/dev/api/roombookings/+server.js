import { eventDatabase } from '$lib/server/DatabaseInitializer';
import { json } from '@sveltejs/kit';

export async function GET() {
	return json(eventDatabase.getEvents());
}

export async function DELETE({ request }) {
	const id = await request.json();
	eventDatabase.deleteEventById(id.toString());

	return new Response(null, { status: 204 });
}

export async function POST({ request }) {
	const event = await request.json();
	eventDatabase.createEvent(event);
	return new Response(null, { status: 200 });
}
