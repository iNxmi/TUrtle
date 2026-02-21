<script>
    import {Arab, De, Es, Fr, Hu, Jp, Ro, Ru, Us, Vi} from "svelte-flag-icons";
    import {Dropdown, DropdownDivider, DropdownGroup, DropdownHeader, DropdownItem} from "flowbite-svelte";
    import {setLocale} from "$lib/paraglide/runtime.js";

    const languages = [
        {icon: Us, name: "English", value: "en"},
        {icon: De, name: "Deutsch", value: "de"},
        {icon: Jp, name: "日本語", value: "ja"},
        {icon: Arab, name: "عربي", value: "ar"},
        {icon: Ru, name: "Русский", value: "ru"},
        {icon: Vi, name: "Tiếng Việt", value: "vi"},
        {icon: Hu, name: "Magyar", value: "hu"},
        {icon: Ro, name: "Română", value: "ro"},
        {icon: Fr, name: "Français", value: "fr"},
        {icon: Es, name: "Español", value: "es"}
    ].sort((a, b) => a.value.localeCompare(b.value));

    let initialValue = localStorage.getItem("PARAGLIDE_LOCALE") || "en";
    let selected = $state(
        languages.find(item => item.value === initialValue)
    );
    $effect(async () => {
        if(!selected)
            return;

        setLocale(selected.value);
    })
</script>

<Dropdown simple>

    {#if selected}
        <DropdownHeader>
            <div class="flex gap-2 items-center">
                <selected.icon/>
                <span class="text-center">{selected.name}</span>
            </div>
        </DropdownHeader>

        <DropdownDivider class="m-0 p-0"/>
    {/if}

    <DropdownGroup class="h-48 overflow-y-auto">
        {#each languages.filter(item => item.value !== selected.value) as language}
            <DropdownItem class="w-full" onclick={() => selected = language}>
                <div class="flex gap-2 items-center">
                    <language.icon/>
                    <span class="text-center">{language.name}</span>
                </div>
            </DropdownItem>
        {/each}
    </DropdownGroup>
</Dropdown>