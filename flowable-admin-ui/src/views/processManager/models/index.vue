<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input  placeholder="模型key" style="width: 200px;" class="filter-item"
                 @keyup.enter.native="handleFilter" />
      <el-input  placeholder="模型名称" style="width: 200px;" class="filter-item"
                 @keyup.enter.native="handleFilter" />
      <el-dropdown split-button type="primary" class="filter-item">
        <i class="el-icon-search el-icon--left"></i>查询
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item icon="el-icon-zoom-out">重置</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <el-button-group>
        <el-button icon="el-icon-plus" type="primary" class="filter-item" @click="btnCreate">新增
        </el-button>
      </el-button-group>
    </div>
    <!-- 表格-->
    <el-table
      :data="list"
      ref="multipleTable"
      @selection-change="selectionChange"
      border
      fit
      highlight-current-row
      style="width: 100%;"
      :cell-style="{padding:'3px'}"
    >
      <el-table-column type="selection" align="center">
      </el-table-column>
      <el-table-column label="模型key" prop="key" align="center">
        <template slot-scope="scope"><span>{{ scope.row.key }}</span></template>
      </el-table-column>
      <el-table-column label="模型名称" prop="name" align="center">
        <template slot-scope="scope"><span>{{ scope.row.name }}</span></template>
      </el-table-column>
      <el-table-column label="模型类别" prop="category" align="center">
        <template slot-scope="scope">
          <span v-html="formatDictText(dicts.processCategory,scope.row.category)"></span>
        </template>
      </el-table-column>
      <el-table-column label="版本" prop="version" align="center">
        <template slot-scope="scope"><span>{{ scope.row.version }}</span></template>
      </el-table-column>
      <el-table-column label="是否发布" prop="deployed" align="center">
        <template slot-scope="scope"><span>{{ scope.row.deployed?'是':'否' }}</span></template>
      </el-table-column>
      <el-table-column label="操作" width="110" align="center">
        <template slot-scope="{row}">
          <el-dropdown>
            <span class="el-dropdown-link">操作<i class="el-icon-arrow-down el-icon--right"></i></span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item icon="el-icon-view" @click.native="btnView(row)">查看</el-dropdown-item>
              <el-dropdown-item icon="el-icon-edit" divided @click.native="btnUpdate(row)">
                修改
              </el-dropdown-item>
              <el-dropdown-item icon="el-icon-view" @click.native="btnUpdateModel(row)">
                流程设计
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>
    <!-- 弹窗-->
    <el-dialog title="流程模型" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" :disabled="dialogStatus==='view'"
               label-position="right" label-width="110px">
        <el-form-item label="模型key" prop="key">
          <el-input v-model="temp.key"/>
        </el-form-item>
        <el-form-item label="模型名称" prop="name">
          <el-input v-model="temp.name"/>
        </el-form-item>
        <el-form-item label="模型类别" prop="category">
          <el-select v-model="temp.category" placeholder="模型类别">
            <el-option v-for="item in dicts.processCategory" :key="item.value" :label="item.content" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="模型描述" prop="description">
          <el-input v-model="temp.description"/>
        </el-form-item>
        <el-form-item label="租户ID" prop="tenantId">
          <el-input v-model="temp.tenantId"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button icon="el-icon-close" @click="dialogFormVisible = false">取消</el-button>
        <el-button v-if="dialogStatus!=='view'" icon="el-icon-check" type="primary"
                   @click="dialogStatus==='create'?createData():updateData()">确定
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { getList } from '@/api/table'
  import {getAction, putAction, postAction, deleteAction} from '@/api/manage'
  import {Message} from 'element-ui'

  export default {
    filters: {
      statusFilter(status) {
        const statusMap = {
          published: 'success',
          draft: 'gray',
          deleted: 'danger'
        }
        return statusMap[status]
      }
    },
    data() {
      return {
        dicts: [],
        list: null,
        listLoading: true,
        selectedRecords: [],
        dialogFormVisible: false,
        dialogStatus: '',
        temp: {
          id: undefined,
          key: '',
          name: '',
          category: '',
          description: '',
          tenantId: ''
        },
        rules: {
          id: [{required: true, message: '该项不能为空', trigger: 'change'}],
          key: [{required: true, message: '该项不能为空', trigger: 'change'}],
          name: [{required: true, message: '该项不能为空', trigger: 'change'}]
        }
      }
    },
    created() {
      this.fetchData()
    },
    methods: {
      /* 初始化*/
      fetchData() {
        this.listLoading = true
        getList().then(response => {
          console.log(response)
          this.list = response.datas
          this.listLoading = false
        })
      },
      /* 全选*/
      selectionChange(selectedRecords) {
        this.selectedRecords = selectedRecords
      },
      btnView(row) {
        this.temp = Object.assign({}, row)
        this.dialogStatus = 'view'
        this.dialogFormVisible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].clearValidate()
        })
      },
      /* 添加模板*/
      btnCreate() {
        this.resetTemp()
        this.dialogStatus = 'create'
        this.dialogFormVisible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].clearValidate()
        })
      },
      /* 重新初始化表单*/
      resetTemp() {
        this.temp = {
          id: undefined,
          key: '',
          name: '',
          category: '',
          description: '',
          tenantId: ''
        }
      },
      btnUpdate(row) {
        // this.temp = Object.assign({}, row)
        this.temp.id = row.id
        this.temp.key = row.key
        this.temp.name = row.name
        this.temp.category = row.category
        this.temp.description = row.description
        this.temp.tenantId = row.tenantId
        this.dialogStatus = 'update'
        this.dialogFormVisible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].clearValidate()
        })
      },
      /* 流程设计*/
      btnUpdateModel(row) {
        this.$router.push({path: '/processManager/modelDesigner', query: {modelId: row.id}})
      },
      /* 确定添加模板*/
      createData() {
        console.log(this.temp)
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            postAction('/vue-admin-template/rest/model/saveModel', this.temp).then(({msg}) => {
              this.dialogFormVisible = false
              Message.success(msg)
              this.fetchData()
            })
          }
        })
      },
      updateData() {
        console.log(this.temp)
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            putAction('/vue-admin-template/rest/model/updateModel', this.temp).then(({msg}) => {
              this.dialogFormVisible = false
              Message.success(msg)
              this.fetchData()
            })
          }
        })
      },
    }
  }
</script>
