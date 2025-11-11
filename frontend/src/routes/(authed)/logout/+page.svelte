<script>
    let apiResponse = $state(null)

    function logout() {
        apiResponse = fetch("api/auth/logout")
    }
</script>

<div>
    <button onclick={logout}>Logout</button>
</div>

{#if apiResponse}
    <p class="whitespace-pre">{JSON.stringify(apiResponse, null, 2)}</p>
{/if}