<script>
    import RegisterModal from "$lib/components/modal/auth/RegisterModal.svelte"
    import LoginModal from "$lib/components/modal/auth/LoginModal.svelte"
    import LogoutModal from "$lib/components/modal/auth/LogoutModal.svelte"
    import UnlockDoorModal from "$lib/components/modal/UnlockDoorModal.svelte";
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
        SidebarDropdownItem,
        SidebarDropdownWrapper,
        SidebarGroup,
        Span,
        Tooltip
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
	import { resolve } from "$app/paths";

    let {
        logoRedirect = "/",
        header = null,
        categories = [],

        isTrusted = false,
        isLocalNetwork = false,

        hideDoorButton = false,
        hideThemeButton = false,
        hideLanguageButton = false,
        hideAuthButtons = false,
        showLogoutButton = false
    } = $props()

    let darkmode = $state(localStorage.getItem("darkmode") || true);

    function toggleDarkMode() {
        darkmode = !darkmode;
        localStorage.setItem("darkmode", darkmode ? "true" : "");
        document.documentElement.classList.remove(darkmode ? "light" : "dark");
        document.documentElement.classList.add(darkmode ? "dark" : "light");
    }

    let activeUrl = $derived(page.url.pathname);

    let loginModal = $state(false);
    let logoutModal = $state(false);
    let registerModal = $state(false);
    let openDoorModal = $state(false);
</script>

<Sidebar activeUrl={activeUrl}
         isOpen
         isSingle={false}
         alwaysOpen
         backdrop={false}
         position="static"
         class="min-w-68 min-h-svh max-h-svh shadow-md/30"
         classes={{div: "flex flex-col gap-2 h-full"}}
>
    <div class="flex flex-col items-center justify-between">
        <TUrtleLogo path={logoRedirect}/>
    </div>

    <Hr class="m-0 p-0"/>

    {#if header !== null}
        <SidebarGroup>
            <Heading tag="h5" class="text-center">
                <a href={resolve("/user/profile")}><Span class="text-csw">{header}</Span></a>
            </Heading>
        </SidebarGroup>

        <Hr class="m-0 p-0"/>
    {/if}

    <div class="flex flex-col gap-2 overflow-y-auto overflow-x-clip">
        {#each categories as category (category.label)}
            <SidebarDropdownWrapper
                    class="list-none"
                    classes={{ span: "font-bold text-text" }}
                    isOpen={true}
                    label={category.label}
            >
                {#snippet icon()}
                    <category.icon class="text-text"/>
                {/snippet}
                {#each category.items as item (item.label)}
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
                                <Button id="doorButton" disabled={!isLocalNetwork} name="button_open_door"
                                        color="alternative" class="flex-1" onclick={() => (openDoorModal = true)}>
                                    <LockOpenOutline/>
                                </Button>
                            {/if}
                        </ButtonGroup>
                        {#if !isLocalNetwork}
                            <Tooltip triggeredBy="#doorButton">{m.modal_door_unlock_tooltip()}</Tooltip>
                        {/if}
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
                            <Button name="button_login" class="flex-1 px-3" onclick={() => loginModal = true}>
                                <div class="flex gap-1 items-center">
                                    <ArrowLeftToBracketOutline/>
                                    <Span>{m.main_navigation_button_login()}</Span>
                                </div>
                            </Button>
                            <Button name="button_register" class="flex-1 px-3" onclick={() => registerModal = true}>
                                <div class="flex gap-1 items-center">
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

{#if loginModal}
    <LoginModal bind:open={loginModal} isTrusted={isTrusted}/>
{/if}

{#if logoutModal}
    <LogoutModal bind:open={logoutModal}/>
{/if}

{#if registerModal}
    <RegisterModal bind:open={registerModal} isTrusted={isTrusted}/>
{/if}

{#if openDoorModal}
    <UnlockDoorModal bind:open={openDoorModal}/>
{/if}