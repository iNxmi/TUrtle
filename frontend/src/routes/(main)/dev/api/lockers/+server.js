import { json } from "@sveltejs/kit";

export async function GET(){

    const lockers = [
  {
    id: 1,
    name: "3rd Locker",
    index: 3,
    isSoftwareUnlockable: false,
    locked: false,
    updatedAt: '2025-12-24T13:42:09.616072Z',
    createdAt: "2025-12-24T13:42:09.613448Z"
  },
  {
    id: 2,
    name: "4th Locker",
    index: 4,
    isSoftwareUnlockable: false,
     locked: false,
    updatedAt: '2025-12-24T13:42:09.616072Z',
    createdAt: "2025-12-24T13:42:09.616072Z"
  },
  {
    id: 3,
    name: "6th Locker",
    index: 6,
    isSoftwareUnlockable: true,
     locked: false,
    updatedAt: '2025-12-24T13:42:09.616072Z',
    createdAt: "2025-12-24T13:42:09.616779Z"
  },
  {
    id: 4,
    name: "7th Locker",
    index: 7,
    isSoftwareUnlockable: true,
    locked: false,
    updatedAt: '2025-12-24T13:42:09.616072Z',
    createdAt: "2025-12-24T13:42:09.618018Z"
  }
];

return json(lockers);
}