<template>
  <div>
    <el-button @click="clearUser">Add User</el-button>
    <el-table
      :data="userinfo"
      style="width: 100%">
      <el-table-column
        label="username"
        width="180"
        prop="username"
      >
      </el-table-column>
      <el-table-column
        label="password"
        width="180"
        prop="password"
      >
      </el-table-column>
      <el-table-column
        label="nickName"
        width="180"
        prop="nickName"
      >
      </el-table-column>
      <el-table-column
        label="cover"
        width="180"
        prop="salt"
      >
        <template slot-scope="scope">
          <el-image
            style="width: 100px; height: 100px"
            :src="scope.row.salt"
            fit="fit"></el-image>
        </template>
      </el-table-column>
      <el-table-column
        label="是否启用"
        width="180"
        prop="isDeleted"
      >
        <template slot-scope="scope">
          {{scope.row.isDeleted==0?"启用":"已删除"}}
        </template>
      </el-table-column>
      <el-table-column
        label="创建日期"
        width="180">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="修改日期"
        width="180">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.updateTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="saverelation( scope.row)">添加关联
          </el-button>
          <el-button
            size="mini"
            @click="relation( scope.row)">关联信息
          </el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete( scope.row)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="添加用户" :visible.sync="dialogFormVisible">
      <el-form :model="uploaduserinfo">
        <el-form-item label="username" :label-width="formLabelWidth" style="width: 300px">
          <el-input v-model="uploaduserinfo.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="password" :label-width="formLabelWidth" style="width: 300px">
          <el-input v-model="uploaduserinfo.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="nickname" :label-width="formLabelWidth" style="width: 300px">
          <el-input v-model="uploaduserinfo.nickName" autocomplete="off"></el-input>
        </el-form-item>
        <el-upload
          list-type="picture-card"
          class="avatar-uploader"
          action="http://localhost:8003/oss/upload"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
        >
          <img v-if="imurl" :src="imurl" class="avatar"/>
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveuser">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="关联" :visible.sync="dialogTableVisible">
      <el-table :data="roleuserinfo">
        <el-table-column property="nickName" label="用户昵称" width="150"></el-table-column>
        <el-table-column property="remark" label="角色详情" width="200"></el-table-column>
        <el-table-column property="userId" label="用户id"></el-table-column>
        <el-table-column property="roleId" label="角色id"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button v-if="scope.row"
                       size="mini"
                       type="danger"
                       @click="removerelation( scope.row)">删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

    <el-dialog title="角色信息" :visible.sync="dialogrelation">
      <el-form :model="saveuserroleinfo">
        <el-form-item label="请选择" :label-width="formLabelWidth">
          <el-select v-model="saveuserroleinfo.roleId" placeholder="请选择角色">
            <el-option
              v-for="item in roleinfo"
              :key="item.id"
              :label="item.roleName"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogrelation = false">取 消</el-button>
        <el-button type="primary" @click="saveuserrealtion">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

  import * as userapi from '@/api/user'

  export default {
    name: "list",
    data() {
      return {
        userinfo: [],
        dialogFormVisible: false,
        uploaduserinfo: {},
        imurl: "",
        formLabelWidth: "80px",
        dialogTableVisible: false,
        roleuserinfo: {},
        dialogrelation: false,
        roleinfo: [],
        saveuserroleinfo: {},
        userid: "",
      }
    },
    created() {
      this.showUserInfo();
    },
    methods: {
      showUserInfo() {
        userapi.userlist().then(resp => {
          this.userinfo = resp.data.item;
        })
      },
      clearUser() {
        this.uploaduserinfo = {}
        this.dialogFormVisible = true
      },
      handleAvatarSuccess(res, file) {
        //显示图片
        this.imurl = URL.createObjectURL(file.raw);
        this.uploaduserinfo.salt = res.data.url;
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === "image/jpeg";
        const isLt2M = file.size / 1024 / 1024 < 10;

        if (!isJPG) {
          this.$message.error("上传头像图片只能是 JPG 格式!");
        }
        if (!isLt2M) {
          this.$message.error("上传头像图片大小不能超过 10MB!");
        }
        return isJPG && isLt2M;
      },
      relation(row) {
        this.roleuserinfo = []
        this.dialogTableVisible = true
        userapi.getrelation(row.id).then(resp => {
          console.log(resp)
          var relation = []
          relation.push(resp.data.item)
          this.roleuserinfo = relation;
        })
      },
      saveuser() {
        userapi.saveuser(this.uploaduserinfo).then(resp => {
          this.$message({
            type: "success",
            message: "添加成功"
          })
          this.dialogFormVisible = false;
          this.showUserInfo();
        })
      },
      handleDelete(row) {
        this.$confirm('此操作将永久删除当前用户吗, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          userapi.deleteuser(row.id).then(resp => {
            if (resp.code == 200) {
              this.$message({
                type: "success",
                message: "删除关联成功"
              })
              this.showUserInfo();
            } else {
              this.$message({
                type: "error",
                message: "删除关联失败"
              })
              this.showUserInfo();
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      saverelation(row) {
        this.userid = row.id;
        this.dialogrelation = true;
        this.saveuserroleinfo = {}
        userapi.listrole().then(resp => {
          this.roleinfo = resp.data.item;
        })
      },
      saveuserrealtion() {
        this.saveuserroleinfo.userId = this.userid;
        userapi.saveRelation(this.saveuserroleinfo).then(resp => {
          this.$message({
            type: "success",
            message: "关联成功"
          })
          this.dialogrelation = false
        })
      },
      removerelation(row) {
        this.$confirm('此操作将永久删除当前关联, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          userapi.removerelation(row.userId, row.roleId).then(resp => {
            if (resp.code == 200) {
              this.$message({
                type: "success",
                message: "删除关联成功"
              })
              this.dialogTableVisible = false
            } else {
              this.$message({
                type: "error",
                message: "删除关联失败"
              })
              this.dialogTableVisible = false
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
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }

  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 148px;
    height: 148px;
    line-height: 148px;
    text-align: center;
  }

  .avatar {
    width: 148px;
    height: 148px;
    display: block;
  }

  .template {
    margin: 0px;
    padding: 0px;
  }

</style>
