import {data} from './sampleUsers.js';
import SimpleDB from './SimpleDB.js';

export const db = new SimpleDB();

function seedDatabase() {
	data.forEach((user) =>
		db.create('users', {
			firstName: user.firstName,
			lastName: user.lastName,
			studentId: user.studentId,
			username: user.username,
			email: user.email
		})
	);
}
seedDatabase();

export function getUsers() {
	const users = db.read('users');

	return users;
}

export function getUser(id) {
	const user = db.read('users', id);
	return user;
}

export function createUser(name, email) {
	db.create('users', { name: name, email: email });
}

export function deleteUser(id) {
	db.delete('users', id);
}
