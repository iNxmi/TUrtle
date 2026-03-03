import {request} from "./API.js";

export class Connection {

    static async get() {
        return request("/api/connection");
    }

}