import { json } from '@sveltejs/kit';

export async function GET({ request }) {
	const headers = request.headers.get('cookie');

	const response = await fetch('http://backend:8080/api/v1/users?page=0&size=20', {
		headers: {
			Cookie: headers
		}
	});

	const { content } = await response.json();

	return json(content);
}
