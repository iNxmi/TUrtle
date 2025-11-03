<script>
  export let data = {};
</script>

{#if data.user}
  <h1>Admin Panel</h1>
  <p>Logged in as {data.user.username}</p>
{:else}
  <h1>Admin Panel</h1>
  <p>Not logged in</p>
{/if}