<script>
    import {m} from '$lib/paraglide/messages';
    import {onMount} from 'svelte';
    import {
        Input,
        Button,
        Table,
        TableBody,
        TableBodyCell,
        TableBodyRow,
        TableHead,
        TableHeadCell,
        Heading
    } from 'flowbite-svelte';
    import {PlusOutline} from 'flowbite-svelte-icons';
    import NewUser from './NewUser.svelte';

    let searchTerm = $state('');
    let defaultModal = $state(false);

    let page = $state(null)
    onMount(async () => {
        const response = await fetch('/api/users');
        page = await response.json();
    });

    const handleSearch = () => {
        defaultModal = true;
    };
</script>

<div>
    <Heading tag="h3">{m.admin_users__title()}</Heading>

    {#if page}
        <div class="flex">
            <Input class="max-w-1/5 m-3" placeholder={m.admin_users__search_user()} bind:value={searchTerm}/>
            <Button onclick={handleSearch}
                    class=" absolute right-20 m-3 bg-accent text-accent-dark hover:cursor-pointer">
                <PlusOutline class="text-green-700 h-5 w-5 transition duration-75"/>
                {m.admin_users__create_user()}
            </Button>
        </div>

        <Table bind:inputValue={searchTerm} hoverable={true}>
            <TableHead>
                <TableHeadCell class="bg-secondary">{m.admin_users__student_id()}</TableHeadCell>
                <TableHeadCell class="bg-secondary">{m.admin_users__last_name()}</TableHeadCell>
                <TableHeadCell class="bg-secondary">{m.admin_users__first_name()}</TableHeadCell>
                <TableHeadCell class="bg-secondary">{m.admin_users__username()}</TableHeadCell>
                <TableHeadCell class="bg-secondary">{m.admin_users__email()}</TableHeadCell>
                <TableHeadCell class="bg-secondary">{m.admin_users__edit_user()}</TableHeadCell>
                <TableHeadCell class="bg-secondary">{m.admin_users__delete_user()}</TableHeadCell>
            </TableHead>

            <TableBody>
                {#each page.content as user}
                    <TableBodyRow>
                        <TableBodyCell>{user.studentId}</TableBodyCell>
                        <TableBodyCell>{user.lastName}</TableBodyCell>
                        <TableBodyCell>{user.firstName}</TableBodyCell>
                        <TableBodyCell>{user.userName}</TableBodyCell>
                        <TableBodyCell>{user.email}</TableBodyCell>
                        <TableBodyCell>
                            <Button color="orange"
                                    onclick={() =>{window.location.replace(`/admin/users/${user.userName}`) }}>
                                {m.admin_users__edit_user()}
                            </Button>
                        </TableBodyCell>
                        <TableBodyCell>
                            <Button color="red" onclick={async (e) => {
                                alert(`/api/users/${user.userName}`);
                                await fetch(`/api/users/${user.userName}`, {method: 'DELETE'});

                                window.location.reload();
                            }}>
                                {m.admin_users__delete_user()}
                            </Button>
                        </TableBodyCell>
                    </TableBodyRow>
                {/each}
            </TableBody>
        </Table>

        <NewUser bind:open={defaultModal}></NewUser>
    {:else}
        <p>_loading_users</p>
    {/if}
</div>