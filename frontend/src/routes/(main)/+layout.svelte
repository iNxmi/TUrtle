<script>
    import Navigation from "$lib/components/Navigation.svelte";
    import Footer from "$lib/components/Footer.svelte";
    import ConfirmProvider from '$lib/components/ConfirmProvider.svelte';
    import {m} from "$lib/paraglide/messages.js";
    import {
        AdjustmentsVerticalSolid,
        BookOpenSolid,
        BugSolid,
        CalendarEditSolid,
        ChartOutline,
        ClipboardSolid,
        FilePasteSolid,
        GlobeOutline,
        HomeSolid,
        PaperClipOutline,
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

    const publicItems = [{
        label: m.navigation_public_home(),
        href: "/",
        icon: HomeSolid
    }, {
        label: m.navigation_public_faq(),
        href: "/faq",
        icon: QuestionCircleSolid
    }, {
        label: m.navigation_public_contact(),
        href: "/contact",
        icon: UserHeadsetSolid
    }];

    const userItems = [{
        label: m.navigation_user_dashboard(),
        href: "/user/dashboard",
        icon: ChartOutline
    }, {
        label: m.navigation_user_item_bookings(),
        href: "/user/item-bookings",
        icon: CalendarEditSolid
    }, {
        permission: "REQUEST_ROOM_BOOKINGS",
        label: m.navigation_user_room_bookings(),
        href: "/user/room-bookings",
        icon: CalendarEditSolid
    }, {
        label: m.navigation_user_profile(),
        href: "/user/profile",
        icon: UserSolid
    }].filter(item => user != null && (!item.permission || permissions.includes(item.permission)));

    const manageItems = [{
        permission: "MANAGE_USERS",
        label: m.navigation_manage_users(),
        href: "/manage/users",
        icon: UsersGroupSolid
    }, {
        permission: "MANAGE_ROLES",
        label: m.navigation_manage_roles(),
        href: "/manage/roles",
        icon: UsersGroupSolid
    }, {
        permission: "MANAGE_ITEMS",
        label: m.navigation_manage_items(),
        href: "/manage/items",
        icon: ClipboardSolid
    }, {
        permission: "MANAGE_ITEM_CATEGORIES",
        label: m.navigation_manage_item_categories(),
        href: "/manage/item-categories",
        icon: ClipboardSolid
    }, {
        permission: "MANAGE_ROOM_BOOKINGS",
        label: m.navigation_manage_room_bookings(),
        href: "/manage/room-bookings",
        icon: CalendarEditSolid
    }, {
        permission: "MANAGE_ITEM_BOOKINGS",
        label: m.navigation_manage_item_bookings(),
        href: "/manage/item-bookings",
        icon: CalendarEditSolid
    }, {
        permission: "MANAGE_AUDIT_LOGS",
        label: m.navigation_manage_audit_logs(),
        href: "/manage/audit-logs",
        icon: BookOpenSolid
    }, {
        permission: "MANAGE_GENERAL_TEMPLATES",
        label: m.navigation_manage_content_templates(),
        href: "/manage/content-templates",
        icon: FilePasteSolid
    }, {
        permission: "MANAGE_EMAIL_TEMPLATES",
        label: m.navigation_manage_email_templates(),
        href: "/manage/email-templates",
        icon: FilePasteSolid
    }, {
        permission: "MANAGE_FAQ",
        label: m.navigation_manage_faq(),
        href: "/manage/faq",
        icon: FilePasteSolid
    }, {
        permission: "MANAGE_LOCKERS",
        label: m.navigation_manage_lockers(),
        href: "/manage/lockers",
        icon: PaperClipOutline
    }, {
        permission: "MANAGE_SUPPORT_TICKETS",
        label: m.navigation_manage_support_tickets(),
        href: "/manage/support-tickets",
        icon: UserHeadsetSolid
    }, {
        permission: "MANAGE_EXCEPTIONS",
        label: m.navigation_manage_exceptions(),
        href: "/manage/exceptions",
        icon: BugSolid
    }, {
        permission: "MANAGE_SYSTEM_SETTINGS",
        label: m.navigation_manage_system_settings(),
        href: "/manage/system-settings",
        icon: AdjustmentsVerticalSolid
    }].filter(item => !item.permission || permissions.includes(item.permission));

    let categories = [{
        icon: GlobeOutline,
        label: m.navigation_category_public(),
        items: publicItems
    }, {
        icon: UserCircleOutline,
        label: m.navigation_category_user(),
        items: userItems
    }, {
        icon: TerminalOutline,
        label: m.navigation_category_manage(),
        items: manageItems
    }].filter(category => category.items.length > 0);

    let header = user !== null ? `${user.firstName} ${user.lastName}` : null;
</script>

<div class="flex">
    <Navigation
            logoRedirect="/"
            header={header}
            categories={categories}
            showLogoutButton={user !== null}
    />

    <div class="w-full bg-background max-h-svh overflow-y-auto">
        <div class="min-h-svh max-h-svh p-5">
            <ConfirmProvider>
                {@render children?.()}
            </ConfirmProvider>
        </div>
        <Footer/>
    </div>
</div>