<script>
    import '../app.css';
    import {page} from '$app/state';
    import {onMount, setContext} from 'svelte';
    import {goto} from '$app/navigation';

    let {data, children} = $props();
    const permissions = data.permissions;
    const user = data.user;

    import {
        Heading,
        Hr,
        Select,
        Sidebar,
        SidebarDropdownItem,
        SidebarDropdownWrapper,
        Span,
        Toggle
    } from 'flowbite-svelte';

    import {m} from '$lib/paraglide/messages.js';
    import {setLocale} from '$lib/paraglide/runtime.js';

    import TUrtleLogo from "$lib/components/TUrtleLogo.svelte";
    import Footer from "$lib/components/Footer.svelte";
    import {
        BookOpenSolid,
        BugSolid,
        CalendarEditSolid,
        CalendarMonthSolid,
        DesktopPcSolid,
        HomeSolid,
        InfoCircleSolid,
        NewspaperSolid,
        UserHeadsetSolid,
        UserSettingsSolid,
        UsersGroupSolid,
        UserSolid,
        LockSolid
    } from 'flowbite-svelte-icons';

    const languages = [
        {value: 'en', name: 'English'},
        {value: 'de', name: 'Deutsch'},
        {value: 'ja', name: '日本語'},
        {value: 'ar', name: '_arabic'},
        {value: 'ru', name: '_russian'},
        {value: 'vi', name: '_vietnamese'},
        {value: 'hu', name: '_hungarian'},
        {value: 'ro', name: '_romanian'}
    ];

    let language = $state(page.url.searchParams.get('locale') || 'de');

    setContext('locale', () => language);

    function updateLanguage(event) {
        event.preventDefault();

        goto(`?locale=${language}`);
        setTimeout(() => setLocale(language));
    }

    let activeUrl = $derived(page.url.pathname);

    let darkmode = $state(false);
    onMount(() => {
        if (document.documentElement.className === 'dark') darkmode = true;
        delete document.documentElement.dataset.theme;
    });

    function toggleDarkMode() {
        document.documentElement.classList.remove(darkmode ? 'dark' : 'light');
        document.documentElement.classList.add(darkmode ? 'light' : 'dark');
        darkmode = !darkmode;
        document.documentElement.data
    }

    const itemsPublic = [{
        permission: "FRONTEND__SIDEBAR_ITEM__HOME",
        label: m.sidebar_public_home(),
        href: '/',
        icon: HomeSolid
    }, {
        permission: "FRONTEND__SIDEBAR_ITEM__LOGIN",
        label: m.sidebar_public_login(),
        href: '/login',
        icon: UserSolid
    }, {
        permission: "FRONTEND__SIDEBAR_ITEM__REGISTER",
        label: m.sidebar_public_register(),
        href: '/register',
        icon: UserSolid
    }, {
        permission: "FRONTEND__SIDEBAR_ITEM__SUPPORT",
        label: m.sidebar_public_support(),
        href: '/support',
        icon: UserHeadsetSolid
    }];
    const itemsUser = [{
        permission: "FRONTEND__SIDEBAR_ITEM__DASHBOARD",
        label: m.sidebar_user_dashboard(),
        href: '/user/dashboard',
        icon: NewspaperSolid
    }, {
        permission: "FRONTEND__SIDEBAR_ITEM__PROFILE",
        label: m.sidebar_user_profile(),
        href: '/user/profile',
        icon: UserSolid
    }, {
        permission: "FRONTEND__SIDEBAR_ITEM__BOOK_DEVICE",
        label: m.sidebar_user_reservations(),
        href: '/user/reservation',
        icon: DesktopPcSolid
    }, {
        permission: "FRONTEND__SIDEBAR_ITEM__BOOK_ROOM",
        label: m.sidebar_user_bookings(),
        href: '/user/room',
        icon: CalendarMonthSolid
    }];

    const itemsAdmin = [{
        permission: "FRONTEND__SIDEBAR_ITEM__MANAGE_USERS",
        label: m.sidebar_admin_manage_users(),
        href: '/admin/users',
        icon: UsersGroupSolid
    }, {
        permission: "FRONTEND__SIDEBAR_ITEM__MANAGE_ROOM_BOOKINGS",
        label: m.sidebar_admin_manage_bookings(),
        href: '/admin/bookings',
        icon: CalendarEditSolid
    }, {
        permission: "FRONTEND__SIDEBAR_ITEM__MANAGE_SUPPORT_TICKETS",
        label: m.sidebar_admin_manage_support_tickets(),
        href: '/admin/support',
        icon: UserHeadsetSolid
    }, {
        permission: "FRONTEND__SIDEBAR_ITEM__MANAGE_EXCEPTIONS",
        label: m.sidebar_admin_manage_exceptions(),
        href: '/admin/exceptions',
        icon: BugSolid
    }, {
        permission: "FRONTEND__SIDEBAR_ITEM__MANAGE_NEWS",
        label: m.sidebar_admin_manage_news(),
        href: '/admin/news',
        icon: NewspaperSolid
    }, {
        permission: null,
        label: m.sidebar_admin_settings(),
        href: '/admin/settings',
        icon: UserSettingsSolid
    }, {
        permission: "FRONTEND__SIDEBAR_ITEM__MANAGE_AUDIT_LOGS",
        label: m.sidebar_admin_auditlogs(),
        href: '/admin/auditlogs',
        icon: BookOpenSolid
    }, {
        permission: "FRONTEND__SIDEBAR_ITEM__MANAGE_ROLES",
        label: m.sidebar_admin_roles(),
        href: '/admin/roles',
        icon: LockSolid
    }];

