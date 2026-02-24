import {request} from "./API.js";

export class Exceptions {

    static async get(id) {
        return request(`/api/exceptions/${id}`);
    }

    static async getCollection(parameters) {
        const params = new URLSearchParams(parameters);
        return request(`/api/exceptions?${params}`);
    }

    static async delete(id) {
        return request(`/api/exceptions/${id}`, {method: "DELETE"});
    }

}