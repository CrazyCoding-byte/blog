import request from '@/utils/request'

export function getrolelist() {
  return request({
    url: '/acl/role/list',
    method: 'get',
    baseURL:"http://localhost:8002"
  })
}
//根据角色的id查询出所有的menu菜单
export function getrolemenu(id) {
  return request({
    url: `/acl/role/getInfo/${id}`,
    method: 'get',
    baseURL:"http://localhost:8002"
  })
}

export function showpermission() {
  return request({
    url: `/acl/role/showPermission`,
    method: 'get',
    baseURL:"http://localhost:8002"
  })
}

export function updaterolepermission(id,data) {
  return request({
    url: `/acl/role/update/${id}`,
    method: 'post',
    data,
    baseURL:"http://localhost:8002"
  })
}

export function savepermission(data) {
  return request({
    url: `/acl/role/save`,
    method: 'post',
    data,
    baseURL:"http://localhost:8002"
  })
}

export function deletepermission(id) {
  return request({
    url: `/acl/role/delete/${id}`,
    method: 'delete',
    baseURL:"http://localhost:8002"
  })
}

