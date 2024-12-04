import request from '@/utils/request'

const sysAddressApi = {

  // 查询地址列表
  getList(query) {
    return request({
      url: '/system/sys_address/getList',
      method: 'get',
      params: query
    })
  },

  // 查询地址列表（排除节点）
  listDataExcludeChild(code) {
    return request({
      url: '/system/sys_address/getExcludeList/' + code,
      method: 'get'
    })
  },

  // 查询地址信息
  getInfo(code) {
    return request({
      url: '/system/sys_address/getInfoByCode/' + code,
      method: 'get'
    })
  },

  // 新增地址
  add(data) {
    return request({
      url: '/system/sys_address',
      method: 'post',
      data: data
    })
  },

  // 修改地址
  update(data) {
    return request({
      url: '/system/sys_address',
      method: 'put',
      data: data
    })
  },

  // 删除取货地址
  del(code) {
    return request({
      url: '/system/sys_address/delete/' + code,
      method: 'delete'
    })
  },
}
// 导出对象
export default sysAddressApi;