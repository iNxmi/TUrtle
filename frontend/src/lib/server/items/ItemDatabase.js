export class ItemDatabase {
	constructor(db) {
		this.db = db;
		this.collection = 'Items';
		this.id = 0;
	}

	createItem(item) {
		this.db.create(this.collection, {
			id: this.id.toString(),
			...item
		});
		this.id += 1;
	}

	getItems() {
		return this.db.read(this.collection);
	}

	getItemById(id) {
		return this.db.read(this.collection, id);
	}

	deleteItemById(id) {
		this.db.delete(this.collection, id);
	}
	updateItemByID(id, Item) {
		this.db.update(this.collection, id, Item);
	}
}