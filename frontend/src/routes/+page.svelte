<script>
	import { log } from "console";


    let logins = $state(false);
   
    async function login(){
        const response = await fetch('/api/v1/auth/login', {
           method: 'POST',
           body: JSON.stringify({username: 'Twitchi', password: 'eosc2d'}),
           headers: {
            'Content-Type': 'application/json'
           }
        });

        const { username } = await response.json();
        if(username) {
            logins = true;
        }
    }
</script>
<h1>Welcome to SvelteKit</h1>
<p>Visit <a href="https://svelte.dev/docs/kit">svelte.dev/docs/kit</a> to read the documentation</p>

<button onclick={login}>Login</button>
{#if logins}
    <p>Login successful!</p>
{/if}
