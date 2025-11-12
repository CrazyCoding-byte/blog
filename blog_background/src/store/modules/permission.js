import {asyncRoutes, constantRoutes} from '@/router'
import * as userapi from '@/api/user'
import  Layout from '@/views/layout/Layout'
/**
 * Use meta.role to determine if the current user has permission
 * @param roles
 * @param route
 */
// function hasPermission(roles, route) {
//   if (route.meta && route.meta.roles) {
//     //判断当前路由是否包含当前的角色
//     return roles.some(role => route.meta.roles.includes(role))
//   } else {
//     //如果当前的meta没有role 那就是说谁都可以访问
//     return true
//   }
// }

function filterAsyncRouter(asyncRouterMap) { // 遍历后台传来的路由字符串，转换为组件对象
  try {
    const accessedRouters = asyncRouterMap.filter(route => {
      if (route.component) {
        if (route.component === 'Layout') { // Layout组件特殊处理
          route.component = Layout
        } else {
          const component = route.component
          route.component = resolve => {
            require(['@/views' + component + '.vue'], resolve)
          }
        }
      }
      if (route.children && route.children.length) {
        console.log("我被调用了")
        route.children = filterAsyncRouter(route.children)
      }
      return true
    })
    return accessedRouters
  } catch (e) {
    console.log(e)
  }
}

const state = {
  routes: [],
  addRoutes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = constantRoutes.concat(routes)
    console.log("zzz", state.routes)
  }
}

const actions = {
  async generateRoutes({commit}, roles) {
    console.log("获取路由的接口被调用了")
    const asyncRouter = await userapi.getMenu()
    return new Promise(resolve => {
      const tmp = asyncRouter
      console.log("当前后端返回的数据", tmp)
      const accessedRoutes = filterAsyncRouter(tmp)
      console.log("当前后端返回的数据2", accessedRoutes)
      commit('SET_ROUTES',accessedRoutes)
      resolve(accessedRoutes)
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
