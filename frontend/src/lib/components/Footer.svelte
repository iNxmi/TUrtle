<script>
    import {A} from "flowbite-svelte";
    import {GithubSolid, InstagramSolid, XSolid} from "flowbite-svelte-icons";
    import {m} from '$lib/paraglide/messages.js';
</script>

<div class="flex justify-between gap-5 p-5 text-sm bg-background-secondary">
    <A class="flex-1 text-gray-500 hover:text-gray-900 dark:text-gray-500 dark:hover:text-white" href="/">© 2025 CSW TUrtle All Rights Reserved.</A>

    <div class="flex flex-1 gap-x-4 justify-around flex-wrap">
        <A class="text-gray-500 hover:text-gray-900 dark:text-gray-500 dark:hover:text-white" href="/imprint">{m.footer_imprint()}</A>
        <A class="text-gray-500 hover:text-gray-900 dark:text-gray-500 dark:hover:text-white" href="/tos">{m.footer_tos()}</A>
        <A class="text-gray-500 hover:text-gray-900 dark:text-gray-500 dark:hover:text-white" href="/gdpr">{m.footer_gdpr()}</A>
        <A class="text-gray-500 hover:text-gray-900 dark:text-gray-500 dark:hover:text-white" href="/about">{m.footer_about()}</A>
        <A class="text-gray-500 hover:text-gray-900 dark:text-gray-500 dark:hover:text-white" href="/faq">_FAQ_</A>
        <A class="text-gray-500 hover:text-gray-900 dark:text-gray-500 dark:hover:text-white" href="/contact">{m.footer_contact()}</A>
    </div>

    <!--    TODO make as system setting-->
    <div class="flex flex-1 gap-5 justify-end">
        <A class="text-gray-500 hover:text-gray-900 dark:text-gray-500 dark:hover:text-white" href="https://github.com/CSWTeam/TUrtle" target="_blank">
            <GithubSolid/>
        </A>
        <A class="text-gray-500 hover:text-gray-900 dark:text-gray-500 dark:hover:text-white" href="https://www.instagram.com/csw_tudarmstadt/" target="_blank">
            <InstagramSolid/>
        </A>
        <A class="text-gray-500 hover:text-gray-900 dark:text-gray-500 dark:hover:text-white" href="https://twitter.com/CSW_TUDarmstadt" target="_blank">
            <XSolid/>
        </A>
    </div>
</div>