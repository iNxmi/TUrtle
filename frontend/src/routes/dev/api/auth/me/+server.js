import {json} from '@sveltejs/kit';

export async function GET() {
    return json({
        id: "129",
        firstName: "Admin",
        lastName: "Admin",
        email: 'admin@admin.com',
        studentId: "0",
        username: "Admin"
    });
}
