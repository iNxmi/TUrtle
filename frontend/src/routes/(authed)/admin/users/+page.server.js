import * as db from '$lib/server/UserDatabase.js';
import { getegid } from 'process';

export async function load() {
	const users = db.getUsers();

	return { users };
}
export const actions = {
	default: async ({ cookies, request }) => {
		const data = await request.formData();
		const name = data.get('first_name') + ' ' + data.get('last_name');
		const email = data.get('email');
		db.createUser(name, email);
	}
};
