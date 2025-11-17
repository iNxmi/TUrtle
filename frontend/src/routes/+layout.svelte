<script>
    import '../app.css';
    import {Sidebar, SidebarDropdownWrapper, SidebarDropdownItem, Hr, Heading, Span} from 'flowbite-svelte';

    import {Select, Toggle} from 'flowbite-svelte';

    import {m} from '$lib/paraglide/messages.js';
    import {setLocale} from '$lib/paraglide/runtime.js';

    import TUrtleLogo from "../components/TUrtleLogo.svelte";
    import Footer from "../components/Footer.svelte";

    let {data, children} = $props();
    const user = data.user;

    const languages = [
        {value: "en", name: "English"},
        {value: "de", name: "Deutsch"},
        {value: "ja", name: "日本語"},
        {value: "ar", name: "_arabic"},
        {value: "ru", name: "_russian"},
        {value: "vi", name: "_vietnamese"},
        {value: "hu", name: "_hungarian"},
        {value: "ro", name: "_romanian"}
    ];
    let language = $state("en");

    function updateLanguage(event) {
        event.preventDefault();
        setLocale(language);
    }

    import {
        UserSolid,
        UsersGroupSolid,
        DesktopPcSolid,
        UserHeadsetSolid,
        CalendarMonthSolid,
        CalendarEditSolid,
        UserSettingsSolid,
        NewspaperSolid,
        HomeSolid,
        InfoCircleSolid,
        BugSolid,
        BookOpenSolid
    } from 'flowbite-svelte-icons';

    import {page} from '$app/state';

    let activeUrl = $state(page.url.pathname);
    $effect(() => {
        activeUrl = page.url.pathname;
    });

    import {onMount} from 'svelte';

    let darkmode = $state(false);
    onMount(() => {
        if (document.documentElement.className === 'dark')
            darkmode = true;
    });

    function toggleDarkMode() {
        document.documentElement.classList.remove(darkmode ? "dark" : "light");
        document.documentElement.classList.add(darkmode ? "light" : "dark");
        darkmode = !darkmode;
    }

    const theme = {
        sidebar: {
            base: 'bg-secondary dark:bg-secondary'
        }
    };

    const itemsPublic = [{
        label: m.sidebar_home(),
        href: '/',
        icon: HomeSolid
    }, {
        label: m.sidebar_login(),
        href: '/login',
        icon: UserSolid
    }, {
        label: m.sidebar_register(),
        href: '/register',
        icon: UserSolid
    }, {
        label: m.sidebar_support(),
        href: '/support',
        icon: UserHeadsetSolid
    }, {
        label: m.sidebar_about(),
        href: '/about',
        icon: InfoCircleSolid
    }];
    const itemsUser = [{
        label: m.sidebar_dashboard(),
        href: '/user/dashboard',
        icon: NewspaperSolid
    }, {
        label: m.sidebar_profile(),
        href: '/user/profile',
        icon: UserSolid
    }, {
        label: m.sidebar_reservations(),
        href: '/user/reservation',
        icon: DesktopPcSolid
    }, {
        label: m.sidebar_bookings(),
        href: '/user/bookings',
        icon: CalendarMonthSolid
    }, {
        label: m.sidebar_logout(),
        href: '/user/logout',
        icon: UserSolid
    }];

    const itemsAdmin = [{
        label: m.sidebar_manage_users(),
        href: '/admin/users',
        icon: UsersGroupSolid
    }, {
        label: m.sidebar_manage_bookings(),
        href: '/admin/bookings',
        icon: CalendarEditSolid
    }, {
        label: m.sidebar_manage_support_tickets(),
        href: '/admin/support',
        icon: UserHeadsetSolid
    }, {
        label: m.sidebar_manage_exceptions_tickets(),
        href: '/admin/exceptions',
        icon: BugSolid
    }, {
        label: m.sidebar_manage_news(),
        href: '/admin/news',
        icon: NewspaperSolid
    }, {
        label: m.sidebar_admin_settings(),
        href: '/admin/settings',
        icon: UserSettingsSolid
    }, {
        label: m.sidebar_auditlogs(),
        href: '/admin/auditlogs',
        icon:  BookOpenSolid
    }];

</script>

<div class="flex h-full">
    <Sidebar
            alwaysOpen
            {activeUrl}
            isSingle={false}
            backdrop={false}
            isOpen={true}
            position="static"
            class="min-w-64"
    >
        <div class="flex flex-col gap-3">
            <div class="flex flex-col items-center">
                <TUrtleLogo/>
            </div>

            <Hr class="m-0 p-0"/>

            {#if user}
                <Heading tag="h5" class="text-center">
                    <Span class="text-orange-400">
                        {`${user.firstName} ${user.lastName}`}
                    </Span>
                </Heading>

                <Hr class="m-0 p-0"/>
            {/if}

            <SidebarDropdownWrapper class="list-none" classes={{ span: "font-bold" }} isOpen={true}
                                    label={m.sidebar_category_public()}>
                {#each itemsPublic as item}
                    <SidebarDropdownItem label={item.label} href={item.href}>
                        {#snippet icon()}
                            <svelte:component this={item.icon} class="text-orange-400 h-5 w-5"/>
                        {/snippet}
                    </SidebarDropdownItem>
                {/each}
            </SidebarDropdownWrapper>

            {#if user}
                <SidebarDropdownWrapper class="list-none" classes={{ span: "font-bold" }} isOpen={true}
                                        label={m.sidebar_category_user()}>
                    {#each itemsUser as item}
                        <SidebarDropdownItem label={item.label} href={item.href}>
                            {#snippet icon()}
                                <svelte:component this={item.icon} class="text-orange-400 h-5 w-5"/>
                            {/snippet}
                        </SidebarDropdownItem>
                    {/each}
                </SidebarDropdownWrapper>
            {/if}

            {#if user && user.role === "ADMINISTRATOR"}
                <SidebarDropdownWrapper class="list-none" classes={{ span: "font-bold" }} isOpen={true}
                                        label={m.sidebar_category_admin()}>
                    {#each itemsAdmin as item}
                        <SidebarDropdownItem label={item.label} href={item.href}>
                            {#snippet icon()}
                                <svelte:component this={item.icon} class="text-orange-400 h-5 w-5"/>
                            {/snippet}
                        </SidebarDropdownItem>
                    {/each}
                </SidebarDropdownWrapper>
            {/if}

            <SidebarDropdownWrapper class="list-none" classes={{ span: "font-bold" }} isOpen={true}
                                    label={m.sidebar_category_settings()}>
                <Select items={languages} bind:value={language} onchange={updateLanguage}/>
                <Toggle onchange={toggleDarkMode}>_toggle_darkmode</Toggle>
            </SidebarDropdownWrapper>
        </div>
    </Sidebar>

    <div class="w-full m-10">
        {@render children?.()}
    </div>

</div>

<Footer/>
