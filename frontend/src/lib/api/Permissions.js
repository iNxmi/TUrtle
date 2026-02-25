import {request} from "./API.js";

export class Permissions {

    static async permission() {
        return request("/api/permissions/enum/permission")
    }

    static async permissions() {
        return request("/api/permissions");
    }

}