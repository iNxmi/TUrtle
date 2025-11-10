<script>
    import {m} from '$lib/paraglide/messages.js';

    function send(event) {
        event.preventDefault();
        alert("TODO: Implement ticket submission!");
    }
</script>

<div>
    <h1 class="text-center">{m.support__title()}</h1>

    <form class="flex flex-col gap-5" onsubmit={send}>
        <div class="flex gap-5">
            <div class="flex flex-1 flex-col">
                <label for="urgency">{m.support__urgency_label()}</label>
                <select id="urgency" required>
                    <option value="urgent">{m.support__urgency_urgent()}</option>
                    <option value="not so urgent">{m.support__urgency_not_urgent()}</option>
                </select>
            </div>
            <div class="flex flex-1 flex-col">
                <label for="category">{m.support__category_label()}</label>
                <select id="category" required>
                    <option value="login">{m.support__category_login()}</option>
                    <option value="registration">{m.support__category_register()}</option>
                    <option value="other">{m.support__category_other()}</option>
                </select>
            </div>
        </div>

        <div class="flex flex-col">
            <label for="email">{m.support__email_label()}</label>
            <input id="email" type="email" required/>
        </div>

        <div class="flex flex-col">
            <label for="subject">{m.support__subject_label()}</label>
            <input id="subject" type="text" required/>
        </div>

        <div class="flex flex-col">
            <label for="description">{m.support__description_label()}</label>
            <textarea id="description" type="text" required></textarea>
        </div>

        <div class="border border-dashed">
            <h1 class="text-center m-8">I am not a Robot ✅</h1>
        </div>

        <input type="submit" value={m.support__button()}/>
    </form>
</div>