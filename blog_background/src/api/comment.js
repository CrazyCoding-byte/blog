import request from '@/utils/request'

export function commentlist(page,limit) {
  return request({
    url: `/articles/comments/show/list/${page}/${limit}`,
    method: 'get',
    baseURL:"http://localhost:8001"
  })
}
export function deletecomment(id) {
  return request({
    url: `/articles/comments/deleteCommentById/${id}`,
    method: 'delete',
    baseURL:"http://localhost:8001"
  })
}
export function saveComment(data) {
  return request({
    url: `/articles/comments/saveComment`,
    method: 'post',
    data,
    baseURL:"http://localhost:8001"
  })
}
