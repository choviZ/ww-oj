import { defineStore } from 'pinia'
import { ref } from 'vue'
import ACCESS_ENUM from '@/access/ACCESS_ENUM.ts'
import { type LoginUserVO, UserControllerService } from '../../generated'

export const useUserStore = defineStore('userStore', () => {
  // 登录用户信息
  const loginUser = ref<LoginUserVO>({
    userName: ACCESS_ENUM.NOT_LOGIN,
    userRole: ACCESS_ENUM.NOT_LOGIN
  })

  // 获取登录用户信息
  async function fetchLoginUser() {
    const res = await UserControllerService.getLoginUserUsingGet()
    if (res.code === 0) {
      loginUser.value = res.data
    }
  }

  // 更新登录用户
  function setLoginUser(user: LoginUserVO) {
    loginUser.value = user
  }

  return { loginUser, setLoginUser, fetchLoginUser }
})
