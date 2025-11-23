import { events } from './sampleEvents';
import SimpleDB from '../SimpleDB.js';

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
				title: event.title,
				start: event.start,
				end: event.end
			});
			this.id += 1;
		});
	}

	createEvent(title, start, end) {
		this.this.db.create(this.collection, this.collection, {
			id: this.id.toString(),
			title,
			start,
			end
		});
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
