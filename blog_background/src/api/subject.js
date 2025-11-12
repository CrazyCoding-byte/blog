import request from '@/utils/request'

export function subjectlist() {
  return request({
    url: `/articles/subject/show/list`,
    method: 'get',
    baseURL:"http://localhost:8001"
  })
}
export function deletesubject(id) {
  return request({
    url: `/articles/subject/deleteSubjectById/${id}`,
    method: 'delete',
    baseURL:"http://localhost:8001"
  })
}
export function savesubject(data) {
  return request({
    url: `/articles/subject/saveSubject`,
    method: 'post',
    data,
    baseURL:"http://localhost:8001"
  })
}
