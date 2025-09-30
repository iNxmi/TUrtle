<script> 
let error = ""; 

document.documentElement.classList.add("dark");	// example: activate darkmode

async function login(e) { 
	e.preventDefault(); 
	error = ""; 
	const form = new FormData(e.target);
	try { 
		const res = await fetch(
			"/api/login", 
			{ 
				method: "POST", headers: { "Content-Type": "application/json" }, 
				body: JSON.stringify({ username: form.get("user"), password: form.get("password") }), 
				credentials: "include" 
			}); 
				
		const data = await res.json(); 
		if (!res.ok) { 
			error = data.detail || "Login fehlgeschlagen"; 
			return; 
			} 
		
		window.location.href = "/csw/dashboard"; 
	} catch (err) { 
		error = "Fehler: " + (err?.message ?? JSON.stringify(err)); 
	} 
} 
</script> 

<form on:submit={login}> 
	<label> Username 
		<input name="user" required /> 
	</label> 
	<label> Password 
		<input name="password" type="password" required /> 
	</label> 
	<button>Log in</button> 
</form> 
{#if error} 
<p style="color:red">{error}</p> 
{/if}