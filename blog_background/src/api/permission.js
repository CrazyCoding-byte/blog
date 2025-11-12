import request from '@/utils/request'

export function listpermission(page,limit,permissonname) {
  return request({
    url: `/acl/permission/list/${page}/${limit}`,
    method: 'get',
    params:{permissonname},
    baseURL:"http://localhost:8002"
  })
}
export function savepermission(data) {
  return request({
    url: `/acl/permission/save`,
    method: 'post',
    data,
    baseURL:"http://localhost:8002"
  })
}

export function deletepermission(id) {
  return request({
    url: `/acl/permission/delete/${id}`,
    method: 'delete',
    baseURL:"http://localhost:8002"
  })
}
export function updatepermission(data) {
  return request({
    url: `/acl/permission/update`,
    method: 'post',
    data,
    baseURL:"http://localhost:8002"
  })
}
export function getpermission(id) {
  return request({
    url: `/acl/permission/getInfo/${id}`,
    method: 'get',
    baseURL:"http://localhost:8002"
  })
}
