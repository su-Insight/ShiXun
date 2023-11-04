import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "@/store";


Vue.use(VueRouter)
const originalPush = VueRouter.prototype.push
//修改原型对象中的push方法
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}
//解决vue路由重复导航错误
//获取原型对象上的push函数

const routes = [
  // {
  //   path: '/',
  //   name: 'Manage',
  //   component: () => import(/* webpackChunkName: "about" */ '../views/Manage.vue'),
  //   redirect: "/login",
  //   meta: {
  //     title: "首页"
  //   },
  //   children: [
  //     { path: 'user', name: 'User', meta: {title: "用户管理"}, component: () => import(/* webpackChunkName: "about" */ '../views/User.vue') },
  //     { path: 'file', name: 'File', meta: {title: "文件管理"}, component: () => import(/* webpackChunkName: "about" */ '../views/File.vue') },
  //     { path: 'role', name: 'Role', meta: {title: "角色管理"}, component: () => import(/* webpackChunkName: "about" */ '../views/Role.vue') },
  //     { path: 'menu', name: 'Menu', meta: {title: "菜单管理"}, component: () => import(/* webpackChunkName: "about" */ '../views/Menu.vue') },
  //     { path: 'home', name: 'Home', component: () => import(/* webpackChunkName: "about" */ '../views/Home.vue') },
  //     { path: 'profile', name: 'Profile', meta: {title: "修改个人信息"}, component: () => import(/* webpackChunkName: "about" */ '../views/Profile.vue') },
  //   ]
  // },
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
      title: "注册"
    }
  },
  {
    path: '*',
    name: '404',
    component: () => import(/* webpackChunkName: "about" */ '../views/404.vue')
  },

]

// 提供一个重置路由的方法
export const resetRouter = () => {
  router.matcher = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
  })
}
const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})


// 注意：刷新页面会导致页面路由重置
export const setRoutes = () => {
  const storeMenus = localStorage.getItem("menus");
  if (storeMenus) {

    // 获取当前的路由对象名称数组
    const currentRouteNames = router.getRoutes().map(v => v.name)
    if (!currentRouteNames.includes('Manage')) {
      // 拼装动态路由
      const manageRoute = { path: '/', name: 'Manage', meta:{title: "首页"}, component: () => import('../views/Manage.vue'), redirect: "/home", children: [
          { path: 'profile', name: '个人信息', component: () => import('../views/Profile.vue')},
          // { path: 'password', name: '修改密码', component: () => import('../views/Password.vue')}
        ] }
      const menus = JSON.parse(storeMenus)
      menus.forEach(item => {
        if (item.path) {  // 当且仅当path不为空的时候才去设置路由
          let itemMenu = { path: item.path.replace("/", ""), name: item.name, meta:{title: item.name}, component: () => import('../views/' + item.pagePath + '.vue')}
          manageRoute.children.push(itemMenu)
        } else if(item.children.length) {
          item.children.forEach(item => {
            if (item.path) {
              let itemMenu = { path: item.path.replace("/", ""), name: item.name, meta:{title: item.name}, component: () => import('../views/' + item.pagePath + '.vue')}
              manageRoute.children.push(itemMenu)
            }
          })
        }
      })
      // 动态添加到现在的路由对象中去
      router.addRoute(manageRoute)
    }

  }
}

// 重置我就再set一次路由
setRoutes()








router.beforeEach((to, from, next) => {

  if(!to.matched.length){
    const storeMenus = localStorage.getItem("menus")
    if(storeMenus){
      next("/404")
    }else{
      next("/login")
    }
  }
  next()
})

export default router
