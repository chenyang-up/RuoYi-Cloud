<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户名称" prop="deliveryName">
        <el-input
          v-model="queryParams.deliveryName"
          placeholder="请输入用户名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户账号" prop="userName">
        <el-select v-model="queryParams.userNames" filterable multiple placeholder="请选择用户账号">
          <el-option v-for="item in userOptions" :key="item.userName" :label="item.nickName" :value="item.userName" :disabled="item.status == 1" ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="默认地址" prop="isDefaultAddress">
        <el-select v-model="queryParams.isDefaultAddress" placeholder="" clearable>
          <el-option
            v-for="dict in dict.type.server_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['server:pick_up_address:add']"
        >新增</el-button>
      </el-col>
      <!-- 是否展示搜索栏 -->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="pageList">
      <el-table-column v-if="false" label="收货地址ID" prop="id"/>
      <el-table-column v-if="false" label="收货地址code" prop="code"/>
      <el-table-column v-if="false" label="收件地址Code (和地址管理功能关联)" prop="systemAddressCode"/>
      <el-table-column v-if="false" label="用户账号" prop="userName"/>
      <el-table-column label="收货用户名称" align="left" prop="deliveryName"/>
      <el-table-column label="用户账号" align="left" prop="userNameStr"/>
      <el-table-column label="收货电话号码" align="left" prop="phoneNum"/>
      <el-table-column label="收货地址(前缀)" align="left" prop="systemAddressFullName"/>
      <el-table-column label="收货地址(详情)" align="left" prop="fullAddress"/>
      <el-table-column label="默认地址" prop="isDefaultAddress" align="center" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.server_yes_no" :value="scope.row.isDefaultAddress"/>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['server:delivery_address:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['server:delivery_address:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改岗位对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item v-if="false" label="收货地址id" prop="id">
          <el-input v-model="form.id" />
        </el-form-item>
        <el-form-item v-if="false" label="收货地址code" prop="code">
          <el-input v-model="form.code" />
        </el-form-item>
        <el-form-item v-if="false" label="收货地址-用户账号" prop="userName">
          <el-input v-model="form.userName" />
        </el-form-item>
        <el-form-item label="用户名称" prop="deliveryName">
          <el-input v-model="form.deliveryName" placeholder="请输入用户名称" />
        </el-form-item>
        <el-form-item label="电话号码" prop="phoneNum">
          <el-input v-model="form.phoneNum" placeholder="请输入电话号码" maxlength="11"/>
        </el-form-item>
        <el-form-item label="收货地址" prop="systemAddressCode">
          <el-cascader
            v-model="form.systemAddressCode"
            :options="systemAddressTreeOptions"
            :props="cascaderProps"
            placeholder="选择地址"
            style="width: 100%;"
            @change="onNodeSelect"
          ></el-cascader>
        </el-form-item>
        <el-form-item label="详细地址" prop="fullAddress">
          <el-input type="textarea" :rows="2" v-model="form.fullAddress" placeholder="请输入详细地址" />
        </el-form-item>
        <el-form-item label="是否默认地址"prop="isDefaultAddress">
          <el-radio-group v-model="form.isDefaultAddress">
            <el-radio v-for="dict in dict.type.server_yes_no"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import sysAddressApi from "@/api/system/sys_address";
import deliveryAddressApi from "@/api/server/delivery_address";
import { listUser } from "@/api/system/user";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "deliveryAddress",
  dicts: ['server_yes_no'],
  components: { Treeselect },
  data() {
    return {
      cascaderProps: {
        value: "code",
        label: "shortName",
        children: "children",
        disabled: "addressStatus", // 指定禁用的字段
        emitPath: false,
        expandTrigger: "hover"
      },
      // 用户列表
      userOptions:[],
      systemAddressTreeOptions:[],
      // 遮罩层
      loading: true,
      // 选中数组
      codes: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 取货地址表格数据
      pageList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deliveryName: undefined,
        userNames: [],
        isDefaultAddress: undefined
      },
      // 表单参数
      form: {
        systemAddressCode: null
      },
      // 表单校验
      rules: {
        deliveryName: [
          { required: true, message: "用户名称不能为空", trigger: "blur" }
        ],
        systemAddressCode: [
          { required: true, message: "收货地址不能为空", trigger: "blur" }
        ],
        fullAddress: [
          { required: true, message: "详细地址不能为空", trigger: "blur" }
        ],
        phoneNum: [
          {
            required: true,
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur"
          }
        ],
        isDefaultAddress: [
          { required: true, message: "默认地址状态不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    onNodeSelect(value, selectedData) {
      console.log(value);
      if (!selectedData || selectedData.length === 0) {
        this.selectedPath = "";
        return;
      }
      this.selectedPath = selectedData.map((item) => item.shortName).join(" / ");
    },
    /** 查询收货地址列表 */
    getList() {
      this.loading = true;
      listUser({}).then(response => {
        this.userOptions = response.rows;
      });
      deliveryAddressApi.getList(this.queryParams).then(response => {
        this.pageList = response.data.records;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id:undefined,
        code:undefined,
        userName:undefined,
        deliveryName: undefined,
        phoneNum:undefined,
        systemAddressCode:undefined,
        fullAddress:undefined,
        isDefaultAddress: "0"
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据 (选中code列表)
    handleSelectionChange(selection) {
      this.codes = selection.map(item => item.addressCode)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加收货地址";
      sysAddressApi.getList().then(response => {
        this.systemAddressTreeOptions = this.handleTree(response.data, "code", "parentCode", "children","addressStatus");
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const code = row.code;
      deliveryAddressApi.getInfo(code).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改收货地址";
      });
      sysAddressApi.getList().then(response => {
        this.systemAddressTreeOptions = this.handleTree(response.data, "code", "parentCode", "children","addressStatus");
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.code != undefined) {
            deliveryAddressApi.update(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            deliveryAddressApi.add(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      let code = row.code;
      this.$modal.confirm('是否确认删除数据项？').then(function() {
        return deliveryAddressApi.del(code);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
  }
};
</script>