import {request} from "./API.js";

export class Tokens {

    static async type() {
        return request("/api/tokens/enum/type")
    }

    static async get(id) {
        return request(`/api/tokens/${id}`);
    }

    static async getCollection(parameters) {
        const params = new URLSearchParams(parameters);
        return request(`/api/tokens?${params}`);
    }

}