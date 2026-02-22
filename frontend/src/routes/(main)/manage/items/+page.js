import {create} from '$lib/page.js';
import {itemsPath} from '$lib/backend.js';

export const load = create(
    itemsPath,
    [
        "id",
        "name",
        "acquiredAt",
        "updatedAt",
        "createdAt"
    ],
    ""
)