import {request} from "./API.js";

export class Configuration {

    static async get(id) {
        return request(`/api/configuration/${id}`);
    }

    static async getCollection(parameters) {
        const params = new URLSearchParams(parameters);
        return request(`/api/configuration?${params}`);
    }

    static async patch(id, payload) {
        return request(`/api/configuration/${id}`, {
            method: "PATCH",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });
    }

}