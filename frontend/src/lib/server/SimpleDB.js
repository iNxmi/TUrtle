export default class SimpleDB {
    constructor() {
        this.collections = new Map();
    }

    _ensureCollection(collectionName) {
        if (!this.collections.has(collectionName)) {
            this.collections.set(collectionName, []);
        }
    }

    create(collectionName, data) {
        this._ensureCollection(collectionName);
        const collection = this.collections.get(collectionName);
        const newRecord = {
            id: Math.random().toString(36).substring(2, 9),
            ...data
        };
        collection.push(newRecord);
        return newRecord;
    }

    read(collectionName, id) {
        const collection = this.collections.get(collectionName);
        if (!collection) return id ? null : [];
        if (id) return collection.find((record) => record.id === id) || null;
        return collection;
    }

    update(collectionName, id, newData) {
        const collection = this.collections.get(collectionName);
        if (!collection) return null;
        const recordIndex = collection.findIndex((record) => record.id === id);
        if (recordIndex === -1) return null;
        const updatedRecord = {...collection[recordIndex], ...newData};
        collection[recordIndex] = updatedRecord;
        return updatedRecord;
    }

    delete(collectionName, id) {
        const collection = this.collections.get(collectionName);
        if (!collection) return false;
        const initialLength = collection.length;
        this.collections.set(
            collectionName,
            collection.filter((record) => record.id !== id)
        );
        return collection.length !== initialLength;
    }
}
