<script>
    import {Footer, FooterCopyright, FooterLinkGroup, FooterLink, FooterIcon} from "flowbite-svelte";
    import {GithubSolid, InstagramSolid, TwitterSolid} from "flowbite-svelte-icons";
</script>

<Footer>
    <FooterCopyright href="/" by="CSW TUrtle" year={2025}/>
    <FooterLinkGroup class="mt-3 flex flex-wrap items-center text-sm text-gray-500 sm:mt-0 dark:text-gray-400">
        <FooterLink href="/">_footer__about</FooterLink>
        <FooterLink href="/">_footer__privacy_policy</FooterLink>
        <FooterLink href="/">_footer__licensing</FooterLink>
        <FooterLink href="/">_footer__contact</FooterLink>
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