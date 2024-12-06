import request from '@/utils/request'

const deliveryAddressApi = {

  // 查询收货地址列表
  getList(query) {
    return request({
      url: '/system/delivery_address/getPageList',
      method: 'get',
      params: query
    })
  },

  // 查询收货地址详情信息
  getInfo(code) {
    return request({
      url: '/system/delivery_address/getInfoByCode/' + code,
      method: 'get'
    })
  },

  // 新增收货地址
  add(data) {
    return request({
      url: '/system/delivery_address',
      method: 'post',
      data: data
    })
  },

  // 修改收货地址
  update(data) {
    return request({
      url: '/system/delivery_address',
      method: 'put',
      data: data
    })
  },

  // 删除取货地址
  del(code) {
    return request({
      url: '/system/delivery_address/delete/' + code,
      method: 'delete'
    })
  },
}
// 导出对象
export default deliveryAddressApi;