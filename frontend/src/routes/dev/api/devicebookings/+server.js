import { json } from '@sveltejs/kit';

export async function GET(){
    return json([
        /* {
            deviceName: "Dell Laptop",
            start: "2026-01-05",
            end: "2026-01-07",
            locker: "5"
        },
         {
            deviceName: "Asus Laptop",
            start: "2026-01-06",
            end: "2026-01-08",
            locker: "4"
        },
         {
            deviceName: "IPad",
            start: "2026-01-02",
            end: "2026-01-04",
            locker: "4"
        },
         {
            deviceName: "Beamer",
            start: "2026-01-03",
            end: "2026-01-05",
            locker: "7"
        } */
    ]);
}