import request from '@/utils/request'

export function listbanner() {
  return request({
    url: "/articles/banner/getBanner",
    method: "get",
    baseURL: "http://localhost:8001"
  })
}

export function listarticles() {
  return request({
    url: "/articles/banner/listarticles",
    baseURL: "http://localhost:8001"
  })
}

export function saveBanner(data) {
  return request({
    url: "/articles/banner/save/banner",
    method: "post",
    data,
    baseURL: "http://localhost:8001"
  })
}

export function deleteBanner(bId) {
  return request({
    url: `/articles/banner/delete/banner/${bId}`,
    method: "delete",
    baseURL: "http://localhost:8001"
  })
}

