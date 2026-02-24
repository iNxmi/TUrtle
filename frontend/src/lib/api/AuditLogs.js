import {request} from "./API.js";

export class AuditLogs {

    static async get(id) {
        return request(`/api/audit-logs/${id}`);
    }

    static async getCollection(parameters) {
        const params = new URLSearchParams(parameters);
        return request(`/api/audit-logs?${params}`);
    }

}