import { events } from './sampleEvents';

export class EventDatabase {
	constructor(db) {
		this.db = db;
		this.collection = 'events';
		this.id = 0;
	}

	seedDatabase() {
		events.forEach((event, index) => {
			this.db.create(this.collection, {
				id: index.toString(),
				...event
			});
			this.id += 1;
		});
	}

	createEvent(event) {
		this.db.create(this.collection, {
			id: this.id.toString(),
			...event
		});
		this.is += 1;
	}

	getEvents() {
		return this.db.read(this.collection);
	}

	getEventById(id) {
		return this.db.read(this.collection, id);
	}

	deleteEventById(id) {
		this.db.delete(this.collection, id);
	}
	updateEventByID(id, event) {
		this.db.update(this.collection, id, event);
	}
}
