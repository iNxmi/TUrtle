import { json } from '@sveltejs/kit';

export async function GET(){
    return json([
        {
            id: "0",
            name: "Dell Laptop",
            start: "2026-01-05T06:00Z",
            end: "2026-01-07T06:00Z",
            deviceId: "0",
            locker: "5",
            status: "COLLECTION_READY"
        },
         {
            id: "1",
            name: "Asus Laptop",
            start: "2026-01-06T06:00Z",
            end: "2026-01-08T06:00Z",
            deviceId: "1",
            locker: "4",
            status: "RESERVATION_ENDED"
        },
         {
            id: "2",
            name: "IPad",
            start: "2026-01-02T06:00Z",
            end: "2026-01-04T06:00Z",
            deviceId: "2",
            locker: "4",
            status: "DEVICE_RETURNED"
        },
         {
            id: "3",
            name: "Beamer",
            start: "2026-01-03T06:00Z",
            end: "2026-01-05T06:00Z",
            deviceId: "3",
            locker: "7",
            status: "DEVICE_RETURNED"
        }
    ]);
}
export async function PATCH(){
    return json(true, {status:200});
}
export async function POST(){
    return json(true, {status:200, ok:true});
}