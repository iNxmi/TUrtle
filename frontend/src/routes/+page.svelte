<script>
    let apiResponse = $state(null);

    async function login(event) {
        event.preventDefault();

        const username = document.getElementById("input_username").value;
        const password = document.getElementById("input_password").value;
        const response = await fetch('/api/auth/login', {
            method: 'POST',
            body: JSON.stringify({username: username, password: password}),
            headers: {
                'Content-Type': 'application/json'
            }
        });

        apiResponse = await response.json();
    }
</script>

<div>
    <form onsubmit={login}>
        <input id="input_username" type="text" placeholder="Username" value="Twitchi" required>
        <input id="input_password" type="password" placeholder="Password" value="eosc2d" required>

        <input type="submit" value="Login">
    </form>
</div>

{#if apiResponse}
    <p class="whitespace-pre">{JSON.stringify(apiResponse, null, 2)}</p>
{/if}
