<script>
    import Navigation from "$lib/components/Navigation.svelte";
    import Footer from "$lib/components/Footer.svelte";
    import ConfirmProvider from '$lib/components/ConfirmProvider.svelte';
    import {m} from "$lib/paraglide/messages.js";
    import Breadcrumbs from "$lib/components/Breadcrumbs.svelte";
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

    import {Hr} from "flowbite-svelte";

    let {data, children} = $props();
    let permissions = $derived(data.permissions);
    let user = $derived(data.user);

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
    }].sort((a, b) => a.href.localeCompare(b.href));

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
    }].filter(item => user != null && (!item.permissions || item.permissions.some(p => permissions.includes(p))))
        .sort((a, b) => a.href.localeCompare(b.href)));

    const manageItems = $derived([{
        permissions: ["MANAGE_USERS"],
        label: m.main_navigation_item_manage_users(),
        keys: ["/manage/users"],
        href: "/manage/users",
        icon: UsersGroupSolid
    }, {
        permissions: ["MANAGE_ROLES"],
        label: m.main_navigation_item_manage_roles(),
        keys: ["/manage/roles"],
        href: "/manage/roles",
        icon: UsersGroupSolid
    }, {
        permissions: ["MANAGE_ITEMS"],
        label: m.main_navigation_item_manage_items(),
        keys: ["/manage/items"],
        href: "/manage/items",
        icon: ClipboardSolid
    }, {
        permissions: ["MANAGE_ITEM_CATEGORIES"],
        label: m.main_navigation_item_manage_item_categories(),
        keys: ["/manage/item-categories"],
        href: "/manage/item-categories",
        icon: ClipboardSolid
    }, {
        permissions: ["MANAGE_ROOM_BOOKINGS"],
        label: m.main_navigation_item_manage_room_bookings(),
        keys: ["/manage/room-bookings"],
        href: "/manage/room-bookings",
        icon: CalendarEditSolid
    }, {
        permissions: ["MANAGE_ITEM_BOOKINGS"],
        label: m.main_navigation_item_manage_item_bookings(),
        keys: ["/manage/item-bookings"],
        href: "/manage/item-bookings",
        icon: CalendarEditSolid
    }, {
        permissions: ["MANAGE_AUDIT_LOGS"],
        label: m.main_navigation_item_manage_audit_logs(),
        keys: ["/manage/audit-logs"],
        href: "/manage/audit-logs",
        icon: BookOpenSolid
    }, {
        permissions: [
            "MANAGE_EMAIL_TEMPLATES",
            "MANAGE_CONTENT_TEMPLATES"
        ],
        label: m.main_navigation_item_manage_templates(),
        keys: [
            "/manage/email-templates",
            "/manage/content-templates"
        ],
        href: "/manage/email-templates",
        icon: FilePasteSolid
    }, {
        permissions: ["MANAGE_FAQ"],
        label: m.main_navigation_item_manage_faq(),
        keys: ["/manage/faq"],
        href: "/manage/faq",
        icon: FilePasteSolid
    }, {
        permissions: ["MANAGE_LOCKERS"],
        label: m.main_navigation_item_manage_lockers(),
        keys: ["/manage/lockers"],
        href: "/manage/lockers",
        icon: PaperClipOutline
    }, {
        permissions: ["MANAGE_SUPPORT_TICKETS"],
        label: m.main_navigation_item_manage_support_tickets(),
        keys: ["/manage/support-tickets"],
        href: "/manage/support-tickets",
        icon: UserHeadsetSolid
    }, {
        permissions: ["MANAGE_EXCEPTIONS"],
        label: m.main_navigation_item_manage_exceptions(),
        keys: ["/manage/exceptions"],
        href: "/manage/exceptions",
        icon: BugSolid
    }, {
        permissions: ["MANAGE_CONFIGURATION"],
        label: m.main_navigation_item_manage_configuration(),
        keys: ["/manage/configuration"],
        href: "/manage/configuration",
        icon: AdjustmentsVerticalSolid
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
    <Navigation logoRedirect="/"
                header={header}
                categories={categories}
                showLogoutButton={user !== null}
    />

    <div class="w-full bg-background max-h-svh overflow-y-auto">
        <div class="flex flex-col gap-5 min-h-svh p-5">
            <Breadcrumbs/>

            <Hr class="m-0 p-0"/>

            <ConfirmProvider>
                {@render children?.()}
            </ConfirmProvider>
        </div>

        <Footer/>
    </div>
</div>