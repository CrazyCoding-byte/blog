import request from '@/utils/request'

export function getLittleInfo() {
  return request({
    url: `/articles/littleinfo/list`,
    method: 'get',
    baseURL:"http://localhost:8001"
  })
}

export function savelittleInfo(data) {
  return request({
    url: `/articles/littleinfo/save`,
    method: 'post',
    data,
    baseURL:"http://localhost:8001"
  })
}
export function removeLittleInfo(id) {
  return request({
    url: `/articles/littleinfo/remove/${id}`,
    method: 'delete',
    baseURL:"http://localhost:8001"
  })
}
