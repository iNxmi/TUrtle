import { json } from '@sveltejs/kit';
import { dev } from '$app/environment';
import * as db from '$lib/server/UserDatabase.js';

export async function GET({ request }) {
	if (dev) {
		const content = db.getUsers();
		return json(content);
	} else {
		const headers = request.headers.get('cookie');

		const response = await fetch('http://backend:8080/api/v1/users?page=0&size=20', {
			headers: {
				Cookie: headers
			}
		});

		const { content } = await response.json();
		return json(content);
	}
}
