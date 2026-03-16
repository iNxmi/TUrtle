<script>
    import {Heading, Hr, Span} from "flowbite-svelte";
    import {CirclePlusOutline, ClockArrowOutline} from "flowbite-svelte-icons";
    import Card from "$lib/components/Card.svelte";
    import Markdown from "$lib/components/Markdown.svelte";

    let {data} = $props();
    let posts = $derived(data.all);
</script>

<Card>
    <Heading tag="h2" class="text-center text-text">
        Welcome to <Span gradient="redToYellow">TUrtle</Span>
    </Heading>
</Card>

{#snippet timestamp(date, Icon)}
    <div class="text-sm flex gap-1 justify-between">
        <div class="text-muted">
            {date.toLocaleDateString()}
        </div>
        <div class="text-muted">
            {date.toLocaleTimeString()}
        </div>
        <div class="flex flex-col justify-center">
            <Icon class="text-muted h-4 w-4"/>
        </div>
    </div>
{/snippet}

{#each posts as post}
    {@const createdAt = new Date(post.createdAt)}
    {@const updatedAt = new Date(post.updatedAt)}
    {@const isUpdated = createdAt.getTime() !== updatedAt.getTime()}

    <Card class="flex flex-col gap-3">
        <div class="flex justify-between gap-5">
            <div class="flex flex-col justify-center">
                <Heading tag="h3">{post.title}</Heading>
            </div>

            <div class="flex flex-col justify-center gap-1">
                {@render timestamp(createdAt, CirclePlusOutline)}
                {#if isUpdated}
                    {@render timestamp(updatedAt, ClockArrowOutline)}
                {/if}
            </div>
        </div>

        <Hr class="m-0 p-0"/>

        <Markdown content={post.content}/>
    </Card>
{/each}