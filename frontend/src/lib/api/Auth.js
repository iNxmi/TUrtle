import {request} from "./API.js";

export class Auth {

    static async login(payload) {
        return request("/api/auth/login", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(payload)
        });
    }

    static async logout() {
        return request("/api/auth/logout", {method: "POST"});
    }

    static async me() {
        return request("/api/auth/me");
    }

    static async refresh() {
        return request("/api/auth/refresh", {method: "POST"});
    }

    static async requestVerification(email) {
        const payload = {email: email}
        return request("/api/auth/request-verification", {
            method: "POST",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });
    }

    static async verify(uuid) {
        return request(`/api/auth/verify?uuid=${uuid}`);
    }

}