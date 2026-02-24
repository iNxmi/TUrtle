import {request} from "./API.js";

export class StatisticQueries {

    static async create(payload) {
        return request("/api/statistic-queries", {
            method: "POST",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });
    }

    static async get(id) {
        return request(`/api/statistic-queries/${id}`);
    }

    static async getCollection(parameters) {
        const params = new URLSearchParams(parameters);
        return request(`/api/statistic-queries?${params}`);
    }

    static async patch(id, payload) {
        return request(`/api/statistic-queries/${id}`, {
            method: "PATCH",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });
    }

    static async delete(id) {
        return request(`/api/statistic-queries/${id}`, {method: "DELETE"});
    }

}