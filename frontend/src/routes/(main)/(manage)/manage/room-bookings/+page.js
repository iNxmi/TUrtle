import {itemsBookingsPath, usersPath} from '$lib/backend.js';
import {create} from "$lib/page.js";

export const load = create(
    [itemsBookingsPath, usersPath],
    [
        'id',
        'userId',
        'itemId',
        'start',
        'end',
        'status',
        'updatedAt',
        'createdAt'
    ]
)