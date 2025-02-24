<template>
  <div id="app">
    <template v-if="route.fullPath.startsWith('/user')">
      <router-view />
    </template>
    <template v-else>
      <BasicLayout />
    </template>
  </div>
</template>

<script setup lang="ts">
import BasicLayout from '@/layout/BasicLayout.vue'
import { useRoute } from 'vue-router'

const route = useRoute()

</script>

<style scoped></style>
