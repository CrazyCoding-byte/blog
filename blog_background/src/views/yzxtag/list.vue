<template>
  <div>
    <el-button type="text" @click="clearsubject">添加条标签</el-button>

    <el-dialog title="添加标签" :visible.sync="dialogFormVisible">
      <el-form :model="uploadTag">
        <el-form-item label="标签名" :label-width="formLabelWidth">
          <el-input v-model="uploadTag.tagName" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="upload">确 定</el-button>
      </div>
    </el-dialog>
    <el-table :data="taginfo" style="width: 100%">
      <el-table-column label="序号" prop="id"></el-table-column>
      <el-table-column label="评论昵称" prop="tagName"></el-table-column>
      <el-table-column label="创建时间" prop="createTime"></el-table-column>
      <el-table-column label="修改时间" prop="updateTime"></el-table-column>
      <el-table-column label="是否删除" prop="isDeleted">
        <template slot-scope="scope">
          {{scope.row.isDelete==1?"已删除":"未删除"}}
        </template>
      </el-table-column>
      <el-table-column align="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.row)"
          >Delete
          </el-button
          >
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
  import * as tagapi from '@/api/tag'

  export default {
    name: "list",
    data() {
      return {
        taginfo: [],
        dialogFormVisible: false,
        uploadTag: {},
        formLabelWidth: '120px'
      }
    },
    created() {
      this.showTag();
    },
    methods: {
      clearsubject() {
        this.uploadSubject = {};
        this.dialogFormVisible = true;
      },
      showTag() {
        tagapi.getTag().then(resp => {
          console.log("当前返回的数据", resp)
          this.taginfo = resp.data.item
        })
      },
      upload() {
        console.log("我被调用了")
        tagapi.saveTag(this.uploadTag).then(resp => {
          console.log(resp)
          if (resp.code == 200) {
            this.$message({
              type: 'success',
              message: "添加成功"
            })
            this.dialogFormVisible = false;
            this.showTag();
          } else {
            this.$message({
              type: "添加失败"
            })
            this.dialogFormVisible = false;
            this.showTag();
          }
        })
      },
      handleDelete(row) {
        this.$confirm('确定要删除当前分类吗, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          tagapi.deleteTag(row.id).then(response => {
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            this.showTag();
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
    }
  }
</script>
<style scoped>

</style>

