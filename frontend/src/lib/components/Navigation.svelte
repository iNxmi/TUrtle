<script>
    import RegisterModal from "$lib/components/modal/RegisterModal.svelte"
    import LoginModal from "$lib/components/modal/LoginModal.svelte"
    import LogoutModal from "$lib/components/modal/LogoutModal.svelte"
    import OpenDoorModal from "$lib/components/modal/OpenDoorModal.svelte";
    import LanguageDropdown from "$lib/components/LanguageDropdown.svelte";
    import TUrtleLogo from "$lib/components/TUrtleLogo.svelte";
    import {m} from "$lib/paraglide/messages.js";
    import {page} from "$app/state";

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
    } from "flowbite-svelte";

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

    let loginModal = $state(false);
    let logoutModal = $state(false);
    let registerModal = $state(false);
    let openDoorModal = $state(false);
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
                                    <LanguageDropdown/>
                                    <LanguageOutline/>
                                </Button>
                            {/if}

                            {#if !hideDoorButton}
                                <Button name="button_open_door" color="alternative" class="flex-1"
                                        onclick={() => (openDoorModal = true)}>
                                    <LockOpenOutline/>
                                </Button>
                            {/if}
                        </ButtonGroup>
                    </div>
                {/if}

                {#if !hideAuthButtons}
                    {#if showLogoutButton}
                        <ButtonGroup className="flex">
                            <Button name="button_logout" class="flex-1" onclick={() => logoutModal = true}>
                                <div class="flex gap-2 items-center">
                                    <ArrowRightToBracketOutline/>
                                    <Span>{m.main_navigation_button_logout()}</Span>
                                </div>
                            </Button>
                        </ButtonGroup>
                    {:else}
                        <ButtonGroup className="flex">
                            <Button name="button_login" class="flex-1" onclick={() => loginModal = true}>
                                <div class="flex gap-2 items-center">
                                    <ArrowLeftToBracketOutline/>
                                    <Span>{m.main_navigation_button_login()}</Span>
                                </div>
                            </Button>
                            <Button name="button_register" class="flex-1" onclick={() => registerModal = true}>
                                <div class="flex gap-2 items-center">
                                    <UserAddOutline/>
                                    <Span>{m.main_navigation_button_register()}</Span>
                                </div>
                            </Button>
                        </ButtonGroup>
                    {/if}
                {/if}
            </div>
        </SidebarGroup>
    {/if}
</Sidebar>

<LoginModal bind:open={loginModal}/>
<LogoutModal bind:open={logoutModal}/>
<RegisterModal bind:open={registerModal}/>
<OpenDoorModal bind:open={openDoorModal}/>