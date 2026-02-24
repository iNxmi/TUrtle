import {request} from "./API.js";

export class Altcha {

    static async challenge() {
        return request("/api/altcha/challenge");
    }

    static async trusted() {
        return request("/api/altcha/trusted");
    }

}