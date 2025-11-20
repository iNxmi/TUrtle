<script>
    import {Button} from "flowbite-svelte";
    import request from "$lib/api/api.js";

    let response = $state(null)

    async function logout() {
        response = await request("/auth/logout")
        // window.location.href = "/login"
    }
</script>

<Button color="orange" onclick={logout}>Logout</Button>