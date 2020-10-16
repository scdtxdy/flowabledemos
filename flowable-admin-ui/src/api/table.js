import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/vue-admin-template/rest/model/getModels',
    method: 'get',
    params
  })
}
