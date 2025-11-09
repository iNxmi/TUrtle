import { json } from '@sveltejs/kit';
import { authorizationCookie } from '$lib/server/cookie.js';
import { log } from 'console';
export async function POST({ request, fetch }) {
	const body = await request.json();
	const response = await fetch('http://backend:8080/api/v1/auth/login', {
		method: 'POST',
		body: JSON.stringify(body),
		headers: {
			'Content-Type': 'application/json'
		}
	});

	const setCookie = response.headers.getSetCookie();

	const loginData = await response.text();

	console.log(response.url);
	console.log(response.headers);
	console.log(loginData);
	const username = loginData.username;

	return json({ username }, { headers: response.headers, status: 201 });
}
