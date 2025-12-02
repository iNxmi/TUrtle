import { data } from './sampleUsers.js';
import SimpleDB from './SimpleDB.js';

export class UserDatabase {
	constructor(db) {
		this.db = db;
		this.collection = 'users';
	}

	seedDatabase() {
		data.forEach((user) =>
			this.db.create('users', {
				firstName: user.firstName,
				lastName: user.lastName,
				studentId: user.studentId,
				username: user.username,
				email: user.email
			})
		);
	}

	getUsers() {
		const users = this.db.read('users');

		return users;
	}

	getUser(id) {
		const user = this.db.read('users', id);
		return user;
	}

	createUser(name, email) {
		this.db.create('users', { name: name, email: email });
	}

	deleteUser(id) {
		this.db.delete('users', id);
	}
}
