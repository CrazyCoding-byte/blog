import request from "@/utils/request";

export function showAudio() {
  return request({
    url: `/front/audio/list`,
    method: 'get',
    baseURL:"http://localhost:8001"
  })
}

export function saveAudio(data) {
  return request({
    url: `/front/audio/save`,
    method: 'post',
    data,
    baseURL:"http://localhost:8001"
  })
}

export function searchAudio(param) {
  return request({
    url: `/front/audio/search`,
    method: 'get',
    params:{param},
    baseURL:"http://localhost:8001"
  })
}

export function removeAudio(id) {
  return request({
    url: `/front/audio/delete/${id}`,
    method: 'delete',
    baseURL:"http://localhost:8001"
  })
}
