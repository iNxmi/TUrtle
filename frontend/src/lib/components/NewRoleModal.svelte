<script>
    import { Modal, Label, Input, Button } from "flowbite-svelte";
    import WhitelistDropdown from "./WhitelistDropdown.svelte";
	import request from "$lib/api/api";
    let {showNewRoleModal = $bindable(), permissions} = $props();

    let roleName = $state('');
    let selectedRoles = $state([]);

    let dropdownPermissions = $derived(permissions.map((permission) => 
    ({
        name: permission, 
        value: permission
    })));

    async function createRole(){
        const response = await request('/roles', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({name: roleName, permissions: selectedRoles})
        });

        if(response.ok){
             showNewRoleModal = false;
        }
    }
    function sortFunction(x,y, selectedPermissions){
        if(selectedPermissions.includes(x) && !selectedPermissions.includes(y)) return -1;
        if(selectedPermissions.includes(y) && !selectedPermissions.includes(x)) return 1;
    }

    function displayFunction(permission){
        return permission.name;
    }

    function filterFunction(permission, searchTerm){
        return !searchTerm || 
		permission.name.toLowerCase().includes(searchTerm.toLowerCase());
    }
</script>
<Modal classes={{body: 'flex flex-col justify-between gap-2 h-100'}} bind:open={showNewRoleModal} onclose={()=> {selectedRoles = []}}>
   
    <div class="flex flex-col gap-5">
        <Label>
            _Name_
            <Input type='text' bind:value={roleName}/>
        </Label> 
        <Label>
            _Roles_
        <WhitelistDropdown users={dropdownPermissions} bind:value={selectedRoles} {filterFunction} {sortFunction} {displayFunction} />
    </Label>
</div> 
<Button type='submit' onclick={createRole}>_Create Role_</Button>
</Modal>