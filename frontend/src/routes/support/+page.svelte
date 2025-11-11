<script>
    import {Heading, Input, Label, Button, Select, Textarea, Checkbox, P, A} from 'flowbite-svelte';
    import {m} from '$lib/paraglide/messages.js';
    import ReCAPTCHA from '../../components/ReCAPTCHA.svelte';

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
        <Heading class="text-center" tag="h3">{m.support__faq_title()}</Heading>

        <div class="flex flex-col">
            <Heading tag="h4">1. dummy_title</Heading>
            <P class="text-justify">
                Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam
                rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt
                explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia
                consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui
                dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora
                incidunt ut labore et dolore magnam aliquam.
            </P>
        </div>

        <div class="flex flex-col">
            <Heading tag="h4">2. dummy_title</Heading>
            <P class="text-justify">
                Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the
                industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and
                scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap
                into
                electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the
                release of
            </P>
        </div>

        <div class="flex flex-col">
            <Heading tag="h4">3. dummy_title</Heading>
            <P class="text-justify">
                A wonderful serenity has taken possession of my entire soul, like these sweet mornings of spring which I
                enjoy with my whole heart. I am alone, and feel the charm of existence in this spot, which was created
                for the bliss of souls like mine. I am so happy, my dear friend, so absorbed in the exquisite sense of
                mere tranquil existence, that I neglect my talents. I should be incapable of drawing a single stroke at
                the present moment; and yet I feel that I never was a greater artist than now. When, while the lovely
                valley teems with vapour around me, and the meri
            </P>
        </div>
    </div>

    <hr>

    <form class="flex flex-col gap-5" onsubmit={send}>
        <Heading class="text-center" tag="h3">{m.support__title()}</Heading>

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

        <div class="flex items-center gap-5 justify-between">
            <Checkbox name="agree_tos">{m.support__i_agree_to_tos()}</Checkbox>
            <ReCAPTCHA/>
        </div>

        <Button class="flex-10" type="submit">{m.support__button()}</Button>

<!--        <div class="border border-dashed">-->
<!--            <P class="text-center m-8">I am not a Robot ✅</P>-->
<!--        </div>-->
    </form>
</div>