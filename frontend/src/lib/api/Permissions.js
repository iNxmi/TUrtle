import {request} from "./API.js";

export class Permissions {

    static async permissions() {
        return request("/api/permissions");
    }

}