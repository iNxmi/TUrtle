import {request} from "./API.js";

export class Posts {

    static async create(payload) {
        return request("/api/posts", {
            method: "POST",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });
    }

    static async get(id) {
        return request(`/api/posts/${id}`);
    }

    static async getCollection(parameters) {
        const params = new URLSearchParams(parameters);
        return request(`/api/posts?${params}`);
    }

    static async patch(id, payload) {
        return request(`/api/posts/${id}`, {
            method: "PATCH",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });
    }

    static async delete(id) {
        return request(`/api/posts/${id}`, {method: "DELETE"});
    }

}