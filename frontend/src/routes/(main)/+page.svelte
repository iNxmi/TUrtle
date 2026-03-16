<script>
    import {Heading, Hr, Span} from "flowbite-svelte";
    import {CirclePlusOutline, ClockArrowOutline} from "flowbite-svelte-icons";
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
    <Card class="flex flex-col gap-3">
        <div class="flex justify-between gap-5">
            <div class="flex flex-col justify-center">
                <Heading tag="h3">{post.title}</Heading>
            </div>

            <div class="flex flex-col justify-center gap-1">
                <div class="text-sm flex gap-1">
                    <div class="text-gray-500">{new Date(post.createdAt).toLocaleDateString()}</div>
                    <div class="flex flex-col justify-center">
                        <CirclePlusOutline class="text-gray-500"/>
                    </div>
                </div>
                {#if post.createdAt !== post.updatedAt}
                    <div class="text-sm flex gap-1">
                        <div class="text-gray-500">{new Date(post.updatedAt).toLocaleDateString()}</div>
                        <div class="flex flex-col justify-center">
                            <ClockArrowOutline class="text-gray-500"/>
                        </div>
                    </div>
                {/if}
            </div>
        </div>

        <Hr class="m-0 p-0"/>

        <Markdown content={post.content}/>
    </Card>
{/each}