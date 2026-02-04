import { devicesPath } from "$lib/backend";
import { create } from "$lib/page";

export const load = create(
    devicesPath,
    [
        "id",
        'name',
        'description',
        'category',
        'locker',
        'acquiredAt',
        'updatedAt',
        'createdAt'
    ],
    'locker.id=='
)
