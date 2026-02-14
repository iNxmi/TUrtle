<script>
    import TUrtleLogo from "$lib/components/TUrtleLogo.svelte";
    import {m} from '$lib/paraglide/messages.js';
    import {goto} from '$app/navigation';
    import {page} from '$app/state';
    import request from '$lib/api/api';

    const {user, permissions} = $props()
    let innerWidth = $state();

    import {
        Button,
        ButtonGroup,
        Heading,
        Hr,
        Sidebar,
        SidebarButton,
        SidebarDropdownItem,
        SidebarDropdownWrapper,
        SidebarGroup,
        Span
    } from 'flowbite-svelte';

    import {
        BookOpenSolid,
        CalendarEditSolid,
        CalendarMonthSolid,
        DesktopPcSolid,
        GlobeOutline,
        HomeSolid,
        InfoCircleSolid,
        LockOpenOutline,
        LockSolid,
        MoonOutline,
        OpenDoorOutline,
        PaperClipOutline,
        QuestionCircleSolid,
        SunOutline,
        UserHeadsetSolid,
        UsersGroupSolid
    } from 'flowbite-svelte-icons';

    const publicItems = [{
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

    const userItems = [{
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

    const managerItems = [{
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
    }, {
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
                label={m.sidebar_category_public()}
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
                    label={m.sidebar_category_user()}
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
                                        label={"_manage_"}>
                    {#each managerItems as item}
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
                    <Button color="alternative" class="w-1/3" onclick={toggleDarkMode}>
                        {#if darkmode}
                            <MoonOutline/>
                        {:else}
                            <SunOutline/>
                        {/if}
                    </Button>
                    <Button color="alternative" class="w-1/3">
                        <GlobeOutline/>
                    </Button>
                    <Button color="alternative" class="w-1/3">
                        <LockOpenOutline/>
                    </Button>
                </ButtonGroup>

                {#if user === null}
                    <ButtonGroup>
                        <Button class="w-1/2" onclick={() => goto("/auth/login")}>Login</Button>
                        <Button class="w-1/2" onclick={() => goto("/auth/register")}>Register</Button>
                    </ButtonGroup>
                {:else}
                    <ButtonGroup>
                        <Button class="w-full" onclick={signOut}>
                            <div class="flex gap-2">
                                <OpenDoorOutline/>
                                <Span>_Sign out_</Span>
                            </div>
                        </Button>
                    </ButtonGroup>
                {/if}
            </div>
        </SidebarGroup>
    </div>
</Sidebar>