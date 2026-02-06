<script>
    import {page} from '$app/state';
    import {setContext} from 'svelte';

    let {data, children} = $props();  
    let permissions = $derived(data.userPermissions);
    let user = $derived(data.user);

    import {
        Heading,
        Hr,
        Select,
        Sidebar,
        SidebarButton,
        SidebarDropdownItem,
        SidebarDropdownWrapper,
        Span,
        Toggle,
        Dropdown,
        DropdownItem,
        DropdownDivider,
        Avatar,
        Radio,
        RadioButton
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
        PaperClipOutline,
        NewspaperSolid,
        UserHeadsetSolid,
        UserSettingsSolid,
        UsersGroupSolid,
        UserSolid,
        LockSolid,
        QuestionCircleSolid,
        SunOutline,
        MoonOutline,
        LanguageOutline,
        CheckOutline
    } from 'flowbite-svelte-icons';
	import request from '$lib/api/api';
	import { goto } from '$app/navigation';

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
        if(language){
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
        localStorage.setItem('darkmode', darkmode? "true": "");
        document.documentElement.classList.remove(darkmode ? 'light' : 'dark');
        document.documentElement.classList.add(darkmode ? 'dark' : 'light');
        
        /* document.documentElement.data */
    }

   async function logOut(){
        const response = await request('/auth/logout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        });
        if(response.ok){
            goto('/',{invalidateAll: true});
        }

    }

    let innerWidth = $state();
    let isOpen = $derived(!innerWidth || innerWidth >= 768);

    const itemsPublic = [{
        permission: "VIEW__SIDEBAR_ITEM__HOME",
        label: m.sidebar_public_home(),
        href: '/',
        icon: HomeSolid
    }, {
        permission: "VIEW__SIDEBAR_ITEM__LOGIN",
        label: m.sidebar_public_login(),
        href: '/auth/login',
        icon: UserSolid
    }, {
        permission: "VIEW__SIDEBAR_ITEM__REGISTER",
        label: m.sidebar_public_register(),
        href: '/auth/register',
        icon: UserSolid
    }, {
        permission: "VIEW__SIDEBAR_ITEM__FAQ",
        label: m.sidebar_public_faq(),
        href: '/faq',
        icon: QuestionCircleSolid
    }];
    const itemsUser = [{
        permission: "VIEW__SIDEBAR_ITEM__DASHBOARD",
        label: m.sidebar_user_dashboard(),
        href: '/dashboard',
        icon: NewspaperSolid
    }, /* {
        permission: "VIEW__SIDEBAR_ITEM__PROFILE",
        label: m.sidebar_user_profile(),
        href: '/user/profile',
        icon: UserSolid
    }, */ {
        permission: "VIEW__SIDEBAR_ITEM__BOOK_DEVICE",
        label: m.sidebar_user_reservations(),
        href: '/items',
        icon: DesktopPcSolid
    }, {
        permission: "VIEW__SIDEBAR_ITEM__BOOK_ROOM",
        label: m.sidebar_user_bookings(),
        href: '/room',
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
        href: '/admin/bookings',
        icon: CalendarEditSolid
    }, {
        permission: ['MANAGE_ITEMS', 'MANAGE_ITEM_CATEGORIES'],
        label: '_Manage Devices',
        href: '/admin/devices',
        icon: DesktopPcSolid

    }, {
        permission: "MANAGE_SUPPORT_TICKETS",
        label: m.sidebar_admin_manage_support_tickets(),
        href: '/admin/support',
        icon: UserHeadsetSolid
    }, {
        permission: "VIEW__SIDEBAR_ITEM__MANAGE_NEWS",
        label: m.sidebar_admin_manage_news(),
        href: '/admin/news',
        icon: NewspaperSolid
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

<SidebarButton onclick={() => isOpen = !isOpen} class="mb-2" />
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

            {#if user !== {} }
                <Heading tag="h5" class="text-center">
					<Span class="text-csw!">
						{`${user.firstName} ${user.lastName}`}
					</Span>
                </Heading>

                <Hr class="m-0 p-0"/>
            {/if}

           <!--  {#if permissions.includes("VIEW__SIDEBAR_CATEGORY__PUBLIC")} 
                <SidebarDropdownWrapper
                        class="list-none"
                        classes={{ span: 'font-bold text-text', ul: 'py-0' }}
                        isOpen={true}
                        label={m.sidebar_category_public()}
                >
                     {#each itemsPublic as item}
                        {#if permissions.includes(item.permission)}
                            <SidebarDropdownItem spanClass="text-text ms-3" label={m.sidebar_public_home()} href="/">
                                {#snippet icon()}
                                    <HomeSolid class="text-csw h-5 w-5"/>
                                {/snippet}
                            </SidebarDropdownItem>
                        {/if}
                    {/each}
                </SidebarDropdownWrapper> -->
           <!--  {/if} -->

            <!-- {#if permissions.includes("VIEW__SIDEBAR_CATEGORY__USER")} -->
                <SidebarDropdownWrapper
                        class="list-none"
                        classes={{ span: 'font-bold text-text', ul: 'py-0' }}
                        isOpen={true}
                        label={m.sidebar_category_user()}
                >
                    {#each itemsUser as item}
                        <!-- {#if permissions.includes(item.permission)} -->
                            <SidebarDropdownItem spanClass="text-text ms-3" label={item.label} href={item.href}>
                                {#snippet icon()}
                                    <item.icon class="text-csw h-5 w-5"/>
                                {/snippet}
                            </SidebarDropdownItem>
                       <!--  {/if} -->
                    {/each}
                </SidebarDropdownWrapper>
           <!--  {/if} -->

            {#if permissions}
                <SidebarDropdownWrapper class="list-none" classes={{ span: "font-bold text-text", ul: 'py-0' }} isOpen={true}
                                        label={m.sidebar_category_admin()}>
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
        </div>
    </Sidebar>

    <div class="min-h-svh justify-between flex flex-col w-full bg-background">
        <div class="mx-10 my-3 mb-2">
            <div class="flex justify-end items-center gap-2 mb-1">
                <button onclick={toggleDarkMode} class="cursor-pointer inline-flex items-center justify-center text-md dark:text-white ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-0 disabled:pointer-events-none disabled:opacity-50 hover:bg-gray-50 dark:hover:bg-gray-800 rounded-md px-3 h-8">
                    {#if darkmode}
                    <MoonOutline class="text-white mr-1" /> _Dunkel_
                    {:else}
                    <SunOutline class="mr-1"/> _Hell_
                    {/if}
                </button>
                    <button size="xl" class="sizes cursor-pointer inline-flex items-center justify-center ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-0 disabled:pointer-events-none border border-gray-300 border-input bg-transparent hover:bg-gray-50 px-3 relative h-10 w-10 rounded-full">
                        <span class="relative flex shrink-0 overflow-hidden rounded-full h-9 w-9">
                            <span class="flex h-full w-full items-center justify-center rounded-full bg-gray-100 dark:bg-gray-800 text-text">
                                {user.firstName.charAt(0).toUpperCase()+user.lastName.charAt(0).toUpperCase()}
                            </span>
                        </span>
                    </button>
                    <Dropdown simple>
                        <DropdownItem class="w-50" href='/user/profile'>_profile_</DropdownItem>
                        <DropdownItem><div class="flex items-center gap-2"><LanguageOutline /> _Language_</div></DropdownItem>
                        <Dropdown simple placement="right-start">
                            <li>
                                <RadioButton class="focus-within:ring-0 text-black bg-white hover:bg-white hover:text-orange-400" name="languageGroup" checkedClass="text-csw!" bind:group={language} value="en">Englisch</RadioButton>
                            </li>
                            <li>
                                <RadioButton class="focus-within:ring-0 text-black bg-white hover:bg-white hover:text-orange-400" name="languageGroup" checkedClass="text-csw!" bind:group={language} value="de">Deutsch</RadioButton>
                            </li>
                            <li>
                                <RadioButton class="focus-within:ring-0 text-black bg-white hover:bg-white hover:text-orange-400" name="languageGroup" checkedClass="text-csw!" bind:group={language} value="ja">日本語</RadioButton>
                            </li>
                            <li>
                                <RadioButton class="focus-within:ring-0 text-black bg-white hover:bg-white hover:text-orange-400" name="languageGroup" checkedClass="text-csw!" bind:group={language} value="ar">'_arabic'</RadioButton>
                            </li>
                            <li>
                                <RadioButton class="focus-within:ring-0 text-black bg-white hover:bg-white hover:text-orange-400" name="languageGroup" checkedClass="text-csw!" bind:group={language} value="ru">'_arabic'</RadioButton>
                            </li>
                            <li>
                                <RadioButton class="focus-within:ring-0 text-black bg-white hover:bg-white hover:text-orange-400" name="languageGroup" checkedClass="text-csw!" bind:group={language} value="vi">'_vietnamese'</RadioButton>
                            </li>
                            <li>
                                <RadioButton class="focus-within:ring-0 text-black bg-white hover:bg-white hover:text-orange-400" name="languageGroup" checkedClass="text-csw!" bind:group={language} value="hu">'_hungarian'</RadioButton>
                            </li>
                            <li>
                                <RadioButton on class="focus-within:ring-0 text-black bg-white hover:bg-white hover:text-orange-400" name="languageGroup" checkedClass="text-csw!" bind:group={language} value="ro">'_romanian'</RadioButton>
                            </li>
                        </Dropdown>
                        <DropdownDivider />
                        <DropdownItem class onclick={logOut}><span class="text-red-600">_Log out_</span></DropdownItem>
                    </Dropdown>
            </div>
            {@render children?.()}
        </div>
        <Footer/>
    </div>
</div>

<svelte:window bind:innerWidth={innerWidth} />