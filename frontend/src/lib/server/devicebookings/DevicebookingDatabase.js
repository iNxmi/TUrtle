export class DevicebookingDatabase {
    constructor(db) {
        this.db = db;
        this.collection = 'devicebookings';
        this.id = 0;
    }

    createDevicebooking(booking) {
        this.db.create(this.collection, {
            id: this.id.toString(),
            ...booking
        });
        this.id += 1;
    }

    getDevicebookings() {
        return this.db.read(this.collection);
    }

    getDevicebookingById(id) {
        return this.db.read(this.collection, id);
    }

    deleteDevicebookingById(id) {
        this.db.delete(this.collection, id);
    }

    updateDevicebookingById(id, booking) {
        this.db.update(this.collection, id, booking);
    }

}