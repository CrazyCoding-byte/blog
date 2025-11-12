<template>
  <div>
    <el-input v-model="permissonname" style="width: 200px" placeholder="搜索">
    </el-input>
    <el-button round @click="searchprimission">确定搜索</el-button>
    <el-button class="save" @click="handleEditorSave()"
               type="primary"
    >添加一条数据
    </el-button>
    <el-table :data="permissioninfo" style="width: 100%">
      <el-table-column label="序号" prop="id"></el-table-column>
      <el-table-column label="名称" prop="name"></el-table-column>
      <el-table-column label="父id" prop="pid"></el-table-column>
      <el-table-column label="类型" prop="type">
        <template slot-scope="scope">
          {{scope.row.type==0?"菜单":"按钮"}}
        </template>
      </el-table-column>
      <el-table-column label="权限值" prop="permissionValue"></el-table-column>
      <el-table-column label="图标" prop="icon"></el-table-column>
      <el-table-column label="访问路径" prop="path"></el-table-column>
      <el-table-column label="组件路径" prop="component"></el-table-column>
      <el-table-column label="权限是否删除" prop="isDeleted">
        <template slot-scope="scope">
          {{scope.row.isDeleted==0?"未删除":"已经删除"}}
        </template>
      </el-table-column>
      <el-table-column label="创建时间" prop="createTime"></el-table-column>
      <el-table-column label="修改时间" prop="updateTime"></el-table-column>
      <el-table-column align="right">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEditorSave(scope.row)"
                     type="primary"
          >edit
          </el-button
          >
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.row)"
          >deleted
          </el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="menu菜单" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="名称" :label-width="formLabelWidth" style="width: 300px">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="父id" :label-width="formLabelWidth" style="width: 300px">
          <el-input v-model="form.pid" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="类型" :label-width="formLabelWidth" style="width: 300px">
          <el-input v-model="form.type" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="权限值" :label-width="formLabelWidth" style="width: 300px">
          <el-input v-model="form.permissionValue" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图标" :label-width="formLabelWidth" style="width: 300px">
          <el-input v-model="form.icon" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="访问路径" :label-width="formLabelWidth" style="width: 300px">
          <el-input v-model="form.path" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="组件路径" :label-width="formLabelWidth" style="width: 300px">
          <el-input v-model="form.component" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="upload">确 定</el-button>
      </div>
    </el-dialog>
    <div align="center">
      <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="current"
        :page-size="limit"
        :page-count="pages"
        layout="total, prev, pager, next,jumper"
        :total="total"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
  import * as permissionapi from '@/api/permission'

  export default {
    name: "list.vue",
    data() {
      return {
        permissioninfo: [],
        permissonname: "",
        current: 1, //当前页
        limit: 5, //当前显示多少数据
        total: 20,//总数据
        pages: 0,
        dialogFormVisible: false,
        form: {},
        formLabelWidth: "100px"
      }
    },
    created() {
      this.showpermission();
    },
    methods: {
      showpermission() {
        console.log("当前的", this.permissonname)
        permissionapi.listpermission(this.current, this.limit, this.permissonname).then(response => {
          console.log("当前的数据", response)
          this.permissioninfo = response.data.item.records;
          this.current = response.data.item.current;
          this.limit = response.data.item.size;
          this.total = response.data.item.total;
          this.pages = response.data.item.pages;
        })
      },
      handleSizeChange() {
        this.showpermission();
      },
      handleCurrentChange() {
        this.showpermission();
      },
      searchprimission() {
        console.log(this.permissonname)
        this.showpermission();
      },
      handleEditorSave(row) {
        if (row) {
          console.log("当前要修改的信息", row)
          this.dialogFormVisible = true;
          permissionapi.getpermission(row.id).then(resp => {
            console.log("当前响应的数据", resp)
            this.form = resp.data.item;
          })
        }else{
          this.form={};
          this.dialogFormVisible = true;
        }
      },
      upload() {
        //如果当前有id的话就是修改操作
        if (this.form.id&&!isNaN(this.form.id)) {
          permissionapi.updatepermission(this.form).then(resp => {
            if (resp.code == 200) {
              this.$message({
                type: "success",
                message: "修改成功"
              })
              this.showpermission();
              this.dialogFormVisible = false;
            } else {
              this.$message({
                type: "error",
                message: "修改失败"
              })
              this.showpermission();
              this.dialogFormVisible = false;
            }
          })
        } else {
          //没有id的话就是添加操作
          permissionapi.savepermission(this.form).then(resp => {
            if (resp.code == 200) {
              this.$message({
                type: "success",
                message: "添加成功"
              })
              this.showpermission();
              this.dialogFormVisible = false;
            } else {
              this.$message({
                type: "error",
                message: "添加失败"
              })
              this.showpermission();
              this.dialogFormVisible = false;
            }
          })
        }
      },
      handleDelete(row){
        this.$confirm(`确定要删除当前${row.name}吗, 是否继续?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          permissionapi.deletepermission(row.id).then(response => {
            if (response.code == 200) {
              this.$message({
                type: "success",
                message: "删除成功"
              })
              this.showpermission();
              this.dialogFormVisible = false;
            } else {
              this.$message({
                type: "error",
                message: "删除失败"
              })
              this.showpermission();
              this.dialogFormVisible = false;
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      }
    }
  }
</script>

<style scoped>
  .save {
    float: right;
  }
</style>
