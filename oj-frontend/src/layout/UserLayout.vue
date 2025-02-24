<template>
  <div id="UserLayout">
    <a-layout>
      <a-layout-header class="header">
        <div class="title-bar">
          <a-space>
            <img alt="logo" src="@/assets/dog.png" />
            <div class="title">汪汪判官 OJ判题系统</div>
          </a-space>
        </div>
      </a-layout-header>
      <a-layout-content class="content">
        <router-view />
      </a-layout-content>
      <a-layout-footer class="footer"> OJ 判题系统 by vv </a-layout-footer>
    </a-layout>
  </div>
</template>
<script lang="ts" setup>
</script>
<style scoped>
#UserLayout {
  height: 100vh;
}

#UserLayout .header {
  margin-top: 14px;
  margin-bottom: 14px;
  .title-bar {
    text-align: center;
    img {
      height: 48px;
      width: 48px;
    }
  }
}

#UserLayout .content {
  padding: 20px;
}

#UserLayout .footer {
  padding: 16px;
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  text-align: center;
}
</style>
