import {request} from "./API.js";

export class Items {

    static async create(payload) {
        return request("/api/items", {
            method: "POST",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });
    }

    static async get(id) {
        return request(`/api/items/${id}`);
    }

    static async getCollection(parameters) {
        const params = new URLSearchParams(parameters);
        return request(`/api/items?${params}`);
    }

    static async patch(id, payload) {
        return request(`/api/items/${id}`, {
            method: "PATCH",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });
    }

    static async delete(id) {
        return request(`/api/items/${id}`, {method: "DELETE"});
    }

}