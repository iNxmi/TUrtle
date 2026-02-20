<script>
    import TUrtleLogo from "$lib/components/TUrtleLogo.svelte";
    import {m} from "$lib/paraglide/messages.js";
    import {goto} from "$app/navigation";
    import {page} from "$app/state";
    import request from "$lib/api/api";
    import {setContext} from "svelte";
    import {setLocale} from "$lib/paraglide/runtime.js";
    import {Modal} from "flowbite-svelte";
    import {QuestionCircleSolid} from "flowbite-svelte-icons";
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
        ArrowLeftToBracketOutline,
        ArrowRightToBracketOutline,
        LanguageOutline,
        LockOpenOutline,
        MoonOutline,
        SunOutline,
        UserAddOutline
    } from "flowbite-svelte-icons";

    const {
        logoRedirect = "/",
        header = null,
        categories = [],

        hideDoorButton = false,
        hideThemeButton = false,
        hideLanguageButton = false,
        hideAuthButtons = false,
        showLogoutButton = false
    } = $props()

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

    async function handleDoorModal(action) {
        if (action === "yes") {
            request("/hardware/door", {method: "POST"});
        }
    }

    let doorModal = $state(false);
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
        class="min-w-64 min-h-svh max-h-svh shadow-md/30"
        divClass="flex flex-col gap-2 h-full"
>
    <div class="flex flex-col items-center justify-between">
        <TUrtleLogo path={logoRedirect}/>
    </div>

    <Hr class="m-0 p-0"/>

    {#if header !== null}
        <SidebarGroup>
            <Heading tag="h5" class="text-center">
                <Span class="text-csw">{header}</Span>
            </Heading>
        </SidebarGroup>

        <Hr class="m-0 p-0"/>
    {/if}

    <div class="flex flex-col gap-2 overflow-y-auto overflow-x-clip">
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
                                        <div class="flex relative">
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
    </div>

    {#if !hideThemeButton || !hideLanguageButton || !hideDoorButton || !hideAuthButtons}
        <SidebarGroup class="flex flex-col grow justify-end">
            <div class="flex flex-col gap-2 justify-around">
                {#if !hideThemeButton || !hideLanguageButton || !hideDoorButton}
                    <div class="flex flex-col">
                        <ButtonGroup className="flex">
                            {#if !hideThemeButton}
                                <Button name="button_toggle_theme" color="alternative" class="flex-1"
                                        onclick={toggleDarkMode}>
                                    {#if darkmode}
                                        <MoonOutline/>
                                    {:else}
                                        <SunOutline/>
                                    {/if}
                                </Button>
                            {/if}

                            {#if !hideLanguageButton}
                                <Button name="button_change_language" color="alternative" class="flex-1">
                                    <LanguageOutline/>
                                    <Dropdown class="h-64 overflow-y-auto" simple>
                                        {#each languages as language}
                                            <DropdownItem class="w-full" onclick={() => setLocale(language.value)}>
                                                <div class="flex gap-2 items-center">
                                                    <svelte:component this={languageIcons[language.value]}
                                                                      class="m-0 p-0"/>
                                                    <span class="text-center">{language.name}</span>
                                                </div>
                                            </DropdownItem>
                                        {/each}
                                    </Dropdown>
                                </Button>
                            {/if}

                            {#if !hideDoorButton}
                                <Button name="button_open_door" color="alternative" class="flex-1"
                                        onclick={() => (doorModal = true)}>
                                    <LockOpenOutline/>
                                </Button>
                            {/if}
                        </ButtonGroup>
                    </div>
                {/if}

                {#if !hideAuthButtons}
                    {#if showLogoutButton}
                        <ButtonGroup className="flex">
                            <Button name="button_logout" class="flex-1" onclick={signOut}>
                                <div class="flex gap-2 items-center">
                                    <ArrowRightToBracketOutline/>
                                    <Span>{m.navigation_auth_logout()}</Span>
                                </div>
                            </Button>
                        </ButtonGroup>
                    {:else}
                        <ButtonGroup className="flex">
                            <Button name="button_login" class="flex-1" onclick={() => goto("/auth/login")}>
                                <div class="flex gap-2 items-center">
                                    <ArrowLeftToBracketOutline/>
                                    <Span>{m.navigation_auth_login()}</Span>
                                </div>
                            </Button>
                            <Button name="button_register" class="flex-1" onclick={() => goto("/auth/register")}>
                                <div class="flex gap-2 items-center">
                                    <UserAddOutline/>
                                    <Span>{m.navigation_auth_register()}</Span>
                                </div>
                            </Button>
                        </ButtonGroup>
                    {/if}
                {/if}
            </div>
        </SidebarGroup>
    {/if}
</Sidebar>

<Modal form bind:open={doorModal} onaction={({action}) => handleDoorModal(action) }>

    <div class="flex flex-col gap-5 align-center">
        <div class="flex gap-5 justify-center">
            <QuestionCircleSolid class="h-12 w-12"/>
            <div class="flex flex-col justify-center">
                <h1 class="text-lg font-bold">_Unlock the front door?_</h1>
            </div>
        </div>
        <h3 class="text-lg font-normal">_You will need to be logged into the local
            network ("csw_intern") for this action to work_</h3>
        <div class="flex gap-2 justify-center">
            <Button type="submit" value="yes">_Unlock Door_</Button>
            <Button type="submit" value="no">_Close_</Button>
        </div>
    </div>
</Modal>