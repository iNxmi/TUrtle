<script>
    import TUrtleLogo from "$lib/components/TUrtleLogo.svelte";
    import {m} from '$lib/paraglide/messages.js';
    import {goto} from '$app/navigation';
    import {page} from '$app/state';
    import request from '$lib/api/api';
    import {
        Button,
        ButtonGroup,
        Heading,
        Hr,
        Select,
        Sidebar,
        SidebarButton,
        SidebarDropdownItem,
        SidebarDropdownWrapper,
        SidebarGroup,
        Span
    } from 'flowbite-svelte';

    import {
        ArrowRightToBracketOutline,
        BookOpenSolid,
        CalendarEditSolid,
        CalendarMonthSolid,
        DesktopPcSolid,
        HomeSolid,
        InfoCircleSolid,
        LockOpenOutline,
        LockSolid,
        MoonOutline,
        PaperClipOutline,
        QuestionCircleSolid,
        SunOutline,
        UserHeadsetSolid,
        UsersGroupSolid
    } from 'flowbite-svelte-icons';

    const {user, permissions} = $props()
    let innerWidth = $state();

    const publicItems = [{
        label: m.navigation_public_home(),
        href: '/',
        icon: InfoCircleSolid
    }, {
        label: m.navigation_public_faq(),
        href: '/faq',
        icon: QuestionCircleSolid
    }, {
        label: m.navigation_public_contact(),
        href: '/contact',
        icon: UserHeadsetSolid
    }];

    const userItems = [{
        label: m.navigation_user_dashboard(),
        href: '/user/dashboard',
        icon: HomeSolid
    }, {
        label: m.navigation_user_item_bookings(),
        href: '/user/item-bookings',
        icon: DesktopPcSolid
    }, {
        permission: "REQUEST_ROOM_BOOKINGS",
        label: m.navigation_user_room_bookings(),
        href: '/user/room-bookings',
        icon: CalendarMonthSolid
    }];

    const manageItems = [{
        permission: "MANAGE_USERS",
        label: m.navigation_manage_users(),
        href: '/manage/users',
        icon: UsersGroupSolid
    }, {
        permission: "MANAGE_ROOM_BOOKINGS",
        label: m.navigation_manage_room_bookings(),
        href: '/manage/room-bookings',
        icon: CalendarEditSolid
    }, {
        permission: "MANAGE_ITEMS",
        label: m.navigation_manage_items(),
        href: '/manage/items',
        icon: DesktopPcSolid
    }, {
        permission: "MANAGE_ITEM_CATEGORIES",
        label: m.navigation_manage_item_categories(),
        href: '/manage/item-categories',
        icon: DesktopPcSolid
    }, {
        permission: "MANAGE_ITEM_BOOKINGS",
        label: m.navigation_manage_item_bookings(),
        href: '/manage/item-bookings',
        icon: DesktopPcSolid
    }, {
        permission: "MANAGE_SUPPORT_TICKETS",
        label: m.navigation_manage_support_tickets(),
        href: '/manage/support-tickets',
        icon: UserHeadsetSolid
    }, {
        permission: "MANAGE_AUDIT_LOGS",
        label: m.navigation_manage_audit_logs(),
        href: '/manage/audit-logs',
        icon: BookOpenSolid
    }, {
        permission: "MANAGE_SYSTEM_SETTINGS",
        label: m.navigation_manage_system_settings(),
        href: '/manage/system-settings',
        icon: LockSolid
    }, {
        permission: "MANAGE_GENERAL_TEMPLATES",
        label: m.navigation_manage_content_templates(),
        href: '/manage/content-templates',
        icon: PaperClipOutline
    }, {
        permission: "MANAGE_EMAIL_TEMPLATES",
        label: m.navigation_manage_email_templates(),
        href: '/manage/email-templates',
        icon: PaperClipOutline
    }];

    let language = $state("en");
    const languages = [
        {value: "en", name: "English"},
        {value: "de", name: "Deutsch"},
        {value: "ja", name: "日本語"},
        {value: "ar", name: "عربي"},
        {value: "ru", name: "Русский"},
        {value: "vi", name: "Tiếng Việt"},
        {value: "hu", name: "Magyar"},
        {value: "ro", name: "Română"}
    ];

    let darkmode = $state(localStorage.getItem('darkmode') || false);

    function toggleDarkMode() {
        darkmode = !darkmode;
        localStorage.setItem('darkmode', darkmode ? "true" : "");
        document.documentElement.classList.remove(darkmode ? 'light' : 'dark');
        document.documentElement.classList.add(darkmode ? 'dark' : 'light');
    }

    let activeUrl = $derived(page.url.pathname);
    let isOpen = $derived(!innerWidth || innerWidth >= 768);

    async function signOut() {
        const response = await request("/auth/logout", {method: "POST"});

        if (response.ok)
            goto('/', {invalidateAll: true});
    }
</script>

<svelte:window bind:innerWidth={innerWidth}/>
<SidebarButton onclick={() => isOpen = !isOpen} class="mb-2"/>
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

        {#if user !== null}
            <SidebarGroup>
                <Heading tag="h5" class="text-center">
                        <Span class="text-csw">
                            {`${user.firstName} ${user.lastName}`}
                        </Span>
                </Heading>
            </SidebarGroup>

            <Hr class="m-0 p-0"/>
        {/if}

        <SidebarDropdownWrapper
                class="list-none"
                classes={{ span: 'font-bold text-text', ul: 'py-0' }}
                isOpen={true}
                label={m.navigation_category_public()}
        >
            {#each publicItems as item}
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
                    label={m.navigation_category_user()}
            >
                {#each userItems as item}
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
                <SidebarDropdownWrapper class="list-none"
                                        classes={{ span: "font-bold text-text", ul: 'py-0' }}
                                        isOpen={true}
                                        label={m.navigation_category_manage()}>
                    {#each manageItems as item}
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

        <SidebarGroup>
            <div class="flex flex-col gap-2">
                <ButtonGroup>
                    <Button color="alternative" class="w-1/2" onclick={toggleDarkMode}>
                        {#if darkmode}
                            <MoonOutline/>
                        {:else}
                            <SunOutline/>
                        {/if}
                    </Button>

                    <Button color="alternative" class="w-1/2">
                        <LockOpenOutline/>
                    </Button>
                </ButtonGroup>

                <Select items={languages} bind:value={language}/>

                {#if user === null}
                    <ButtonGroup>
                        <Button class="w-1/2" onclick={() => goto("/auth/login")}>{m.navigation_auth_login()}</Button>
                        <Button class="w-1/2"
                                onclick={() => goto("/auth/register")}>{m.navigation_auth_register()}</Button>
                    </ButtonGroup>
                {:else}
                    <ButtonGroup>
                        <Button class="w-full" onclick={signOut}>
                            <div class="flex gap-2">
                                <ArrowRightToBracketOutline/>
                                <Span>{m.navigation_auth_logout()}</Span>
                            </div>
                        </Button>
                    </ButtonGroup>
                {/if}
            </div>
        </SidebarGroup>
    </div>
</Sidebar>