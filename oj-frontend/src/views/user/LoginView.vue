<template>
  <div id="userLoginView">
    <h2>登录</h2>
    <a-form
      :model="form"
      @submit="handleSubmit"
      auto-label-width
      label-align="left"
      style="max-width: 480px;margin: 0 auto;"
    >
      <a-form-item field="name" tooltip="请输入用户名" label="账号">
        <a-input v-model="form.userAccount" placeholder="请输入用户名" />
      </a-form-item>
      <a-form-item field="post" tooltip="密码不少于8位" label="密码">
        <a-input-password v-model="form.userPassword" placeholder="请输入密码" />
      </a-form-item>
      <div class="toRegister">
        没有账号？<router-link to="/user/register">去注册</router-link>
      </div>
      <a-form-item>
        <a-button html-type="submit" type="primary" style="max-width: 480px;width: 100%">登录</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>
<script setup lang="ts">
import { reactive } from 'vue'
import { UserControllerService, type UserLoginRequest } from '../../../generated'
import { Message } from '@arco-design/web-vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/useUserStore.ts'

const router = useRouter()
const userStore = useUserStore()
/**
 * 表单信息
 */
const form = reactive<UserLoginRequest>({
  userAccount: '',
  userPassword: '',
})

/**
 * 提交表单，执行登录
 */
const handleSubmit = async () => {
  const res = await UserControllerService.userLoginUsingPost(form)
  if (res.code === 0) {
    userStore.setLoginUser(res.data)
    Message.success('登录成功')
    // todo 引导到原来的地址 redirect
    router.push({
      path: '/',
      replace: true,
    })
  } else {
    Message.error('登录失败，' + res.message)
  }
}
</script>
<style scoped>
#userLoginView{
  .toRegister{
    color: #0088fd;
    text-align: right;
    margin-bottom: 14px;
  }
  h2{
    text-align: center;
    margin-bottom: 14px;
  }
}
</style>
