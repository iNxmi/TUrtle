import * as db from '$lib/server/UserDatabase.js';

export async function DELETE({ params }) {
	await db.deleteUser(params.id);

	return new Response(null, { status: 204 });
}
