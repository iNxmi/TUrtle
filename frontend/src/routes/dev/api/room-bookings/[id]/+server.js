import {eventDatabase} from '$lib/server/DatabaseInitializer';

export async function DELETE({request}) {
    const id = await request.json();
    eventDatabase.deleteEventById(id.toString());

    return new Response(null, {status: 204});
}

export async function PATCH({request}) {
    const event = await request.json();
    eventDatabase.updateEventByID(event.id, event);
    return new Response(null, {status: 200});
}

export async function POST({request}) {
    const event = await request.json();
    eventDatabase.createEvent(event);
    return new Response(null, {status: 200});
}

