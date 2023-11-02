import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/Manage.vue'

Vue.use(VueRouter)
const originalPush = VueRouter.prototype.push
//修改原型对象中的push方法
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}
//解决vue路由重复导航错误
//获取原型对象上的push函数

const routes = [
  {
    path: '/',
    name: 'Manage',
    component: () => import(/* webpackChunkName: "about" */ '../views/Manage.vue'),
    redirect: "/login",
    meta: {
      title: "首页"
    },
    children: [
      { path: 'user', name: 'User', meta: {title: "用户管理"}, component: () => import(/* webpackChunkName: "about" */ '../views/User.vue') },
      { path: 'file', name: 'File', meta: {title: "文件管理"}, component: () => import(/* webpackChunkName: "about" */ '../views/File.vue') },
      { path: 'home', name: 'Home', component: () => import(/* webpackChunkName: "about" */ '../views/Home.vue') },
      { path: 'profile', name: 'Profile', meta: {title: "修改个人信息"}, component: () => import(/* webpackChunkName: "about" */ '../views/Profile.vue') },
    ]
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import(/* webpackChunkName: "about" */ '../views/Login.vue'),
    meta: {
      title: "登录"
    }
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import(/* webpackChunkName: "about" */ '../views/Register.vue'),
    meta: {
      title: "登录"
    }
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
