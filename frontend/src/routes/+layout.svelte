<script>
    import '../app.css';
    import {page} from '$app/state';
    import {onMount, setContext} from 'svelte';
    import {goto} from '$app/navigation';

    let {data, children} = $props();
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
    });

    function toggleDarkMode() {
        document.documentElement.classList.remove(darkmode ? 'dark' : 'light');
        document.documentElement.classList.add(darkmode ? 'light' : 'dark');
        darkmode = !darkmode;
    }

    const itemsPublic = [{
        label: m.sidebar_public_home(),
        href: '/',
        icon: HomeSolid
    }, {
        label: m.sidebar_public_login(),
        href: '/login',
        icon: UserSolid
    }, {
        label: m.sidebar_public_register(),
        href: '/register',
        icon: UserSolid
    }, {
        label: m.sidebar_public_support(),
        href: '/support',
        icon: UserHeadsetSolid
    }, {
        label: m.sidebar_public_faq(),
        href: '/faq',
        icon: UserHeadsetSolid
    }, {
        label: m.sidebar_public_about(),
        href: '/about',
        icon: InfoCircleSolid
    }];
    const itemsUser = [{
        label: m.sidebar_user_dashboard(),
        href: '/user/dashboard',
        icon: NewspaperSolid
    }, {
        label: m.sidebar_user_profile(),
        href: '/user/profile',
        icon: UserSolid
    }, {
        label: m.sidebar_user_reservations(),
        href: '/user/reservation',
        icon: DesktopPcSolid
    }, {
        label: m.sidebar_user_bookings(),
        href: '/user/bookings',
        icon: CalendarMonthSolid
    }];

    const itemsAdmin = [{
        label: m.sidebar_admin_manage_users(),
        href: '/admin/users',
        icon: UsersGroupSolid
    }, {
        label: m.sidebar_admin_manage_bookings(),
        href: '/admin/bookings',
        icon: CalendarEditSolid
    }, {
        label: m.sidebar_admin_manage_support_tickets(),
        href: '/admin/support',
        icon: UserHeadsetSolid
    }, {
        label: m.sidebar_admin_manage_exceptions(),
        href: '/admin/exceptions',
        icon: BugSolid
    }, {
        label: m.sidebar_admin_manage_news(),
        href: '/admin/news',
        icon: NewspaperSolid
    }, {
        label: m.sidebar_admin_settings(),
        href: '/admin/settings',
        icon: UserSettingsSolid
    }, {
        label: m.sidebar_admin_auditlogs(),
        href: '/admin/auditlogs',
        icon: BookOpenSolid
    }, {
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
            class="min-w-64 min-h-[calc(100svh-71px)]"
    >
        <div class="flex flex-col gap-3">
            <div class="flex flex-col items-center">
                <TUrtleLogo/>
            </div>

            <Hr class="m-0 p-0"/>

            {#if user}
                <Heading tag="h5" class="text-center">
					<Span class="text-csw">
						{`${user.firstName} ${user.lastName}`}
					</Span>
                </Heading>

                <Hr class="m-0 p-0"/>
            {/if}

            <SidebarDropdownWrapper
                    class="list-none"
                    classes={{ span: 'font-bold' }}
                    isOpen={true}
                    label={m.sidebar_category_public()}
            >
                {#each itemsPublic as item}
                    <SidebarDropdownItem label={item.label} href={item.href}>
                        {#snippet icon()}
                            <item.icon class="text-csw h-5 w-5"/>
                        {/snippet}
                    </SidebarDropdownItem>
                {/each}
            </SidebarDropdownWrapper>

            {#if user}
                <SidebarDropdownWrapper
                        class="list-none"
                        classes={{ span: 'font-bold' }}
                        isOpen={true}
                        label={m.sidebar_category_user()}
                >
                    {#each itemsUser as item}
                        <SidebarDropdownItem label={item.label} href={item.href}>
                            {#snippet icon()}
                                <item.icon class="text-csw h-5 w-5"/>
                            {/snippet}
                        </SidebarDropdownItem>
                    {/each}
                </SidebarDropdownWrapper>
            {/if}

            {#if user && user.permissions.includes("FRONTEND__VIEW_ADMINISTRATOR")}
                <SidebarDropdownWrapper class="list-none" classes={{ span: "font-bold" }} isOpen={true}
                                        label={m.sidebar_category_admin()}>
                    {#each itemsAdmin as item}
                        <SidebarDropdownItem label={item.label} href={item.href}>
                            {#snippet icon()}
                                <item.icon class="text-csw h-5 w-5"/>
                            {/snippet}
                        </SidebarDropdownItem>
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

    <div class="min-h-svh justify-between flex flex-col w-full">
        <div class="m-10 w-full">
            {@render children?.()}
        </div>
        <Footer/>
    </div>
</div>
