import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/vue-admin-template/rest/model/getMldelsByPage',
    method: 'get',
    params
  })
}
