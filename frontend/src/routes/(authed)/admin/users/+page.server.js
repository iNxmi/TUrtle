import * as db from '$lib/server/UserDatabase.js';

export async function load({ fetch }) {
	const response = await fetch('/api/v1/users');

	const data = await response.json();

	return { data };
}
export const actions = {
	default: async ({ cookies, request }) => {
		const data = await request.formData();
		const name = data.get('first_name') + ' ' + data.get('last_name');
		const email = data.get('email');
		db.createUser(name, email);
	}
};
