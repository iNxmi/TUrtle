<script>
    import {Button} from "flowbite-svelte";

    let response = $state(null)

    function logout() {
        response = fetch("/api/auth/logout")
        window.location.href = "/login"
    }
</script>

<Button color="orange" onclick={logout}>Logout</Button>