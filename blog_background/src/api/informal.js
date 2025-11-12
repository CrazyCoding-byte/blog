import request from '@/utils/request'

export function saveinformal(data) {
  return request({
    url: `/articles/informal/saveInformal`,
    method: 'post',
    data,
    baseURL:"http://localhost:8001"
  })
}
export function deleteinformal(id) {
  return request({
    url: `/articles/informal/deleteInformal/${id}`,
    method: 'delete',
    baseURL:"http://localhost:8001"
  })
}
export function listinformal(page,limit) {
  return request({
    url: `/articles/informal/list/${page}/${limit}`,
    method: 'get',
    baseURL:"http://localhost:8001"
  })
}
export function updateinformal(data) {
  return request({
    url: `/articles/informal/update`,
    method: 'post',
    data,
    baseURL:"http://localhost:8001"
  })
}
export function getinformal(id) {
  return request({
    url: `/articles/informal/getinformal/${id}`,
    method: 'get',
    baseURL:"http://localhost:8001"
  })
}
