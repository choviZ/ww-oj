<template>
  <div id="BasicLayout">
    <a-layout>
      <a-layout-header class="header">
        <GlobalHeader />
      </a-layout-header>
      <a-layout-content class="content">
        <router-view />
      </a-layout-content>
      <a-layout-footer class="footer">
        oj判题系统 by vv
      </a-layout-footer>
    </a-layout>
  </div>
</template>
<script lang="ts" setup>
import GlobalHeader from '@/components/GlobalHeader.vue'
</script>
<style scoped>
#BasicLayout {
  height: 100vh;
}

#BasicLayout .content {
  background: linear-gradient(to right, #bbb, #fff);
  padding: 20px;
}

#BasicLayout .footer {
  padding: 16px;
  background-color: cadetblue;
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  text-align: center;
}
</style>
