<script>
    import '../app.css';
    import {Sidebar, SidebarDropdownWrapper, SidebarDropdownItem, Hr} from 'flowbite-svelte';

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
        {value: "vi", name: "_vietnamese"}
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
        InfoCircleSolid
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

            <SidebarDropdownWrapper class="list-none" classes={{ span: "font-bold" }} isOpen={true}
                                    label={m.sidebar_category_public()}>
                <SidebarDropdownItem label={m.sidebar_home()} href="/">
                    {#snippet icon()}
                        <HomeSolid class="text-orange-400 h-5 w-5"/>
                    {/snippet}
                </SidebarDropdownItem>
                <SidebarDropdownItem label={m.sidebar_login()} href="/login">
                    {#snippet icon()}
                        <UserSolid class="text-orange-400 h-5 w-5"/>
                    {/snippet}
                </SidebarDropdownItem>
                <SidebarDropdownItem label={m.sidebar_register()} href="/register">
                    {#snippet icon()}
                        <UserSolid class="text-orange-400 h-5 w-5"/>
                    {/snippet}
                </SidebarDropdownItem>
                <SidebarDropdownItem label={m.sidebar_support()} href="/support">
                    {#snippet icon()}
                        <UserHeadsetSolid class="text-orange-400 h-5 w-5"/>
                    {/snippet}
                </SidebarDropdownItem>
                <SidebarDropdownItem label={m.sidebar_about()} href="/about">
                    {#snippet icon()}
                        <InfoCircleSolid class="text-orange-400 h-5 w-5"/>
                    {/snippet}
                </SidebarDropdownItem>
            </SidebarDropdownWrapper>

            {#if user}
                <SidebarDropdownWrapper class="list-none" classes={{ span: "font-bold" }} isOpen={true}
                                        label={m.sidebar_category_user()}>
                    <SidebarDropdownItem label={m.sidebar_dashboard()} href="/dashboard">
                        {#snippet icon()}
                            <NewspaperSolid class="text-orange-400 h-5 w-5"/>
                        {/snippet}
                    </SidebarDropdownItem>
                    <SidebarDropdownItem label={m.sidebar_profile()} href="/profile">
                        {#snippet icon()}
                            <UserSolid class="text-orange-400 h-5 w-5"/>
                        {/snippet}
                    </SidebarDropdownItem>
                    <SidebarDropdownItem label={m.sidebar_reservations()} href="/reservation">
                        {#snippet icon()}
                            <DesktopPcSolid class="text-orange-400 h-5 w-5"/>
                        {/snippet}
                    </SidebarDropdownItem>
                    <SidebarDropdownItem label={m.sidebar_bookings()} href="/bookings">
                        {#snippet icon()}
                            <CalendarMonthSolid class="text-orange-400 h-5 w-5"/>
                        {/snippet}
                    </SidebarDropdownItem>
                    <SidebarDropdownItem label={m.sidebar_logout()} href="/logout">
                        {#snippet icon()}
                            <UserSolid class="text-orange-400 h-5 w-5"/>
                        {/snippet}
                    </SidebarDropdownItem>
                </SidebarDropdownWrapper>

                <SidebarDropdownWrapper class="list-none" classes={{ span: "font-bold" }} isOpen={true}
                                        label={m.sidebar_category_admin()}>
                    <SidebarDropdownItem label={m.sidebar_manage_users()} href="/admin/users">
                        {#snippet icon()}
                            <UsersGroupSolid class="text-orange-400 h-5 w-5"/>
                        {/snippet}
                    </SidebarDropdownItem>
                    <SidebarDropdownItem label={m.sidebar_manage_bookings()} href="/admin/bookings">
                        {#snippet icon()}
                            <CalendarEditSolid class="text-orange-400 h-5 w-5"/>
                        {/snippet}
                    </SidebarDropdownItem>
                    <SidebarDropdownItem label={m.sidebar_manage_support_tickets()} href="/admin/support">
                        {#snippet icon()}
                            <UserHeadsetSolid class="text-orange-400 h-5 w-5"/>
                        {/snippet}
                    </SidebarDropdownItem>
                    <SidebarDropdownItem label={m.sidebar_manage_news()} href="/admin/news">
                        {#snippet icon()}
                            <NewspaperSolid class="text-orange-400 h-5 w-5"/>
                        {/snippet}
                    </SidebarDropdownItem>
                    <SidebarDropdownItem label={m.sidebar_admin_settings()} href="/admin/settings">
                        {#snippet icon()}
                            <UserSettingsSolid class="text-orange-400 h-5 w-5"/>
                        {/snippet}
                    </SidebarDropdownItem>
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
