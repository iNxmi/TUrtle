<script>
    import {marked} from 'marked';

    const {content} = $props();
</script>

<div class="prose max-w-full text-justify">
    {@html marked.parse(content)}
</div>