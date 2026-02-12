<script>
    import {page} from '$app/state';
    import {setContext} from 'svelte';
    import {
        Button,
        Heading,
        Hr,
        Sidebar,
        SidebarButton,
        SidebarDropdownItem,
        SidebarDropdownWrapper,
        Span
    } from 'flowbite-svelte';

    import {m} from '$lib/paraglide/messages.js';
    import {setLocale} from '$lib/paraglide/runtime.js';

    import TUrtleLogo from "$lib/components/TUrtleLogo.svelte";
    import Footer from "$lib/components/Footer.svelte";
    import ConfirmProvider from '$lib/components/ConfirmProvider.svelte';
    import ProfileDropdown from '$lib/components/ProfileDropdown.svelte';
    import {
        BookOpenSolid,
        CalendarEditSolid,
        CalendarMonthSolid,
        DesktopPcSolid,
        HomeSolid,
        LockSolid,
        MoonOutline,
        NewspaperSolid,
        PaperClipOutline,
        QuestionCircleSolid,
        SunOutline,
        UserHeadsetSolid,
        UsersGroupSolid,
        InfoCircleSolid,
    } from 'flowbite-svelte-icons';
    import request from '$lib/api/api';
    import {goto} from '$app/navigation';

    let {data, children} = $props();
    let permissions = $derived(data.permissions);
    let user = $derived(data.user);

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

    let language = $state(localStorage.getItem('PARAGLIDE_LOCALE') || 'de');

    setContext('locale', () => language);

    $effect(() => {
        if (language) {
            setLocale(language);
        }
    })

    function updateLanguage(event) {
        event.preventDefault();

        setLocale(language);
    }

    let activeUrl = $derived(page.url.pathname);

    let darkmode = $state(localStorage.getItem('darkmode') || false);

    /* onMount(() => {
        if (localStorage.getItem('darkmode')) darkmode = true;
    }); */

    function toggleDarkMode() {
        darkmode = !darkmode;
        localStorage.setItem('darkmode', darkmode ? "true" : "");
        document.documentElement.classList.remove(darkmode ? 'light' : 'dark');
        document.documentElement.classList.add(darkmode ? 'dark' : 'light');

        /* document.documentElement.data */
    }

    async function logOut() {
        const response = await request('/auth/logout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (response.ok)
            goto('/', {invalidateAll: true});
    }

    let innerWidth = $state();
    let isOpen = $derived(!innerWidth || innerWidth >= 768);

    const itemsPublic = [{
        label: "_about_",
        href: '/about',
        icon: InfoCircleSolid
    }, {
        label: m.sidebar_public_faq(),
        href: '/faq',
        icon: QuestionCircleSolid
    }, {
        label: "_contact_",
        href: '/contact',
        icon: UserHeadsetSolid
    }];

    const itemsUser = [{
        label: "_home_",
        href: '/home',
        icon: HomeSolid
    }, {
        label: "_item-bookings_",
        href: '/item-bookings',
        icon: DesktopPcSolid
    }, {
        permission: "REQUEST_ROOM_BOOKINGS",
        label: "_room-bookings_",
        href: '/room-bookings',
        icon: CalendarMonthSolid
    }];

    const itemsAdmin = [{
        permission: ["MANAGE_USERS", "MANAGE_ITEM_BOOKINGS"],
        label: m.sidebar_admin_manage_user_entities(),
        href: '/admin/userdata',
        icon: UsersGroupSolid
    }, {
        permission: "MANAGE_ROOM_BOOKINGS",
        label: m.sidebar_admin_manage_bookings(),
        href: '/admin/booking-requests',
        icon: CalendarEditSolid
    }, {
        permission: ['MANAGE_ITEMS', 'MANAGE_ITEM_CATEGORIES'],
        label: '_Manage Devices',
        href: '/admin/items',
        icon: DesktopPcSolid

    }, {
        permission: "MANAGE_SUPPORT_TICKETS",
        label: m.sidebar_admin_manage_support_tickets(),
        href: '/admin/support',
        icon: UserHeadsetSolid
    }, /* {
        permission: "VIEW__SIDEBAR_ITEM__HARDWARE_OVERRIDE",
        label: "_Hardware Override_",
        href: '/admin/hardware',
        icon: LockSolid
    }, */ {
        permission: "MANAGE_AUDIT_LOGS",
        label: m.sidebar_admin_auditlogs(),
        href: '/admin/auditlogs',
        icon: BookOpenSolid
    }, {
        permission: "MANAGE_SYSTEM_SETTINGS",
        label: m.sidebar_admin_settings(),
        href: '/admin/settings',
        icon: LockSolid
    }, {
        permission: "MANAGE_GENERAL_TEMPLATES",
        label: "_manage_templates_",
        href: '/admin/templates',
        icon: PaperClipOutline
    }];

</script>

<SidebarButton onclick={() => isOpen = !isOpen} class="mb-2"/>
<div class="flex">
    <Sidebar
            {activeUrl}
            isOpen={isOpen}
            isSingle={false}
            alwaysOpen={innerWidth > 768}
            backdrop={false}
            closeSidebar={() => isOpen = false}
            position="static"
            class="min-w-64 min-h-svh"
    >
        <div class="flex flex-col gap-2">
            <div class="flex flex-col items-center">
                <TUrtleLogo path="/"/>
            </div>

            <Hr class="m-0 p-0"/>

            {#if user !== null }
                <Heading tag="h5" class="text-center">
					<Span class="text-csw!">
						{`${user.firstName} ${user.lastName}`}
					</Span>
                </Heading>

                <Hr class="m-0 p-0"/>
            {/if}

            <SidebarDropdownWrapper
                    class="list-none"
                    classes={{ span: 'font-bold text-text', ul: 'py-0' }}
                    isOpen={true}
                    label={m.sidebar_category_public()}
            >
                {#each itemsPublic as item}
                    <SidebarDropdownItem spanClass="text-text ms-3" label={item.label} href={item.href}>
                        {#snippet icon()}
                            <item.icon class="text-csw h-5 w-5"/>
                        {/snippet}
                    </SidebarDropdownItem>
                {/each}
            </SidebarDropdownWrapper>

            {#if user !== null}
                <SidebarDropdownWrapper
                        class="list-none"
                        classes={{ span: 'font-bold text-text', ul: 'py-0' }}
                        isOpen={true}
                        label={m.sidebar_category_user()}
                >
                    {#each itemsUser as item}
                        {#if !item.permission || permissions.includes(item.permission)}
                            <SidebarDropdownItem spanClass="text-text ms-3" label={item.label} href={item.href}>
                                {#snippet icon()}
                                    <item.icon class="text-csw h-5 w-5"/>
                                {/snippet}
                            </SidebarDropdownItem>
                        {/if}
                    {/each}
                </SidebarDropdownWrapper>

                {#if permissions}
                    <SidebarDropdownWrapper class="list-none" classes={{ span: "font-bold text-text", ul: 'py-0' }}
                                            isOpen={true}
                                            label={"_manage_"}>
                        {#each itemsAdmin as item}
                            {#if Array.isArray(item.permission) && item.permission.every((permission) => permissions.includes(permission))}
                                <SidebarDropdownItem spanClass="text-text ms-3" label={item.label} href={item.href}>
                                    {#snippet icon()}
                                        <item.icon class="text-csw h-5 w-5"/>
                                    {/snippet}
                                </SidebarDropdownItem>
                            {:else if permissions.includes(item.permission)}
                                <SidebarDropdownItem spanClass="text-text ms-3" label={item.label} href={item.href}>
                                    {#snippet icon()}
                                        <item.icon class="text-csw h-5 w-5"/>
                                    {/snippet}
                                </SidebarDropdownItem>
                            {/if}
                        {/each}
                    </SidebarDropdownWrapper>
                {/if}
            {/if}
        </div>
    </Sidebar>

    <div class="min-h-svh justify-between flex flex-col w-full bg-background">
        <div class="m-5">
            <div class="flex justify-end items-center gap-2 mb-1">
                <button onclick={toggleDarkMode}
                        class="cursor-pointer inline-flex items-center justify-center text-md dark:text-white ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-0 disabled:pointer-events-none disabled:opacity-50 hover:bg-gray-50 dark:hover:bg-gray-800 rounded-md px-3 h-8">
                    {#if darkmode}
                        <MoonOutline class="text-white mr-1"/>
                    {:else}
                        <SunOutline class="mr-1"/>
                    {/if}
                </button>
                {#if user !== null}
                    <ProfileDropdown {language} {user}/>
                {:else}
                    <div class="flex gap-10">
                        <Button onclick={()=>goto("/auth/login")}>Login</Button>
                        <Button onclick={()=>goto("/auth/register")}>Register</Button>
                    </div>
                {/if}
            </div>
            <ConfirmProvider>
                {@render children?.()}
            </ConfirmProvider>
        </div>
        <Footer/>
    </div>
</div>

<svelte:window bind:innerWidth={innerWidth}/>