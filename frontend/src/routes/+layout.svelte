<script>
    import '../app.css';
    import {onMount} from 'svelte';
    import favicon from '$lib/assets/favicon.svg';

    import {Select, Button} from 'flowbite-svelte';

    import {m} from '$lib/paraglide/messages.js';

    import {setLocale} from '$lib/paraglide/runtime.js';

    const languages = [
        {value: "en", name: "English"},
        {value: "de", name: "Deutsch"},
        {value: "ja", name: "日本語"},
        {value: "ar", name: "_arabic"},
        {value: "ru", name: "_russian"},
        {value: "vi", name: "_vietnamese"}
    ];
    let language = "en";

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
        SunSolid,
        MoonSolid
    } from 'flowbite-svelte-icons';
    import {page} from '$app/state';
    import {
        Sidebar,
        SidebarGroup,
        SidebarItem,
        SidebarButton,
        SidebarDropdownWrapper,
        SidebarDropdownItem,
        SidebarBrand,
        uiHelpers,
        ThemeProvider,
        sidebar,
        divider
        ,

    } from 'flowbite-svelte';

    let activeUrl = $state(page.url.pathname);
    const demoSidebarUi = uiHelpers();
    let isDemoOpen = $state(false);
    const closeDemoSidebar = demoSidebarUi.close;
    let darkmode = $state(false);

    $effect(() => {
        isDemoOpen = demoSidebarUi.isOpen;
        activeUrl = page.url.pathname;
    });
    onMount(() => {
        if (document.documentElement.className === 'dark') {
            darkmode = true;
        }
    });
    const theme = {
        sidebar: {
            base: 'bg-secondary dark:bg-secondary'
        }
    };
    const spanClass = 'flex-1 ms-3 whitespace-nowrap';
    const activeClass =
        'flex items-center p-2 text-base font-normal text-white bg-primary-600 dark:bg-primary-700 rounded-lg dark:text-white hover:bg-primary-800 dark:hover:bg-primary-800';
    const nonActiveClass =
        'flex items-center p-2 text-base font-normal text-green-900 rounded-lg dark:text-white hover:bg-green-100 dark:hover:bg-green-700';

    import {Accordion, AccordionItem} from 'flowbite-svelte';

    let {children} = $props();

    function toggleDarkMode() {
        document.documentElement.classList.remove(darkmode ? "dark" : "light");
        document.documentElement.classList.add(darkmode ? "light" : "dark");
        darkmode = !darkmode;
    }

    function rotateLogo() {
        const animation = "animate-spin";
        const element = document.getElementById("logo")

        element.classList.add(animation);
        setTimeout(() => {
            element.classList.remove(animation);
        }, 1000);
    }
</script>

<svelte:head>
    <link rel="icon" href={favicon}/>
</svelte:head>

<!-- <div class="min-h-screen">
 -->
