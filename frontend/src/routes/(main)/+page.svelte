<script>
    import {Heading, Span, Hr} from "flowbite-svelte";
    import Card from "$lib/components/Card.svelte";
    import Markdown from "$lib/components/Markdown.svelte";

    let {
        data
    } = $props();
    let posts = $derived(data.all);

</script>

<Card>
    <Heading tag="h2" class="text-center text-text">
        Welcome to <Span gradient="redToYellow" class="">TUrtle</Span>
    </Heading>
</Card>

{#each posts as post}
    <Card class="flex flex-col gap-5">
        <div class="flex justify-between">
            <Heading tag="h3">{post.title}</Heading>
            <div class="flex flex-col justify-center">
                <div>{new Date(post.updatedAt).toLocaleDateString()}</div>
            </div>
        </div>

        <Hr class="m-0 p-0"/>

        <Markdown content={post.content}/>
    </Card>
{/each}