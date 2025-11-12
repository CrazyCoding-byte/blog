import request from "@/utils/request";

export function showArticles(page,limit,searchObj) {
  return request({
    url: `/articles/service/show/list/${page}/${limit}`,
    method: 'post',
    data:searchObj,
    baseURL:"http://localhost:8001"
  })
}
export function saveArticles(data) {
  return request({
    url: `/articles/service/saveArticle`,
    method: 'post',
    data,
    baseURL:"http://localhost:8001"
  })
}
export function deleteArticles(id) {
  return request({
    url: `/articles/service/deleteArticleById/${id}`,
    method: 'delete',
    baseURL:"http://localhost:8001"
  })
}

export function getArticles(id) {
  return request({
    url: `/articles/service/getArticleById/${id}`,
    method: 'get',
    baseURL:"http://localhost:8001"
  })
}

export function updateArticles(data) {
  return request({
    url: `/articles/service/updateArticleById`,
    data,
    method:"post",
    baseURL:"http://localhost:8001"
  })
}

//显示所有的分类
export function subjectlist() {
  return request({
    url: '/articles/subject/show/list',
    method: 'get',
    baseURL:"http://localhost:8001"
  })
}
