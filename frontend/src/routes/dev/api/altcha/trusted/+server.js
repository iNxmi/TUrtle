import {json} from "@sveltejs/kit";

export async function GET() {
    const payload = {
        "trusted": true
    }
    return json(payload);
}