import request from "$lib/api/api";
import { checkAuthorization } from "$lib/utils";
import { usersPath, itemsBookingsPath } from '$lib/backend';
import { create } from "$lib/page";
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