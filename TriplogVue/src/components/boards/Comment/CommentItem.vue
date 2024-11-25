<script setup>
import { modifyCommnet, deleteCommnet } from "@/api/comment"

const props = defineProps({
    comment: {
        type: Object,
        required: true,
    },
});
</script>

<template>
    <li class="comment-item">
        <div>
            <img class="avatar me-2 float-md-start bg-light p-2" :src="comment.authorImage" style="
                 width: 50px;
                 height: 50px;
                 object-fit: cover;
                 object-position: center;
                 border-radius: 50%;
                " />
            <strong>{{ comment.author }}</strong>
            <span class="text-muted small">{{ comment.createAt }}</span>
            <p>
                <span v-if="comment.parent">ㄴ</span>
                {{ comment.content }}
            </p>
        </div>
        <ul v-if="comment.children.length" class="replies">
            <CommentItem v-for="child in comment.children" :key="child.commentNo" :comment="child" />
        </ul>
    </li>
</template>


<style>
.comment-list {
    list-style: none;
    padding: 0;
}

.comment-item {
    margin-bottom: 1rem;
    padding-left: 1rem;
}

.replies {
    padding-left: 1.5rem;
    border-left: 2px solid #ddd;
}
</style>