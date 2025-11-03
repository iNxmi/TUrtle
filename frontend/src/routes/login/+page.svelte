<script>
    let message = "";

    //document.documentElement.classList.add("dark");

    async function login(e) {
        e.preventDefault();

        message = "";

        const form = new FormData(e.target);
        const payload = {
            email_or_username: form.get("emailOrUsername"),
            password: form.get("password")
        }

        try {
            const url = "http://backend:8080/api/v1/auth/login";
            const response = await fetch(url,
                {
                    method: "POST",
                    headers: {"Content-Type": "application/json"},
                    body: JSON.stringify(payload),
                    credentials: "include"
                }
            );

            const data = await response.json();
            if (!response.ok) {
                message = JSON.stringify(data) || "Login failed";
                return;
            }

            window.location.href = "/dashboard";
        } catch (error) {
            message = "Error: " + (error?.message ?? JSON.stringify(error));
        }
    }
</script>

<form on:submit={login}>
    <label> Email or Username
        <input name="emailOrUsername" required/>
    </label>

    <label> Password
        <input name="password" type="password" required/>
    </label>

    <button>Login</button>
</form>
{#if message}
    <p style="color:red">{message}</p>
{/if}