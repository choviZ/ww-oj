import HomeView from '@/views/HomeView.vue'
import AboutView from '@/views/AboutView.vue'
import type { RouteRecordRaw } from 'vue-router'
import LoginView from '@/views/user/LoginView.vue'
import ACCESS_ENUM from '@/access/ACCESS_ENUM.ts'
import UserLayout from '@/layout/UserLayout.vue'
import RegisterView from '@/views/user/RegisterView.vue'

export const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: '浏览题目',
    component: HomeView
  },
  {
    path: '/about',
    name: '关于',
    component: AboutView,
    meta: {
      access: ACCESS_ENUM.ADMIN
    }
  },
  {
    path: '/user',
    name: '用户布局',
    component: UserLayout,
    meta:{
      hiddenMenu:'true'
    },
    children:[
      {
        path: '/user/login',
        name: '登录',
        component: LoginView,
      },
      {
        path: '/user/register',
        name: '注册',
        component: RegisterView,
      }
    ]
  }
]
