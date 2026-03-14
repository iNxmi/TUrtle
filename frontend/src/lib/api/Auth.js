import {request} from "./API.js";

export class Auth {

    static async me() {
        return request("/api/auth/me");
    }

    static async login(payload) {
        return request("/api/auth/login", {
            method: "POST",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });
    }

    static async register(payload) {
        return request("/api/auth/register", {
            method: "POST",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });
    }

    static async logout() {
        return request("/api/auth/logout", {method: "POST"});
    }

    static async refresh() {
        return request("/api/auth/refresh", {method: "POST"});
    }

    static async resendAccountVerification(session) {
        return request(`/api/auth/resend-account-verification?session=${session}`, {method: "POST"});
    }

    static async submitAccountVerification(session, code) {
        return request(`/api/auth/submit-account-verification?session=${session}&code=${code}`, {method: "POST"});
    }

}