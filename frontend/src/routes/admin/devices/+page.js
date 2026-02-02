import { create} from '$lib/page';
import { devicesPath } from '$lib/backend';
export const load = create(
    devicesPath,
    [
        "id",
        "username",
        "firstName",
        "lastName",
        "email",
        "createdAt"
    ]
)