import request from "$lib/api/api.js";
import { checkAuthorization } from "$lib/utils.js";
import { usersPath, itemsBookingsPath } from '$lib/backend.js';
import { create } from "$lib/page.js";
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