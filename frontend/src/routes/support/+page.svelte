<script>
    let {data} = $props();

    function send(event) {
        event.preventDefault();
    }

</script>

<div>
    <h1 class="text-center">Create a support ticket</h1>

    <form class="flex flex-col gap-5" onsubmit={send}>

        <div class="flex gap-5">
            <div class="flex flex-col">
                <label for="urgency">Urgency</label>
                <select id="urgency" required>
                    <option value="urgent">Urgent</option>
                    <option value="not so urgent">Not so Urgent</option>
                </select>
            </div>
            <div class="flex flex-col">
                <label for="category">Category</label>
                <select id="category" required>
                    <option value="login">Login</option>
                    <option value="registration">Registration</option>
                    <option value="other">Other</option>
                </select>
            </div>
        </div>

        <div class="flex flex-col">
            <label for="email">E-Mail</label>
            <input id="email" type="email" required/>
        </div>

        <div class="flex flex-col">
            <label for="title">Title</label>
            <input id="title" type="text" required/>
        </div>

        <div class="flex flex-col">
            <label for="description">Describe your problem</label>
            <textarea id="description" type="text" required></textarea>
        </div>

        <div class="border border-dashed">
            <h1 class="text-center m-10">Captcha</h1>
        </div>

        <input type="submit" value="Submit Ticket"/>
    </form>
</div>