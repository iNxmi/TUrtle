import request from "$lib/api/api";
import { checkAuthorization } from "$lib/utils";
import { usersPath, deviceBookingsPath } from '$lib/backend';
import { create } from "$lib/page";
export const load = create(
    [deviceBookingsPath, usersPath],

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