<script setup>
defineProps({ article: Object });
</script>

<template>
  <tr class="text-center">
    <th scope="row">{{ article.articleNo }}</th>
    <td class="text-start">
      <router-link
        :to="{ name: 'article-view', params: { articleno: article.articleNo } }"
        class="article-title link-dark"
      >
        {{ article.title }}
      </router-link>
    </td>
    <td>{{ article.author }}</td>
    <td>{{ article.hitCount }}</td>
    <td>{{ article.stars }}</td>
    <td>{{ article.createdAt }}</td>
  </tr>
</template>

<style scoped>
a {
  text-decoration: none;
}
</style>
