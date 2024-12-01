import request from '@/utils/request'

const pickupAddressApi = {

  // 查询取货地址列表
  getList(query) {
    return request({
      url: '/system/pick_up_address/getPageList',
      method: 'get',
      params: query
    })
  },

  // 查询取货地址信息
  getInfo(code) {
    return request({
      url: '/system/pick_up_address/getInfoByCode/' + code,
      method: 'get'
    })
  },

  // 新增取货地址
  add(data) {
    return request({
      url: '/system/pick_up_address',
      method: 'post',
      data: data
    })
  },

  // 修改取货地址
  update(data) {
    return request({
      url: '/system/pick_up_address',
      method: 'put',
      data: data
    })
  },

  // 修改取货地址-状态
  upadteStatus(addressCode, canValid) {
    const data = {
      addressCode,
      canValid
    }
    return request({
      url: '/system/pick_up_address/upadteStatus',
      method: 'put',
      data: data
    })
  },

  // 批量删除取货地址
  delBatch(addressCodes) {
    const data = {
      addressCodes
    }
    return request({
      url: '/system/pick_up_address/deleteBatch',
      method: 'delete',
      data: data
    })
  },
}
// 导出对象
export default pickupAddressApi;