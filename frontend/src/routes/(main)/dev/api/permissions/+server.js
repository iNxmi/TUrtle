import { json } from '@sveltejs/kit';

export async function GET(){
    const itemsPublic = [
        "MANAGE_AUDIT_LOGS",
        "MANAGE_DOOR",
        "MANAGE_ITEM_CATEGORIES",
        "MANAGE_ITEMS",
        "MANAGE_EXCEPTIONS",
        "MANAGE_LOCKERS",
        "MANAGE_SUPPORT_TICKETS",
        "MANAGE_ROLES",
        "MANAGE_USERS",
        "MANAGE_EMAIL_TEMPLATES",
        "MANAGE_GENERAL_TEMPLATES",
        "MANAGE_FAQ",
        "MANAGE_ROOM_BOOKINGS",
        "MANAGE_ITEM_BOOKINGS",
        "MANAGE_SYSTEM_SETTINGS"
    ];

    return json(itemsPublic)
}