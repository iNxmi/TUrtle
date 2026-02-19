<script>
    import Navigation from "$lib/components/Navigation.svelte";
    import {GlobeOutline, HomeSolid, QuestionCircleSolid, UserHeadsetSolid} from "flowbite-svelte-icons";

    let {data, children} = $props();
    let permissions = $derived(data.permissions);
    let user = $derived(data.user);

    const openItems = [{
        label: "_emojis_",
        href: "/terminal/emojis",
        icon: HomeSolid
    }, {
        label: "_qr_",
        href: "/terminal/qr",
        icon: QuestionCircleSolid
    }, {
        label: "_retina_scan_",
        href: "/terminal",
        icon: UserHeadsetSolid
    }];

    const helpItems = [{
        label: "_report_",
        href: "/report",
        icon: HomeSolid
    }];

    let categories = [{
        icon: GlobeOutline,
        label: "_open_",
        items: openItems
    }, {
        icon: GlobeOutline,
        label: "_help_",
        items: helpItems
    }].filter(category => category.items.length > 0);
</script>

<div class="flex">
    <Navigation
            categories={categories}
            hideDoorButton
            hideAuthButtons
    />

    <div class="grow max-h-svh bg-background overflow-y-auto p-5">
        {@render children?.()}
    </div>
</div>