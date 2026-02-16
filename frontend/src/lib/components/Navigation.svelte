<script>
    import TUrtleLogo from "$lib/components/TUrtleLogo.svelte";
    import {m} from "$lib/paraglide/messages.js";
    import {goto} from "$app/navigation";
    import {page} from "$app/state";
    import request from "$lib/api/api";
    import {setContext} from "svelte";
    import {setLocale} from "$lib/paraglide/runtime.js";
    import {
        Button,
        ButtonGroup,
        Dropdown,
        DropdownItem,
        Heading,
        Hr,
        Sidebar,
        SidebarButton,
        SidebarDropdownItem,
        SidebarDropdownWrapper,
        SidebarGroup,
        Span
    } from "flowbite-svelte";

    import {Arab, De, Es, Fr, Hu, Jp, Ro, Ru, Us, Vi} from "svelte-flag-icons";

    import {
        AdjustmentsVerticalSolid,
        ArrowRightToBracketOutline,
        BookOpenSolid,
        BugSolid,
        CalendarEditSolid,
        ChartOutline,
        ClipboardSolid,
        FilePasteSolid,
        GlobeOutline,
        HomeSolid,
        LanguageOutline,
        LockOpenOutline,
        MoonOutline,
        PaperClipOutline,
        QuestionCircleSolid,
        SunOutline,
        TerminalOutline,
        UserCircleOutline,
        UserHeadsetSolid,
        UsersGroupSolid,
        UserSolid
    } from "flowbite-svelte-icons";

    const {user, permissions} = $props()

    const publicItems = [{
        label: m.navigation_public_home(),
        href: "/",
        icon: HomeSolid
    }, {
        label: m.navigation_public_faq(),
        href: "/faq",
        icon: QuestionCircleSolid
    }, {
        label: m.navigation_public_contact(),
        href: "/contact",
        icon: UserHeadsetSolid
    }];

    const userItems = [{
        label: m.navigation_user_dashboard(),
        href: "/user/dashboard",
        icon: ChartOutline
    }, {
        label: m.navigation_user_item_bookings(),
        href: "/user/item-bookings",
        icon: CalendarEditSolid
    }, {
        permission: "REQUEST_ROOM_BOOKINGS",
        label: m.navigation_user_room_bookings(),
        href: "/user/room-bookings",
        icon: CalendarEditSolid
    }, {
        label: m.navigation_user_profile(),
        href: "/user/profile",
        icon: UserSolid
    }].filter(item => !item.permission || permissions.includes(item.permission));

    const manageItems = [{
        permission: "MANAGE_USERS",
        label: m.navigation_manage_users(),
        href: "/manage/users",
        icon: UsersGroupSolid
    }, {
        permission: "MANAGE_ROLES",
        label: m.navigation_manage_roles(),
        href: "/manage/roles",
        icon: UsersGroupSolid
    }, {
        permission: "MANAGE_ITEMS",
        label: m.navigation_manage_items(),
        href: "/manage/items",
        icon: ClipboardSolid
    }, {
        permission: "MANAGE_ITEM_CATEGORIES",
        label: m.navigation_manage_item_categories(),
        href: "/manage/item-categories",
        icon: ClipboardSolid
    }, {
        permission: "MANAGE_ROOM_BOOKINGS",
        label: m.navigation_manage_room_bookings(),
        href: "/manage/room-bookings",
        icon: CalendarEditSolid
    }, {
        permission: "MANAGE_ITEM_BOOKINGS",
        label: m.navigation_manage_item_bookings(),
        href: "/manage/item-bookings",
        icon: CalendarEditSolid
    }, {
        permission: "MANAGE_AUDIT_LOGS",
        label: m.navigation_manage_audit_logs(),
        href: "/manage/audit-logs",
        icon: BookOpenSolid
    }, {
        permission: "MANAGE_GENERAL_TEMPLATES",
        label: m.navigation_manage_content_templates(),
        href: "/manage/content-templates",
        icon: FilePasteSolid
    }, {
        permission: "MANAGE_EMAIL_TEMPLATES",
        label: m.navigation_manage_email_templates(),
        href: "/manage/email-templates",
        icon: FilePasteSolid
    }, {
        permission: "MANAGE_LOCKERS",
        label: m.navigation_manage_lockers(),
        href: "/manage/lockers",
        icon: PaperClipOutline
    }, {
        permission: "MANAGE_SUPPORT_TICKETS",
        label: m.navigation_manage_support_tickets(),
        href: "/manage/support-tickets",
        icon: UserHeadsetSolid
    }, {
        permission: "MANAGE_EXCEPTIONS",
        label: m.navigation_manage_exceptions(),
        href: "/manage/exceptions",
        icon: BugSolid
    }, {
        permission: "MANAGE_SYSTEM_SETTINGS",
        label: m.navigation_manage_system_settings(),
        href: "/manage/system-settings",
        icon: AdjustmentsVerticalSolid
    }].filter(item => !item.permission || permissions.includes(item.permission));

    let categories = [{
        icon: GlobeOutline,
        label: m.navigation_category_public(),
        items: publicItems
    }, {
        icon: UserCircleOutline,
        label: m.navigation_category_user(),
        items: userItems
    }, {
        icon: TerminalOutline,
        label: m.navigation_category_manage(),
        items: manageItems
    }].filter(category => category.items.length > 0);

    let language = $state(localStorage.getItem("PARAGLIDE_LOCALE") || "en");
    setContext("locale", () => language);
    $effect(() => {
        if (language)
            setLocale(language);
    })

    const languageIcons = {
        "en": Us,
        "de": De,
        "ja": Jp,
        "ar": Arab,
        "ru": Ru,
        "vi": Vi,
        "hu": Hu,
        "ro": Ro,
        "fr": Fr,
        "es": Es,
    }

    const languages = [
        {value: "en", name: "English"},
        {value: "de", name: "Deutsch"},
        {value: "ja", name: "日本語"},
        {value: "ar", name: "عربي"},
        {value: "ru", name: "Русский"},
        {value: "vi", name: "Tiếng Việt"},
        {value: "hu", name: "Magyar"},
        {value: "ro", name: "Română"},
        {value: "fr", name: "Français"},
        {value: "es", name: "Español"}
    ].sort((a, b) => a.value.localeCompare(b.value));

    let darkmode = $state(localStorage.getItem("darkmode") || false);

    function toggleDarkMode() {
        darkmode = !darkmode;
        localStorage.setItem("darkmode", darkmode ? "true" : "");
        document.documentElement.classList.remove(darkmode ? "light" : "dark");
        document.documentElement.classList.add(darkmode ? "dark" : "light");
    }

    let activeUrl = $derived(page.url.pathname);
    let innerWidth = $state();
    let isOpen = $derived(!innerWidth || innerWidth >= 768);

    async function signOut() {
        const response = await request("/auth/logout", {method: "POST"});

        if (response.ok)
            await goto("/", {invalidateAll: true});
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

        {#each categories as category}
            <SidebarDropdownWrapper
                    class="list-none"
                    classes={{ span: "font-bold text-text" }}
                    isOpen={true}
                    label={category.label}
            >
                {#snippet icon()}
                    <category.icon class="text-text"/>
                {/snippet}
                {#each category.items as item}
                    <SidebarDropdownItem href={item.href}>
                        {#snippet icon()}
                            <item.icon class="text-csw"/>
                        {/snippet}
                        {#snippet subtext()}
                            <div class="w-full flex">
                                <span class="text-text grow">{item.label}</span>

                                {#if item.ping === true}
                                    <div class="shrink flex flex-col justify-center">
                                        <div class="flex">
                                            <span class="absolute bg-csw h-3 w-3 rounded-full animate-ping opacity-75"></span>
                                            <span class="relative bg-csw h-3 w-3 rounded-full"></span>
                                        </div>
                                    </div>
                                {/if}
                            </div>
                        {/snippet}
                    </SidebarDropdownItem>
                {/each}
            </SidebarDropdownWrapper>
        {/each}

        <Hr class="m-0 p-0"/>

        <SidebarGroup>
            <div class="flex flex-col gap-2">
                <div class="flex flex-col">
                    <ButtonGroup>
                        <Button name="button_toggle_theme" color="alternative" class="w-1/3" onclick={toggleDarkMode}>
                            {#if darkmode}
                                <MoonOutline/>
                            {:else}
                                <SunOutline/>
                            {/if}
                        </Button>

                        <Button name="button_change_language" color="alternative" class="w-1/3">
                            <!--                            <svelte:component this={languageIcons[language]}/>-->
                            <LanguageOutline/>
                        </Button>
                        <Dropdown class="h-64 overflow-y-auto" simple>
                            {#each languages as language}
                                <DropdownItem class="w-full" onclick={() => setLocale(language.value)}>
                                    <div class="flex gap-2 items-center">
                                        <svelte:component this={languageIcons[language.value]} class="m-0 p-0"/>
                                        <span class="text-center">{language.name}</span>
                                    </div>
                                </DropdownItem>
                            {/each}
                        </Dropdown>

                        <Button name="button_open_door" color="alternative" class="w-1/3">
                            <LockOpenOutline/>
                        </Button>
                    </ButtonGroup>
                </div>

                {#if user === null}
                    <ButtonGroup>
                        <Button name="button_login" class="w-1/2" onclick={() => goto("/auth/login")}>
                            {m.navigation_auth_login()}
                        </Button>
                        <Button name="button_register" class="w-1/2" onclick={() => goto("/auth/register")}>
                            {m.navigation_auth_register()}
                        </Button>
                    </ButtonGroup>
                {:else}
                    <ButtonGroup>
                        <Button name="button_logout" class="w-full" onclick={signOut}>
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