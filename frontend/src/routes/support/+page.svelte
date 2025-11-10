<script>
    import {m} from '$lib/paraglide/messages.js';

    function send(event) {
        event.preventDefault();
        alert("TODO: Implement ticket submission!");
    }
</script>

<div class="flex flex-col gap-10">
    <div class="flex flex-col gap-10">
        <h1 class="text-center">{m.support__faq_title()}</h1>

        <div class="flex flex-col">
            <h2 class="font-bold">dummy_title_1</h2>
            <p class="text-justify">
                Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam
                rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt
                explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia
                consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui
                dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora
                incidunt ut labore et dolore magnam aliquam.
            </p>
        </div>

        <div class="flex flex-col">
            <h2 class="font-bold">dummy_title_2</h2>
            <p class="text-justify">
                Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the
                industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and
                scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap
                into
                electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the
                release of
            </p>
        </div>

        <div class="flex flex-col">
            <h2 class="font-bold">dummy_title_3</h2>
            <p class="text-justify">
                A wonderful serenity has taken possession of my entire soul, like these sweet mornings of spring which I
                enjoy with my whole heart. I am alone, and feel the charm of existence in this spot, which was created
                for the bliss of souls like mine. I am so happy, my dear friend, so absorbed in the exquisite sense of
                mere tranquil existence, that I neglect my talents. I should be incapable of drawing a single stroke at
                the present moment; and yet I feel that I never was a greater artist than now. When, while the lovely
                valley teems with vapour around me, and the meri
            </p>
        </div>
    </div>

    <hr>

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

            <div class="flex gap-5">
                <input type="checkbox" required>
                <p>{m.support__i_agree_to_tos()}</p>
            </div>

            <div class="border border-dashed">
                <h1 class="text-center m-8">I am not a Robot ✅</h1>
            </div>

            <input type="submit" value={m.support__button()}/>
        </form>
    </div>
</div>