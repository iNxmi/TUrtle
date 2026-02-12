export const seedDevicebookings = (db) => {

    const bookings = [
        {
            name: "Dell Laptop",
            start: "2026-01-05T06:00Z",
            end: "2026-01-07T06:00Z",
            itemId: "0",
            userId: "0",
            locker: "5",
            status: "COLLECTION_READY",
            createdAt: new Date(Date.now())
        },
         {
            name: "Asus Laptop",
            start: "2026-01-06T06:00Z",
            end: "2026-01-08T06:00Z",
            itemId: "1",
            userId: "1",
            locker: "4",
            status: "RESERVATION_ENDED",
            createdAt: new Date(Date.now())
        },
         {
            name: "IPad",
            start: "2026-01-02T06:00Z",
            end: "2026-01-04T06:00Z",
            itemId: "2",
            userId: "2",
            locker: "4",
            status: "RESERVED",
            createdAt: new Date(Date.now())
        },
         {
            name: "Samsung Tab A",
            start: "2026-01-03T06:00Z",
            end: "2026-01-05T06:00Z",
            itemId: "3",
            userId: "3",
            locker: "7",
            status: "ITEM_RETURNED",
            createdAt: new Date(Date.now())
        }
    ];

    bookings.forEach((booking) => db.createDevicebooking(booking));
}