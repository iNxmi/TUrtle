<script>
    import '../app.css';
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
        UserSolid
    } from 'flowbite-svelte-icons';

    import {page} from '$app/state';
    import {onMount} from 'svelte';

    let {data, children} = $props();
    const user = data.user;
    const roles = data.roles;
    alert(JSON.stringify(roles, null, 4));

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

    let activeUrl = $state(page.url.pathname);
    $effect(() => {
        activeUrl = page.url.pathname;
    });

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
        icon: BookOpenSolid
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
                    <Span class="text-csw">
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
                            <item.icon class="text-csw h-5 w-5"/>
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
                                <item.icon class="text-csw h-5 w-5"/>
                            {/snippet}
                        </SidebarDropdownItem>
                    {/each}
                </SidebarDropdownWrapper>
            {/if}

            {#if user && roles.find(r => r.id === user.roles[0]).name === "Administrator"}
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
