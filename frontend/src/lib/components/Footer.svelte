<script>
    import {Footer, FooterCopyright, FooterIcon, FooterLink, FooterLinkGroup} from "flowbite-svelte";
    import {GithubSolid, InstagramSolid, TwitterSolid} from "flowbite-svelte-icons";
    import {m} from '$lib/paraglide/messages.js';
</script>

<Footer class="border-none rounded-none">
    <FooterCopyright href="/" by="CSW TUrtle" year={2025}/>
    <FooterLinkGroup class="mt-3 flex flex-wrap items-center text-sm text-gray-500 sm:mt-0 dark:text-gray-400">
        <FooterLink href="/imprint">{m.footer_imprint()}</FooterLink>
        <FooterLink href="/tos">{m.footer_tos()}</FooterLink>
        <FooterLink href="/gdpr">{m.footer_gdpr()}</FooterLink>
        <FooterLink href="/about">{m.footer_about()}</FooterLink>
        <FooterLink href="/contact">{m.footer_contact()}</FooterLink>
    </FooterLinkGroup>
    <div class="flex gap-5">
        <FooterIcon href="https://github.com/CSWTeam/TUrtle" target="_blank">
            <GithubSolid class="h-5 w-5 text-gray-500 hover:text-gray-900 dark:text-gray-500 dark:hover:text-white"/>
        </FooterIcon>
        <FooterIcon href="https://www.instagram.com/csw_tudarmstadt/" target="_blank">
            <InstagramSolid class="h-5 w-5 text-gray-500 hover:text-gray-900 dark:text-gray-500 dark:hover:text-white"/>
        </FooterIcon>
        <FooterIcon href="https://twitter.com/CSW_TUDarmstadt" target="_blank">
            <TwitterSolid class="h-5 w-5 text-gray-500 hover:text-gray-900 dark:text-gray-500 dark:hover:text-white"/>
        </FooterIcon>
    </div>
</Footer>