<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="取货地址" prop="addressName">
        <el-input
          v-model="queryParams.addressName"
          placeholder="请输入取货地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.canValid" placeholder="取货地址状态" clearable>
          <el-option
            v-for="dict in dict.type.server_address_status"
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
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['server:pick_up_address:remove']"
        >删除</el-button>
      </el-col>
      <!-- 是否展示搜索栏 -->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="addressList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column v-if="false" label="取货地址ID" prop="id"/>
      <el-table-column v-if="false" label="取货地址code" prop="addressCode"/>
      <el-table-column label="取货地址" align="left" prop="addressName"/>
      <el-table-column label="状态" align="center" width="100">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.canValid"
            active-value="1"
            inactive-value="0"
            @change="handleStatusChange(scope.row)"
          ></el-switch>
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
            v-hasPermi="['server:pick_up_address:add:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['server:pick_up_address:add:remove']"
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
        <el-form-item v-if="false" label="取货地址id" prop="id">
          <el-input v-model="form.id" />
        </el-form-item>
        <el-form-item v-if="false" label="取货地址code" prop="addressCode">
          <el-input v-model="form.addressCode" />
        </el-form-item>
        <el-form-item label="取货地址" prop="addressName">
          <el-input v-model="form.addressName" placeholder="请输入取货地址" />
        </el-form-item>
        <el-form-item label="状态"prop="canValid">
          <el-radio-group v-model="form.canValid">
            <el-radio v-for="dict in dict.type.server_address_status"
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
import pickupAddressApi from "@/api/system/pickup_address";

export default {
  name: "PickupAddress",
  dicts: ['server_address_status'],
  data() {
    return {
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
      addressList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        addressName: undefined,
        canValid: undefined
      },
      // 表单参数
      form: {canValid: '1'},
      // 表单校验
      rules: {
        addressName: [
          { required: true, message: "取货地址名称不能为空", trigger: "blur" }
        ],
        canValid: [
          { required: true, message: "地址状态不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询取货地址列表 */
    getList() {
      this.loading = true;
      pickupAddressApi.getList(this.queryParams).then(response => {
        this.addressList = response.data.records;
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
        addressName: undefined,
        canValid: "1"
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
      this.title = "添加取货地址";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const code = row.addressCode;
      pickupAddressApi.getInfo(code).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改取货地址";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.addressCode != undefined) {
            pickupAddressApi.update(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            pickupAddressApi.add(this.form).then(response => {
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
      let addressCodes = (row.addressCode === undefined || row.addressCode === null) ? this.codes : [row.addressCode].filter(Boolean);
      this.$modal.confirm('是否确认删除数据项？').then(function() {
        return pickupAddressApi.delBatch(addressCodes);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    // 取货地址状态修改
    handleStatusChange(row) {
      let text = row.canValid === "1" ? "启用" : "关闭";
      this.$modal.confirm('确认要"' + text + '""' + row.addressName + '"取货地址吗？').then(function() {
        return pickupAddressApi.upadteStatus(row.addressCode, row.canValid);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.canValid = row.canValid === "0" ? "1" : "0";
      });
    },
  }
};
</script>