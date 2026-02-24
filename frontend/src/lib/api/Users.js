import {request} from "./API.js";

export class Users {

    static async create(payload) {
        return request("/api/users", {
            method: "POST",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });
    }

    static async get(id) {
        return request(`/api/users/${id}`);
    }

    static async getCollection(parameters) {
        const params = new URLSearchParams(parameters);
        return request(`/api/users?${params}`);
    }

    static async patch(id, payload) {
        return request(`/api/users/${id}`, {
            method: "PATCH",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });
    }

    static async delete(id) {
        return request(`/api/users/${id}`, {method: "DELETE"});
    }

}