</script>

<div class="flex">
    <Sidebar
            alwaysOpen
            {activeUrl}
            isSingle={false}
            backdrop={false}
            isOpen={true}
            position="static"
            class="min-w-64 min-h-svh"
    >
        <div class="flex flex-col gap-3">
            <div class="flex flex-col items-center">
                <TUrtleLogo/>
            </div>

            <Hr class="m-0 p-0"/>

            {#if user !== {} }
                <Heading tag="h5" class="text-center">
					<Span class="text-csw">
						{`${user.firstName} ${user.lastName}`}
					</Span>
                </Heading>

                <Hr class="m-0 p-0"/>
            {/if}

            {#if permissions.includes("FRONTEND__SIDEBAR_CATEGORY__PUBLIC")}
                <SidebarDropdownWrapper
                        class="list-none"
                        classes={{ span: 'font-bold' }}
                        isOpen={true}
                        label={m.sidebar_category_public()}
                >
                    {#each itemsPublic as item}
                        {#if permissions.includes(item.permission)}
                            <SidebarDropdownItem label={item.label} href={item.href}>
                                {#snippet icon()}
                                    <item.icon class="text-csw h-5 w-5"/>
                                {/snippet}
                            </SidebarDropdownItem>
                        {/if}
                    {/each}
                </SidebarDropdownWrapper>
            {/if}

            {#if permissions.includes("FRONTEND__SIDEBAR_CATEGORY__USER")}
                <SidebarDropdownWrapper
                        class="list-none"
                        classes={{ span: 'font-bold' }}
                        isOpen={true}
                        label={m.sidebar_category_user()}
                >
                    {#each itemsUser as item}
                        {#if permissions.includes(item.permission)}
                            <SidebarDropdownItem label={item.label} href={item.href}>
                                {#snippet icon()}
                                    <item.icon class="text-csw h-5 w-5"/>
                                {/snippet}
                            </SidebarDropdownItem>
                        {/if}
                    {/each}
                </SidebarDropdownWrapper>
            {/if}

            {#if permissions.includes("FRONTEND__SIDEBAR_CATEGORY__ADMINISTRATOR")}
                <SidebarDropdownWrapper class="list-none" classes={{ span: "font-bold" }} isOpen={true}
                                        label={m.sidebar_category_admin()}>
                    {#each itemsAdmin as item}
                        {#if permissions.includes(item.permission)}
                            <SidebarDropdownItem label={item.label} href={item.href}>
                                {#snippet icon()}
                                    <item.icon class="text-csw h-5 w-5"/>
                                {/snippet}
                            </SidebarDropdownItem>
                        {/if}
                    {/each}
                </SidebarDropdownWrapper>
            {/if}

            <SidebarDropdownWrapper
                    class="list-none"
                    classes={{ span: 'font-bold' }}
                    isOpen={true}
                    label={m.sidebar_category_settings()}
            >
                <Select items={languages} bind:value={language} onchange={updateLanguage}/>
                <Toggle onchange={toggleDarkMode}>_toggle_darkmode</Toggle>
            </SidebarDropdownWrapper>
        </div>
    </Sidebar>

    <div class="min-h-svh justify-between flex flex-col w-full dark:bg-gray-900">
        <div class="m-10">
            {@render children?.()}
        </div>
        <Footer/>
    </div>
</div>
