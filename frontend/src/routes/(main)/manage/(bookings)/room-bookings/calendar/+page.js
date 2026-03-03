import {create} from "$lib/page.js";

export const load = create(
    [itemBookingsPath, usersPath],
    [
        'id',
        'userId',
        'itemId',
        'start',
        'end',
        'status',
        'updatedAt',
        'createdAt'
    ],
    ""
)