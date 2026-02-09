<script>
    import {logOut} from '$lib/utils';
    import { goto } from '$app/navigation';
    import { DropdownItem, Dropdown, RadioButton, DropdownDivider} from 'flowbite-svelte';
    import {LanguageOutline} from 'flowbite-svelte-icons'

    let { language = $bindable(), user } = $props();
    async function logout(){
      const result = await logOut();
        
      if(result){
        goto('/',{invalidateAll: true});
      }
            
        }
</script>
<button size="xl" class="sizes cursor-pointer inline-flex items-center justify-center ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-0 disabled:pointer-events-none border border-gray-300 border-input bg-transparent hover:bg-gray-50 px-3 relative h-10 w-10 rounded-full">
    <span class="relative flex shrink-0 overflow-hidden rounded-full h-9 w-9">
        <span class="flex h-full w-full items-center justify-center rounded-full bg-gray-100 dark:bg-gray-800 text-text">
            {user.firstName.charAt(0).toUpperCase()+user.lastName.charAt(0).toUpperCase()}
        </span>
    </span>
</button>
<Dropdown simple>
    <DropdownItem class="w-50" href='/profile'>_profile_</DropdownItem>
    <DropdownItem><div class="flex items-center gap-2"><LanguageOutline /> _Language_</div></DropdownItem>
    <Dropdown simple placement="right-start">
        <li>
            <RadioButton class="focus-within:ring-0 text-black bg-white! dark:bg-gray-700! hover:bg-white hover:text-orange-400" name="languageGroup" checkedClass="text-csw!" bind:group={language} value="en">Englisch</RadioButton>
        </li>
        <li>
            <RadioButton class="focus-within:ring-0 text-black bg-white! dark:bg-gray-700! hover:bg-white hover:text-orange-400" name="languageGroup" checkedClass="text-csw!" bind:group={language} value="de">Deutsch</RadioButton>
        </li>
        <li>
            <RadioButton class="focus-within:ring-0 text-black bg-white! dark:bg-gray-700! hover:bg-white hover:text-orange-400" name="languageGroup" checkedClass="text-csw!" bind:group={language} value="ja">日本語</RadioButton>
        </li>
        <li>
            <RadioButton class="focus-within:ring-0 text-black bg-white! dark:bg-gray-700! hover:bg-white hover:text-orange-400" name="languageGroup" checkedClass="text-csw!" bind:group={language} value="ar">'_arabic'</RadioButton>
        </li>
        <li>
            <RadioButton class="focus-within:ring-0 text-black bg-white! dark:bg-gray-700! hover:bg-white hover:text-orange-400" name="languageGroup" checkedClass="text-csw!" bind:group={language} value="ru">'_arabic'</RadioButton>
        </li>
        <li>
            <RadioButton class="focus-within:ring-0 text-black bg-white! dark:bg-gray-700! hover:bg-white hover:text-orange-400" name="languageGroup" checkedClass="text-csw!" bind:group={language} value="vi">'_vietnamese'</RadioButton>
        </li>
        <li>
            <RadioButton class="focus-within:ring-0 text-black bg-white! dark:bg-gray-700! hover:bg-white hover:text-orange-400" name="languageGroup" checkedClass="text-csw!" bind:group={language} value="hu">'_hungarian'</RadioButton>
        </li>
        <li>
            <RadioButton class="focus-within:ring-0 text-black bg-white! dark:bg-gray-700! hover:bg-white hover:text-orange-400" name="languageGroup" checkedClass="text-csw!" bind:group={language} value="ro">'_romanian'</RadioButton>
        </li>
    </Dropdown>
    <DropdownDivider />
    <DropdownItem class onclick={logout}><span class="text-red-600">_Log out_</span></DropdownItem>
</Dropdown>