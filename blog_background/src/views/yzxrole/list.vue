<template>
  <div class="app-container">
    <el-button type="primary" @click="handleAddRole">New Role</el-button>

    <el-table :data="rolesList" style="width: 100%;margin-top:30px;" border>
      <el-table-column align="center" label="id" width="220">
        <template slot-scope="scope">
          {{ scope.row.id }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="名称" width="220">
        <template slot-scope="scope">
          {{ scope.row.roleName }}
        </template>
      </el-table-column>
      <el-table-column align="header-center" label="简介">
        <template slot-scope="scope">
          {{ scope.row.remark }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="Operations">
        <template slot-scope="scope">
          <el-button type="primary" size="small" @click="handleEdit(scope.row)">Edit</el-button>
          <el-button type="danger" size="small" @click="handleDelete(scope.row)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :visible.sync="dialogVisible" :title="dialogType==='edit'?'Edit Role':'New Role'">
      <el-form :model="role" label-width="80px" label-position="left">
        <el-form-item label="名称">
          <el-input v-model="role.roleName" placeholder="name" :disabled="flag"/>
        </el-form-item>
        <el-form-item label="简介">
          <el-input v-model="role.remark" placeholder="mark" :disabled="flag"/>
        </el-form-item>
        <el-form-item label="角色编号">
          <el-input v-model="role.roleCode" placeholder="code" :disabled="flag"/>
        </el-form-item>
        <el-form-item label="Menus" v-if="dialogType==='edit'">
          <el-tree
            ref="tree"
            :data="routesData"
            :props="defaultProps"
            node-key="id"
            :default-checked-keys="checkdata"
            show-checkbox
            class="permission-tree"
          />
        </el-form-item>
      </el-form>
      <div style="text-align:right;">
        <el-button type="danger" @click="dialogVisible=false">Cancel</el-button>
        <el-button type="primary" @click="confirmRole">Confirm</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import * as roleapi from '@/api/role'

  export default {
    data() {
      return {
        //点击edit返回之后的角色
        role: {},
        //角色信息
        rolesList: [],
        dialogVisible: false,
        dialogType: 'new',
        checkStrictly: false,
        defaultProps: {
          children: 'child',
          label: 'name',
        },
        //选中的数据
        checkdata: [],
        routesData: [],
        flag: true
      }
    },
    created() {
      this.getRoles()
    },
    methods: {
      getRoles() {
        roleapi.getrolelist().then(resp => {
          this.rolesList = resp.data.item;
        })
      },
      confirmRole() {
        //修改操作
        if (this.role.id && !isNaN(this.role.id)) {
          const checkedKeys = this.$refs.tree.getCheckedKeys()
          console.log("这是什么东西", checkedKeys)
          roleapi.updaterolepermission(this.role.id, checkedKeys).then(response => {
            this.checkdata = [];
            console.log("当前的roleid",this.role.id)
            roleapi.getrolemenu(this.role.id).then(resp => {
              this.role = resp.data.item;
              if(resp.data.item.yzxPermissions){
                resp.data.item.yzxPermissions.map(item => {
                  this.checkdata.push(item.id);
                })
                this.$nextTick(() => {
                  this.$refs.tree.setCheckedKeys(this.checkdata);
                });
              }
            })
            this.$message({
              type: "success",
              message: "修改成功!"
            })
            this.dialogVisible = false
          })
        } else {
          //添加操作
          roleapi.savepermission(this.role).then(resp => {
            this.$message({
              type: "success",
              message: "添加成功"
            })
            this.dialogVisible = false
            this.getRoles();
          })
        }
      },
      handleAddRole() {
        this.flag = false
        this.dialogType = 'new'
        this.dialogVisible = true
        this.role = {};
      },
      handleEdit(row) {
        this.flag = true
        this.dialogType = 'edit'
        this.dialogVisible = true
        this.checkdata=[]
        this.$nextTick(() => {
          roleapi.showpermission().then(resp => {
            //当前的所有数据
            this.routesData = resp.data.item;
            //确定选中的数据
            roleapi.getrolemenu(row.id).then(resp => {
              this.role = resp.data.item;
              console.log("选中的数据",resp.data.item)
              if(resp.data.item.yzxPermissions){
                resp.data.item.yzxPermissions.map(item => {
                  this.checkdata.push(item.id);
                })
                this.$nextTick(() => {
                  this.$refs.tree.setCheckedKeys(this.checkdata);
                });
              }
            })
          })
        })
      },
      handleDelete(row) {
        this.$confirm('确定要删除当前的角色吗?', 'Warning', {
          confirmButtonText: 'Confirm',
          cancelButtonText: 'Cancel',
          type: 'warning'
        })
          .then(() => {
              console.log(row)
              roleapi.deletepermission(row.id).then(resp => {
                this.$message({
                  type: 'success',
                  message: '删除成功'
                })
                this.getRoles()
              })
            }
          )
          .catch(err => {
            console.error(err)
          })
      },

    }
  }
</script>

<style lang="scss" scoped>
  .app-container {
    .roles-table {
      margin-top: 30px;
    }

    .permission-tree {
      margin-bottom: 30px;
    }
  }
</style>

