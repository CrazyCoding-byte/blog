import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/acl/user/login',
    method: 'post',
    data,
    baseURL:"http://localhost:8002"
  })
}

export function getInfo() {
  return request({
    url: '/acl/user/getInfo',
    method: 'get',
    baseURL:"http://localhost:8002"
  })
}
export function getMenu() {
  return request({
    url: '/acl/role/getMenu',
    method: 'get',
    baseURL:"http://localhost:8002"
  })
}

export function logout() {
  return request({
    url: '/acl/user/userLogout',
    method: 'get',
    baseURL:"http://localhost:8002"
  })
}
export function saveuser(data) {
  return request({
    url: '/acl/user/saveUser',
    method: 'post',
    data,
    baseURL:"http://localhost:8002"
  })
}
export function userlist() {
  return request({
    url: '/acl/user/list',
    method: 'get',
    baseURL:"http://localhost:8002"
  })
}
export function getrelation(userId) {
  return request({
    url: `/acl/user/getRelation/${userId}`,
    method: 'get',
    baseURL:"http://localhost:8002"
  })
}
export function removerelation(userid,roleid) {
  return request({
    url: `/acl/user/removeRelation/${userid}/${roleid}`,
    method: 'delete',
    baseURL:"http://localhost:8002"
  })
}
export function listrole() {
  return request({
    url: `/acl/user/listrole`,
    method: 'get',
    baseURL:"http://localhost:8002"
  })
}

export function saveRelation(data) {
  return request({
    url: `/acl/user/saveRelation`,
    method: 'post',
    data,
    baseURL:"http://localhost:8002"
  })
}
export function deleteuser(id) {
  return request({
    url: `/acl/user/removeUser/${id}`,
    method: 'delete',
    baseURL:"http://localhost:8002"
  })
}
