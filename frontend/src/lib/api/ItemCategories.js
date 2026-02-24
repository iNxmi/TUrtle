import {request} from "./API.js";

export class ItemCategories {

    static async create(payload) {
        return request("/api/item-categories", {
            method: "POST",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });
    }

    static async get(id) {
        return request(`/api/item-categories/${id}`);
    }

    static async getCollection(parameters) {
        const params = new URLSearchParams(parameters);
        return request(`/api/item-categories?${params}`);
    }

    static async patch(id, payload) {
        return request(`/api/item-categories/${id}`, {
            method: "PATCH",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });
    }

    static async delete(id) {
        return request(`/api/item-categories/${id}`, {method: "DELETE"});
    }

}