import { json } from '@sveltejs/kit';

export async function GET(){
    return json([
        {
            deviceName: "Dell Laptop",
            start: "2026-01-05T06:00Z",
            end: "2026-01-07T06:00Z",
            locker: "5"
        },
         {
            deviceName: "Asus Laptop",
            start: "2026-01-06T06:00Z",
            end: "2026-01-08T06:00Z",
            locker: "4"
        },
         {
            deviceName: "IPad",
            start: "2026-01-02T06:00Z",
            end: "2026-01-04T06:00Z",
            locker: "4"
        },
         {
            deviceName: "Beamer",
            start: "2026-01-03T06:00Z",
            end: "2026-01-05T06:00Z",
            locker: "7"
        }
    ]);
}