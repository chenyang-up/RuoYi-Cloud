<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="地址名称" prop="shortName">
        <el-input
          v-model="queryParams.shortName"
          placeholder="请输入部门名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="addressStatus">
        <el-select v-model="queryParams.addressStatus" placeholder="地址状态" clearable>
          <el-option
            v-for="dict in dict.type.system_general_switching_status"
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
          v-hasPermi="['system:sys_address:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-sort"
          size="mini"
          @click="toggleExpandAll"
        >展开/折叠</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-if="refreshTable"
      v-loading="loading"
      :data="dataList"
      row-key="code"
      :default-expand-all="isExpandAll"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column prop="id" v-if="false" label="节点ID"></el-table-column>
      <el-table-column prop="code" v-if="false" label="节点code"></el-table-column>
      <el-table-column prop="fullName" v-if="false" label="地址名称(全称)"></el-table-column>
      <el-table-column prop="parentCode" v-if="false" label="父级节点code"></el-table-column>
      <el-table-column prop="shortName" label="地址名称" width="40%"></el-table-column>
      <el-table-column prop="parentName" label="父级名称" width="30%"></el-table-column>
      <el-table-column prop="status" label="状态" width="10%">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.system_general_switching_status" :value="scope.row.addressStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="20%">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="20%" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:sys_address:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['system:sys_address:add']"
          >新增</el-button>
          <el-button
            v-if="scope.row.parentId != 0"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:sys_address:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改部门对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="地址简称" prop="shortName">
              <el-input v-model="form.shortName" placeholder="请输入地址简称名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="地址类型" prop="type">
              <el-select v-model="form.type" placeholder="请选择地址类型">
                <el-option v-for="dict in dict.type.sys_address_type" :key="dict.value" :label="dict.label" :value="dict.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24" v-if="form.parentCode !== '0'">
            <el-form-item label="上级节点" prop="parentCode">
              <treeselect v-model="form.parentCode" :options="treeOptions" :normalizer="normalizer" placeholder="选择上级地址" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="地址全称" prop="fullName">
              <el-input v-model="form.fullName" placeholder="请输入地址全称名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="部门状态">
              <el-radio-group v-model="form.addressStatus">
                <el-radio
                  v-for="dict in dict.type.system_general_switching_status"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
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
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "sysAddress",
  dicts: ['system_general_switching_status','sys_address_type'],
  components: { Treeselect },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 表格树数据
      dataList: [],
      // 部门树选项
      treeOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否展开，默认全部展开
      isExpandAll: true,
      // 重新渲染表格状态
      refreshTable: true,
      // 查询参数
      queryParams: {
        shortName: undefined,
        addressStatus: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        // parentCode: [
        //   { required: true, message: "上级部门不能为空", trigger: "blur" }
        // ],
        shortName: [
          { required: true, message: "地址简称不能为空", trigger: "blur" }
        ],
        fullName: [
          { required: true, message: "地址全称不能为空", trigger: "blur" }
        ],
        type: [
          { required: true, message: "地址类型不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询部门列表 */
    getList() {
      this.loading = true;
      sysAddressApi.getList(this.queryParams).then(response => {
        console.log(response.data);
        this.dataList = this.handleTree(response.data, "code", "parentCode", "children");
        this.loading = false;
      });
    },
    /** 转换部门数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.code,
        label: node.shortName,
        children: node.children
      };
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        code: undefined,
        type: undefined,
        shortName: undefined,
        fullName: undefined,
        parentCode: undefined,
        addressStatus: "1"
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset();
      if (row != undefined) {
        this.form.parentCode = row.code;
      }
      this.open = true;
      this.title = "添加地址";
      sysAddressApi.getList().then(response => {
        this.treeOptions = this.handleTree(response.data, "code", "parentCode", "children");
      });
    },
    /** 展开/折叠操作 */
    toggleExpandAll() {
      this.refreshTable = false;
      this.isExpandAll = !this.isExpandAll;
      this.$nextTick(() => {
        this.refreshTable = true;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      sysAddressApi.getInfo(row.code).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改地址";
      });
      sysAddressApi.listDataExcludeChild(row.code).then(response => {
        this.treeOptions = this.handleTree(response.data, "code", "parentCode", "children");
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.code != undefined) {
            sysAddressApi.update(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            sysAddressApi.add(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除名称为"' + row.shortName + '"的数据项？').then(function() {
        return sysAddressApi.del(row.code);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    }
  }
};
</script>
