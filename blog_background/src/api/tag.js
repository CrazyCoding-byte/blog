import request from '@/utils/request'

export function getTag() {
  return request({
    url: '/articles/tag/list',
    method: 'get',
    baseURL:"http://localhost:8001"
  })
}
export function saveTag(data) {
  return request({
    url: '/articles/tag/saveTag',
    method: 'post',
    data,
    baseURL:"http://localhost:8001"
  })
}
export function deleteTag(id) {
  return request({
    url: `/articles/tag/deleteTag/${id}`,
    method: 'delete',
    baseURL:"http://localhost:8001"
  })
}

