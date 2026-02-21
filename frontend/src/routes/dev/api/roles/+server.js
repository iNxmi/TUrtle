import {json} from "@sveltejs/kit";


export async function GET() {

    const roles = [
        {
            "id": 1,
            "name": "Guest",
            "permissions": [
                "FRONTEND__SIDEBAR_ITEM__LOGIN",
                "FRONTEND__SIDEBAR_ITEM__REGISTER",
                "FRONTEND__SIDEBAR_ITEM__ABOUT",
                "FRONTEND__SIDEBAR_ITEM__HOME",
                "FRONTEND__SIDEBAR_ITEM__SUPPORT",
                "FRONTEND__SIDEBAR_ITEM__FAQ",
                "FRONTEND__SIDEBAR_CATEGORY__PUBLIC"
            ],
            "createdAt": "2026-01-22T13:29:11.757707Z"
        },
        {
            "id": 2,
            "name": "Student",
            "permissions": [
                "FRONTEND__SIDEBAR_CATEGORY__USER",
                "BACKEND__API_USER_PROFILE__PATCH",
                "FRONTEND__SIDEBAR_ITEM__PROFILE",
                "FRONTEND__SIDEBAR_ITEM__HOME",
                "FRONTEND__SIDEBAR_ITEM__SUPPORT",
                "FRONTEND__SIDEBAR_ITEM__BOOK_DEVICE",
                "BACKEND__API_USER_PROFILE__DELETE",
                "FRONTEND__SIDEBAR_CATEGORY__PUBLIC",
                "FRONTEND__SIDEBAR_ITEM__ABOUT",
                "BACKEND__API_USER_PROFILE__GET",
                "FRONTEND__SIDEBAR_ITEM__FAQ",
                "FRONTEND__SIDEBAR_ITEM__DASHBOARD"
            ],
            "createdAt": "2026-01-22T13:29:11.789419Z"
        },
        {
            "id": 3,
            "name": "Professor",
            "permissions": [
                "FRONTEND__SIDEBAR_CATEGORY__USER",
                "BACKEND__API_USER_PROFILE__PATCH",
                "FRONTEND__SIDEBAR_ITEM__PROFILE",
                "FRONTEND__SIDEBAR_ITEM__HOME",
                "FRONTEND__SIDEBAR_ITEM__SUPPORT",
                "FRONTEND__SIDEBAR_ITEM__BOOK_DEVICE",
                "BACKEND__API_USER_PROFILE__DELETE",
                "FRONTEND__SIDEBAR_CATEGORY__PUBLIC",
                "FRONTEND__SIDEBAR_ITEM__ABOUT",
                "BACKEND__API_USER_PROFILE__GET",
                "FRONTEND__SIDEBAR_ITEM__FAQ",
                "FRONTEND__SIDEBAR_ITEM__DASHBOARD",
                "FRONTEND__SIDEBAR_ITEM__BOOK_ROOM"
            ],
            "createdAt": "2026-01-22T13:29:11.790356Z"
        },
        {
            "id": 4,
            "name": "Administrator",
            "permissions": [
                "BACKEND__API_LOCKERS__CREATE",
                "FRONTEND__SIDEBAR_CATEGORY__USER",
                "BACKEND__API_EXCEPTIONS__DELETE",
                "BACKEND__API_ROOMBOOKINGS__CREATE",
                "FRONTEND__SIDEBAR_CATEGORY__PUBLIC",
                "FRONTEND__SIDEBAR_ITEM__MANAGE_AUDIT_LOGS",
                "BACKEND__API_DEVICECATEGORIES__GET",
                "FRONTEND__SIDEBAR_ITEM__LOGIN",
                "FRONTEND__SIDEBAR_ITEM__MANAGE_ROLES",
                "BACKEND__API_SUPPORTTICKETS__PATCH",
                "BACKEND__API_FAQ__PATCH",
                "BACKEND__API_DEVICES__GET",
                "BACKEND__API_AUDITLOGS__GET",
                "BACKEND__API_TEMPLATES__DELETE",
                "FRONTEND__SIDEBAR_ITEM__MANAGE_DEVICE_BOOKINGS",
                "FRONTEND__SIDEBAR_ITEM__REGISTER",
                "FRONTEND__SIDEBAR_ITEM__MANAGE_TEMPLATES",
                "BACKEND__API_FAQ__CREATE",
                "FRONTEND__SIDEBAR_ITEM__MANAGE_SUPPORT_TICKETS",
                "BACKEND__API_DEVICECATEGORIES__CREATE",
                "BACKEND__API_ROOMBOOKINGS__GET",
                "BACKEND__API_DEVICEBOOKINGS__PATCH",
                "BACKEND__API_USER_PROFILE__DELETE",
                "BACKEND__API_TEMPLATES__PATCH",
                "BACKEND__API_ROOMBOOKINGS__PATCH",
                "BACKEND__API_TEMPLATES__CREATE",
                "FRONTEND__SIDEBAR_ITEM__ABOUT",
                "BACKEND__API_FAQ__DELETE",
                "BACKEND__API_EXCEPTIONS__GET",
                "BACKEND__API_USER_PROFILE__GET",
                "BACKEND__API_HARDWARE__LOCKER",
                "FRONTEND__SIDEBAR_ITEM__BOOK_ROOM",
                "BACKEND__API_USERS__CREATE",
                "BACKEND__API_ROLES__GET",
                "FRONTEND__SIDEBAR_CATEGORY__ADMINISTRATOR",
                "BACKEND__API_USER_PROFILE__PATCH",
                "BACKEND__API_ROLES__DELETE",
                "BACKEND__API_SUPPORTTICKETS__GET",
                "BACKEND__API_DEVICECATEGORIES__DELETE",
                "BACKEND__DEBUG__EXCEPTION",
                "BACKEND__API_USERS__DELETE",
                "BACKEND__API_DEVICEBOOKINGS__DELETE",
                "BACKEND__API_USERS__PATCH",
                "BACKEND__API_TEMPLATES__GET",
                "FRONTEND__SIDEBAR_ITEM__MANAGE_USERS",
                "BACKEND__API_DEVICEBOOKINGS__GET",
                "BACKEND__API_SUPPORTTICKETS__DELETE",
                "FRONTEND__SIDEBAR_ITEM__MANAGE_ROOM_BOOKINGS",
                "BACKEND__API_DEVICES__CREATE",
                "BACKEND__API_DEVICES__PATCH",
                "BACKEND__API_DEVICES__DELETE",
                "BACKEND__API_LOCKERS__PATCH",
                "BACKEND__API_HARDWARE__DOOR",
                "BACKEND__API_DEVICECATEGORIES__PATCH",
                "BACKEND__API_USERS__GET",
                "FRONTEND__SIDEBAR_ITEM__MANAGE_EXCEPTIONS",
                "FRONTEND__SIDEBAR_ITEM__PROFILE",
                "BACKEND__DEBUG__EMAIL",
                "FRONTEND__SIDEBAR_ITEM__HOME",
                "BACKEND__DEBUG__INFO",
                "BACKEND__API_ROLES__PATCH",
                "FRONTEND__SIDEBAR_ITEM__SUPPORT",
                "FRONTEND__SIDEBAR_ITEM__BOOK_DEVICE",
                "FRONTEND__SIDEBAR_ITEM__MANAGE_NEWS",
                "BACKEND__API_DEVICEBOOKINGS__CREATE",
                "BACKEND__API_LOCKERS__GET",
                "FRONTEND__SIDEBAR_ITEM__FAQ",
                "FRONTEND__SIDEBAR_ITEM__DASHBOARD",
                "BACKEND__API_ROLES__CREATE",
                "BACKEND__API_ROOMBOOKINGS__DELETE",
                "BACKEND__DEBUG__LOCKER",
                "BACKEND__API_LOCKERS__DELETE",
                "BACKEND__DEBUG__DOOR"
            ],
            "createdAt": "2026-01-22T13:29:11.791044Z"
        }
    ];

    return json({
        content: roles, page: {
            size: 20,
            number: 0,
            totalElements: 4,
            totalPages: 1
        }
    });
}