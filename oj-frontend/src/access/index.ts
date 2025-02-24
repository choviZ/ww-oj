import { Message } from '@arco-design/web-vue'
import router from '@/router'
import { useUserStore } from '@/stores/useUserStore.ts'
import ACCESS_ENUM from '@/access/ACCESS_ENUM.ts'
import checkAccess from '@/access/checkAccess.ts'
import type { LoginUserVO } from '../../generated'

/**
 * 路由跳转前判断权限
 */
router.beforeEach(async (to, from, next) => {
  const userStore = useUserStore()
  const loginUser:LoginUserVO = userStore.loginUser
  // 未登录，尝试获取登录信息
  if (!loginUser || !loginUser.id) {
    await userStore.fetchLoginUser()
  }
  const needAccess = (to.meta?.access as string) ?? ACCESS_ENUM.NOT_LOGIN
  // 访问的页面需要登录
  if (needAccess !== ACCESS_ENUM.NOT_LOGIN) {
    if (!loginUser || !loginUser.userRole) {
      // 未登录，跳转至登录页
      next(`/user/login?redirect=${to.fullPath}`)
      return
    }
    if (!checkAccess(loginUser, needAccess)) {
      // 已登录，但权限不足
      Message.error('无权限')
      return
    }
  }
  next()
})
