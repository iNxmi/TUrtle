<script>
    import SideNavigation from "$lib/components/SideNavigation.svelte";
    import Footer from "$lib/components/Footer.svelte";
    import ConfirmProvider from '$lib/components/ConfirmProvider.svelte';
    import {m} from "$lib/paraglide/messages.js";
    import Breadcrumbs from "$lib/components/Breadcrumbs.svelte";
    import {
        BookOpenSolid,
        CalendarEditSolid,
        ChartOutline,
        ClipboardSolid,
        FilePasteSolid,
        GlobeOutline,
        HomeSolid,
        QuestionCircleSolid,
        TerminalOutline,
        UserCircleOutline,
        UserHeadsetSolid,
        UsersGroupSolid,
        UserSolid
    } from "flowbite-svelte-icons";

    let {data, children} = $props();
    let permissions = $derived(data.permissions);
    let user = $derived(data.user);
    let connection = $derived(data.connection);

    const publicItems = [{
        label: m.main_navigation_item_public_home(),
        keys: ["/"],
        href: "/",
        icon: HomeSolid
    }, {
        label: m.main_navigation_item_public_faq(),
        keys: ["/faq"],
        href: "/faq",
        icon: QuestionCircleSolid
    }, {
        label: m.main_navigation_item_public_contact(),
        keys: ["/contact"],
        href: "/contact",
        icon: UserHeadsetSolid
    }];

    const userItems = $derived([{
        label: m.main_navigation_item_user_dashboard(),
        keys: ["/user/dashboard"],
        href: "/user/dashboard",
        icon: ChartOutline
    }, {
        label: m.main_navigation_item_user_item_bookings(),
        keys: ["/user/item-bookings"],
        href: "/user/item-bookings",
        icon: CalendarEditSolid
    }, {
        permissions: ["REQUEST_ROOM_BOOKINGS"],
        label: m.main_navigation_item_user_room_bookings(),
        keys: ["/user/room-bookings"],
        href: "/user/room-bookings",
        icon: CalendarEditSolid
    }, {
        label: m.main_navigation_item_user_profile(),
        keys: ["/user/profile"],
        href: "/user/profile",
        icon: UserSolid
    }].filter(item => user != null && (!item.permissions || item.permissions.some(p => permissions.includes(p)))));

    const manageItems = $derived([{
        permissions: [
            "MANAGE_USERS",
            "MANAGE_ROLES"
        ],
        label: m.main_navigation_item_manage_user_management(),
        keys: [
            "/manage/users",
            "/manage/roles"
        ],
        href: "/manage/users",
        icon: UsersGroupSolid
    }, {
        permissions: [
            "MANAGE_ITEMS",
            "MANAGE_ITEM_CATEGORIES",
            "MANAGE_LOCKERS"
        ],
        label: m.main_navigation_item_manage_item_management(),
        keys: [
            "/manage/items",
            "manage/item-categories",
            "manage/locker"
        ],
        href: "/manage/items",
        icon: ClipboardSolid
    }, {
        permissions: [
            "MANAGE_ROOM_BOOKINGS",
            "MANAGE_ITEM_BOOKINGS"
        ],
        label: m.main_navigation_item_manage_bookings(),
        keys: [
            "/manage/room-bookings",
            "/manage/item-bookings"
        ],
        href: "/manage/room-bookings",
        icon: CalendarEditSolid
    }, {
        permissions: [
            "MANAGE_AUDIT_LOGS",
            "MANAGE_CONFIGURATIONS",
            "MANAGE_EXCEPTIONS",
            "MANAGE_STATISTIC_QUERIES"
        ],
        label: m.main_navigation_item_manage_administration(),
        keys: [
            "/manage/audit-logs",
            "/manage/configuration",
            "/manage/exceptions",
            "/manage/statistic-queries"
        ],
        href: "/manage/audit-logs",
        icon: BookOpenSolid
    },{
        permissions: [
            "MANAGE_EMAIL_TEMPLATES",
            "MANAGE_CONTENT_TEMPLATES",
            "MANAGE_FAQ"
        ],
        label: m.main_navigation_item_manage_content(),
        keys: [
            "/manage/email-templates",
            "/manage/content-templates",
            "/manage/faq"
        ],
        href: "/manage/email-templates",
        icon: FilePasteSolid
    }, {
        permissions: [
            "MANAGE_SUPPORT_TICKETS",
            "MANAGE_SUPPORT_TICKET_CATEGORIES"
        ],
        label: m.main_navigation_item_manage_support(),
        keys: [
            "/manage/support-tickets",
            "/manage/support-ticket-categories",
            "/manage/support-ticket-urgencies",],
        href: "/manage/support-tickets",
        icon: UserHeadsetSolid
    }].filter(item => !item.permissions || item.permissions.some(p => permissions.includes(p)))
        .sort((a, b) => a.href.localeCompare(b.href)));

    let categories = $derived([{
        icon: GlobeOutline,
        label: m.main_navigation_category_public(),
        items: publicItems
    }, {
        icon: UserCircleOutline,
        label: m.main_navigation_category_user(),
        items: userItems
    }, {
        icon: TerminalOutline,
        label: m.main_navigation_category_manage(),
        items: manageItems
    }].filter(category => category.items.length > 0));

    let header = $derived(user !== null ? `${user.firstName} ${user.lastName}` : null);
</script>

<div class="flex">
    <SideNavigation logoRedirect="/"
                    header={header}
                    categories={categories}
                    isTrusted={connection.trusted}
                    isLocalNetwork={connection.local}
                    showLogoutButton={user !== null}
    />

    <div class="w-full bg-background max-h-svh overflow-y-auto">
        <div class="flex flex-col gap-5 min-h-svh p-5">
            <Breadcrumbs/>

            <ConfirmProvider>
                {@render children?.()}
            </ConfirmProvider>
        </div>

        <Footer/>
    </div>
</div>