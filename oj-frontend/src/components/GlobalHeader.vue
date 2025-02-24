<template>
  <div class="global-header">
    <a-row align="center" :wrap="false">
      <!-- logo 导航栏 -->
      <a-col flex="auto">
        <a-menu mode="horizontal" :selected-keys="selectedKeys" @menu-item-click="doMenuClick">
          <a-menu-item key="0" :style="{ padding: 0, marginRight: '38px' }" disabled>
            <div class="title-bar">
              <img alt="logo" src="@/assets/dog.png" />
              <div class="title">汪汪判官 OJ</div>
            </div>
          </a-menu-item>
          <a-menu-item v-for="item in visible" :key="item.path">
            {{ item.name }}
          </a-menu-item>
        </a-menu>
      </a-col>
      <!-- 用户信息 -->
      <a-col flex="100px">
        <div class="user-info">
          <a-dropdown position="bottom" trigger="hover">
            <div v-if="!userStore.loginUser.id">
              <a-button href="/user/login" type="primary"> 登录</a-button>
            </div>
            <div v-else>
              <a-avatar :size="32" style="margin-right: 8px">A</a-avatar>
              {{ userStore.loginUser.userName ?? '未登录' }}
            </div>
            <template #content>
              <a-doption @click="doLogout">退出登录</a-doption>
            </template>
          </a-dropdown>
        </div>
      </a-col>
    </a-row>
  </div>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue'
import { routes } from '@/router/routes.ts'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/useUserStore.ts'
import checkAccess from '@/access/checkAccess.ts'
import { UserControllerService } from '../../generated'
import { Message } from '@arco-design/web-vue'

const router = useRouter()
const userStore = useUserStore()

// 选中的菜单,默认主页
const selectedKeys = ref(['/'])

/**
 * 展示在导航栏的菜单，过滤掉需要隐藏的菜单项
 */
const visible = computed(() => {
  return routes.filter((item) => {
    if (item.meta?.hiddenMenu) {
      return false
    }
    if (!checkAccess(userStore.loginUser, item.meta?.access)) {
      return false
    }
    return true
  })
})

/**
 * 页面跳转后更新高亮的菜单项
 */
router.afterEach((to) => {
  selectedKeys.value = [to.path]
})

/**
 * 菜单点击事件
 */
const doMenuClick = (key: string) => {
  router.push(key)
}

/**
 * 退出登录
 */
const doLogout = async () => {
  const res = await UserControllerService.userLogoutUsingPost()
  if (res.code === 0) {
    Message.success("退出成功！")
    userStore.setLoginUser({})
    await router.push({
      path: '/',
      replace: true,
    })
  }else {
    Message.error("退出失败")
  }
}
</script>

<style scoped>
.global-header {
  .title-bar {
    display: flex;
    align-items: center;
  }

  .title {
    color: #444;
    margin-left: 16px;
  }

  img {
    height: 48px;
  }
}
</style>
