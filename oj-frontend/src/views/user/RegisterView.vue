<template>
  <div id="userRegisterView">
    <h2>用户注册</h2>
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
      <a-form-item field="post" tooltip="密码不少于8位" label="确认">
        <a-input-password v-model="form.checkPassword" placeholder="请确认密码" />
      </a-form-item>
      <div class="toLogin">
        已有账号？<router-link to="/user/register">去登录</router-link>
      </div>
      <a-form-item>
        <a-button html-type="submit" type="primary" style="max-width: 480px;width: 100%">注册</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>
<script setup lang="ts">
import { reactive } from 'vue'
import {
  UserControllerService,
  type UserRegisterRequest
} from '../../../generated'
import { Message } from '@arco-design/web-vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/useUserStore.ts'

const router = useRouter()
const userStore = useUserStore()
/**
 * 表单信息
 */
const form = reactive<UserRegisterRequest>({
  userAccount: '',
  userPassword: '',
  checkPassword: ''
})

/**
 * 提交表单，执行注册
 */
const handleSubmit = async () => {
  // 判断两次密码是否一致
  if (form.userPassword !== form.checkPassword) {
    Message.error("两次输入的密码不一致！")
  }
  // 发送请求
  const res = await UserControllerService.userRegisterUsingPost(form)
  if (res.code === 0) {
    userStore.setLoginUser(res.data)
    Message.success('注册成功')
    // todo 引导到原来的地址 redirect
    router.push({
      path: '/',
      replace: true,
    })
  } else {
    Message.error('注册失败，' + res.message)
  }
}
</script>
<style scoped>
#userRegisterView{
  .toLogin{
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