<div class="h-full flex flex-col">
    <div class="flex-1 overflow-auto">
        <div class="h-screen overflow-hidden">
            <ThemeProvider {theme}>
                <Sidebar
                        alwaysOpen
                        {activeUrl}
                        isSingle={false}
                        backdrop={false}
                        isOpen={isDemoOpen}
                        closeSidebar={closeDemoSidebar}
                        params={{ x: -50, duration: 50 }}
                        classes={{ nonactive: nonActiveClass, active: activeClass }}
                        position="absolute"
                        class="h-full border-r border-table-dark"
                >
                    <button class="flex items-center m-5" onclick={rotateLogo}>
                        <img id="logo" src="csw_cropped.png" alt="CSW Icon"/>
                    </button>

                    <Select items={languages} bind:value={language} onchange={updateLanguage}/>

                    <SidebarDropdownWrapper classes={{ span: "font-bold" }} isOpen={true}
                                            label={m.sidebar_category_public()}>
                        <SidebarDropdownItem label={m.sidebar_login()} href="/login">
                            {#snippet icon()}
                                <UserSolid class="text-green-700 h-5 w-5"/>
                            {/snippet}
                        </SidebarDropdownItem>
                        <SidebarDropdownItem label={m.sidebar_logout()} href="/logout">
                            {#snippet icon()}
                                <UserSolid class="text-green-700 h-5 w-5"/>
                            {/snippet}
                        </SidebarDropdownItem>
                        <SidebarDropdownItem label={m.sidebar_register()} href="/register">
                            {#snippet icon()}
                                <UserSolid class="text-green-700 h-5 w-5"/>
                            {/snippet}
                        </SidebarDropdownItem>
                        <SidebarDropdownItem label={m.sidebar_support()} href="/support">
                            {#snippet icon()}
                                <UserHeadsetSolid class="text-green-700 h-5 w-5"/>
                            {/snippet}
                        </SidebarDropdownItem>
                    </SidebarDropdownWrapper>

                    <SidebarDropdownWrapper classes={{ span: "font-bold" }} isOpen={true}
                                            label={m.sidebar_category_user()}>
                        <SidebarDropdownItem label={m.sidebar_dashboard()} href="/">
                            {#snippet icon()}
                                <NewspaperSolid class="text-green-700 h-5 w-5"/>
                            {/snippet}
                        </SidebarDropdownItem>
                        <SidebarDropdownItem label={m.sidebar_profile()} href="/profile">
                            {#snippet icon()}
                                <UserSolid class="text-green-700 h-5 w-5"/>
                            {/snippet}
                        </SidebarDropdownItem>
                        <SidebarDropdownItem label={m.sidebar_reservations()} href="/reservation">
                            {#snippet icon()}
                                <DesktopPcSolid class="text-green-700 h-5 w-5"/>
                            {/snippet}
                        </SidebarDropdownItem>
                        <SidebarDropdownItem label={m.sidebar_bookings()} href="/bookings">
                            {#snippet icon()}
                                <CalendarMonthSolid class="text-green-700 h-5 w-5"/>
                            {/snippet}
                        </SidebarDropdownItem>
                    </SidebarDropdownWrapper>

                    <SidebarDropdownWrapper classes={{ span: "font-bold" }} isOpen={true}
                                            label={m.sidebar_category_admin()}>
                        <SidebarDropdownItem label={m.sidebar_manage_users()} {spanClass} href="/admin/users">
                            {#snippet icon()}
                                <UsersGroupSolid class="text-green-700 h-5 w-5"/>
                            {/snippet}
                        </SidebarDropdownItem>
                        <SidebarDropdownItem label={m.sidebar_manage_bookings()} href="/admin/bookings">
                            {#snippet icon()}
                                <CalendarEditSolid class="text-green-700 h-5 w-5"/>
                            {/snippet}
                        </SidebarDropdownItem>
                        <SidebarDropdownItem label={m.sidebar_manage_support_tickets()} href="/admin/support">
                            {#snippet icon()}
                                <UserHeadsetSolid class="text-green-700 h-5 w-5"/>
                            {/snippet}
                        </SidebarDropdownItem>
                        <SidebarDropdownItem label={m.sidebar_manage_news()} href="/admin/news">
                            {#snippet icon()}
                                <NewspaperSolid class="text-green-700 h-5 w-5"/>
                            {/snippet}
                        </SidebarDropdownItem>
                        <SidebarDropdownItem label={m.sidebar_admin_settings()} href="/admin/settings">
                            {#snippet icon()}
                                <UserSettingsSolid class="text-green-700 h-5 w-5"/>
                            {/snippet}
                        </SidebarDropdownItem>
                    </SidebarDropdownWrapper>
                </Sidebar>
            </ThemeProvider>

            <div class="absolute top-0 left-0 right-0 z-50 hidden md:block">
                <div class="absolute inset-0 bg-background/86 backdrop-blur-sm">
                    <!-- <div class="relative z-10 px-6 py-2"> -->
                    <div class="container mx-auto max-w-full p-8">
                        <div class="h-16 flex justify-end">
                            <button
                                    onclick={toggleDarkMode}
                                    class="cursor-pointer inline-flex items-center justify-center whitespace-nowrap"
                            >
                                {#if darkmode}
                                    <SunSolid class="text-accent h-10 w-10 transition duration-75"/>
                                {:else}
                                    <MoonSolid class="text-accent h-10 w-10 transition duration-75"/>
                                {/if}
                            </button>
                        </div>
                        <!-- </div> -->
                    </div>
                </div>
            </div>

            <main class="overflow-auto px-4 md:ml-64 bg-background-50 min-h-screen">
                <div class="container mx-auto max-w-12xl mb-12 md:pt-20">
                    {@render children?.()}
                </div>
            </main>

        </div>
    </div>
</div>
