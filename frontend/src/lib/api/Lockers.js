import {request} from "./API.js";

export class Lockers {

    static async create(payload) {
        return request("/api/lockers", {
            method: "POST",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });
    }

    static async get(id) {
        return request(`/api/lockers/${id}`);
    }

    static async getCollection(parameters) {
        const params = new URLSearchParams(parameters);
        return request(`/api/lockers?${params}`);
    }

    static async patch(id, payload) {
        return request(`/api/lockers/${id}`, {
            method: "PATCH",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });
    }

    static async delete(id) {
        return request(`/api/lockers/${id}`, {method: "DELETE"});
    }

}