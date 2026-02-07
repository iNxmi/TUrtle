<script>
    import { setContext } from "svelte";
    import equal from 'fast-deep-equal';
    import { Badge, CloseButton, Checkbox, Input } from 'flowbite-svelte';


    let { users, onchange = false, value = $bindable(), single = false, sortFunction, displayFunction, filterFunction, disabled = false } = $props();
    let searchTerm = $state("");
    let clickProtected = $state(false);
    
    let filteredUsers = $derived(users.filter((user) => filterFunction(user, searchTerm)));

    let sortedUsers = $derived(filteredUsers.sort((x,y) => sortFunction(x,y, selectUsers)));

    let selectUsers = $derived(users.filter((user) => value.includes(user.value)));
    let show = $state(false);

    let activeIndex = $state(null);
    let activeItem = $derived(activeIndex !== null ? users[((activeIndex % users.length) + users.length) % users.length] : null);
    let multiSelectContainer;
    let whitelistSearchInput = $state();

    function selectUser(select, event) {
        event.stopPropagation();

        const oldValue = [...value];

        if(value.includes(select.value)){
            clearOption(select);
        } else if(!value.includes(select.value)){
            value = single ? [select.value] : [...value, select.value];
        }
        if(!equal(oldValue, value)){
            triggerChange();
        }
    };

    function createDismissableContext(onDismiss) {
        const context = {
            dismiss: onDismiss
    };

  return setContext(Symbol("dismissable"), context);
}
    
    function clearAll(e) {
        e.stopPropagation();
        const oldValue = [...value];

        value = [];

        if(oldValue.length > 0){
            triggerChange();
        }
    };

    createDismissableContext(clearAll);

    function clearOption(select){
        if(value.includes(select.value)){
            const oldValue = [...value];
            value =  value.filter((item) => item !== select.value);

            if(oldValue.length !== value.length){
                triggerChange();
            }
        }
    };

    function triggerChange(){
        if(onchange){
            const changeEvent = new Event("change", {bubbles: true});
            Object.defineProperty(changeEvent, "target", {
                value: {value},
                enumerable: true
            });
            Object.defineProperty(changeEvent, "CurrentTarget", {
                value: {value},
                enumerable: true
            });
            onchange(changeEvent);
        }
    };
    function closeDropdown(){show = false};

    function toggleDropdown(e){
        e.stopPropagation();
        if(!disabled){
            clickProtected = true;
            if(multiSelectContainer && multiSelectContainer.contains(e.target)){
                show = !show;
            } else {
                show = false;
            }
        }
    };

    function handleBlur(event) {
    
    if (event.currentTarget && event.currentTarget.contains && !event.currentTarget.contains(event.relatedTarget)) {
      closeDropdown();
    }
    if (onblur) {
      onblur(event);
    }
  };

  
  function handleToggleActiveItem() {

    if (!show) {
      show = true;
      activeIndex = 0;
    } else {
      if (activeItem !== null) selectUser(activeItem, new MouseEvent("click")); 
    }
  }

  function handleArrowUpDown(offset) {

    if (!show) {
      show = true;
      activeIndex = 0;
    } else {
      if (activeIndex !== null) {
        activeIndex += offset;
      } else {
        activeIndex = 0;
      }
    }
  }

  function handleKeyDown(event) {
    
    if (event.key !== "Tab") {
      event.preventDefault();
    }
    event.stopPropagation();

    const actions = {
      Escape: closeDropdown,
      Enter: handleToggleActiveItem,
      " ": handleToggleActiveItem,
      ArrowDown: () => handleArrowUpDown(1),
      ArrowUp: () => handleArrowUpDown(-1)
    };
    if (event.key in actions) {
      actions[event.key]?.();
    }
  };

   function handleClickOutside(event) {
      if (!clickProtected  && multiSelectContainer && !multiSelectContainer.contains(event.target)) {
        closeDropdown();
      } else {
        clickProtected = false;
      }
    };
  
</script>
<select name="whitelistMembers" {value} hidden  {onchange}>
    {#each users as user (user.value)}
        <option value={user.value}>{displayFunction(user)}</option>
    {/each}
</select>

<div bind:this={multiSelectContainer} onclick={toggleDropdown} onkeydown={handleKeyDown} 
onblur={handleBlur} tabindex="0" role="listbox" 
class="relative border border-gray-300 w-full flex items-center gap-2 dark:border-gray-600 ring-primary-500 dark:ring-primary-500 focus-visible:outline-hidden px-2.5 py-2.5 min-h-[2.4rem] text-xs">
{#if !selectUsers.length}
    <span class="text-gray-400"></span> 
{/if}
    <span>
        {#if single && selectUsers.length === 1}
            <Badge dismissable={!disabled} color="gray" large={false}  params={{duration: 100}} onclose={() => clearOption(selectUsers[0])} class={["mx-0.5 px-2 py-0"]}>
                    {displayFunction(selectUsers[0])}
                </Badge>
        {:else}
            {#each selectUsers as user (user.value)}
                <Badge dismissable={!disabled} color="gray" large={false}  params={{duration: 100}} onclose={() => clearOption(user)} class={["mx-0.5 px-2 py-0"]}>
                    {displayFunction(user)}
                </Badge>
            {/each}
        {/if}
    </span>
    <div class="ms-auto flex items-center gap-2">
        {#if selectUsers.length}
            <CloseButton {disabled} size="sm" color="none" class="p-0 focus:ring-gray-400 dark:text-white" />
        {/if}
        <svg class="ms-1 h-3 w-3 cursor-pointer text-gray-800 dark:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d={show ? "m1 5 4-4 4 4" : "m9 1-4 4-4-4"} />
        </svg>
    </div>

    {#if show}
        
        <div onclick={(e) => {clickProtected = true; e.stopPropagation()}} role="presentation" class={`absolute z-1000 p-3 flex flex-col gap-1 max-h-64 bg-white border border-gray-300 dark:bg-gray-700 dark:border-gray-600 start-0 top-8 cursor-pointer overflow-y-scroll w-full`}>
            <Input onclick={(e) => e.stopPropagation()} onkeydown={(e) => e.stopPropagation()} size="md" placeholder="_Search User_" type="text" bind:value={searchTerm} {disabled}/>
            {#each sortedUsers as user (user.value)}
                {@const isSelected = selectUsers.includes(user)}
                {@const isActive = activeItem === user}
               
                <button onclick={(e) =>{ selectUser(user, e); e.stopPropagation()}}
                    class={"text-left font-bold py-2 px-3"+ (isActive ? "bg-primary-100 text-primary-500 dark:bg-primary-500 dark:text-primary-100 hover:bg-primary-100 dark:hover:bg-primary-500 hover:text-primary-600 dark:hover:text-primary-100" : 
                "text-gray-600 hover:text-gray-600 dark:text-gray-400 hover:bg-gray-100 dark:hover:text-gray-300 dark:hover:bg-gray-600")}
                data-selected={isSelected ? "true" : undefined}
                data-active={isActive ? "true" : undefined}>
                    <Checkbox checked={isSelected}></Checkbox>
                    {displayFunction(user)}
                </button>
            {/each}
        </div>
    {/if}
</div>
<svelte:window on:click={handleClickOutside} />