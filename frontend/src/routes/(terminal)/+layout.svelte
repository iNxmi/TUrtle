<script>
    import Navigation from "$lib/components/Navigation.svelte";
    import {
        QrCodeOutline,
        EyeOutline,
        OpenDoorOutline,
        ExclamationCircleOutline,
        QuestionCircleOutline,
        UserHeadsetSolid,
        AppleFullOutline
    } from "flowbite-svelte-icons";
    import {m} from "$lib/paraglide/messages.js";

    let {data, children} = $props();
    let permissions = $derived(data.permissions);
    let user = $derived(data.user);

    const openItems = [{
        label: m.terminal_navigation_item_emojis(),
        href: "/terminal/emojis",
        icon: AppleFullOutline
    }, {
        label: m.terminal_navigation_item_counter_strike(),
        href: "/terminal/counter-strike",
        icon: UserHeadsetSolid
    }, {
        label: m.terminal_navigation_item_qr_code(),
        href: "/terminal/qr-code",
        icon: QrCodeOutline
    }, {
        label: m.terminal_navigation_item_retina_scan(),
        href: "/terminal/retina-scan",
        icon: EyeOutline
    }];

    const helpItems = [{
        label: m.terminal_navigation_item_report(),
        href: "/terminal/report",
        icon: ExclamationCircleOutline
    }];

    let categories = [{
        icon: OpenDoorOutline ,
        label: m.terminal_navigation_category_open(),
        items: openItems
    }, {
        icon: QuestionCircleOutline,
        label: m.terminal_navigation_category_help(),
        items: helpItems
    }].filter(category => category.items.length > 0);
</script>

<div class="flex">
    <Navigation
            logoRedirect="/terminal/emojis"
            categories={categories}
            hideDoorButton
            hideAuthButtons
    />

    <div class="grow max-h-svh bg-background overflow-y-auto p-5">
        {@render children?.()}
    </div>
</div>