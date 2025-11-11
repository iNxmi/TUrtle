<script>
    import {Input, Label, Button, Select, Textarea, Checkbox} from 'flowbite-svelte';
    import {m} from '$lib/paraglide/messages.js';

    function send(event) {
        event.preventDefault();
        alert("TODO: Implement ticket submission!");
    }

    const urgencies = [
        {value: "urgent", name: m.support__urgency_urgent()},
        {value: "not_urgent", name: m.support__urgency_not_urgent()}
    ];

    const categories = [
        {value: "login", name: m.support__category_login()},
        {value: "registration", name: m.support__category_register()},
        {value: "other", name: m.support__category_other()}
    ];
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
                <Label class="flex flex-1 flex-col">
                    <span>{m.support__urgency_label()}</span>
                    <Select name="category" items={urgencies} required/>
                </Label>
                <Label class="flex flex-1 flex-col">
                    <span>{m.support__category_label()}</span>
                    <Select name="category" items={categories} required/>
                </Label>
            </div>

            <Label>
                <span>{m.support__email_label()}</span>
                <Input name="email" type="email" required/>
            </Label>

            <Label>
                <span>{m.support__subject_label()}</span>
                <Input name="subject" type="text" required/>
            </Label>

            <Label>
                <span>{m.support__description_label()}</span>
                <Textarea class="w-full" name="description" placeholder="_describe_your_issue" required/>
            </Label>

            <Checkbox name="agree_tos">{m.support__i_agree_to_tos()}</Checkbox>

            <div class="border border-dashed">
                <p class="text-center m-8">I am not a Robot ✅</p>
            </div>

            <Button type="submit">{m.support__button()}</Button>
        </form>
    </div>
</div>