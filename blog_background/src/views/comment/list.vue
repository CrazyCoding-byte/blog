<template>
  <div>
    <el-button type="text" @click="clearcomment">添加条评论</el-button>

    <el-dialog title="收货地址" :visible.sync="dialogFormVisible">
      <el-form :model="uploadComment">
        <el-form-item label="评论的昵称" :label-width="formLabelWidth">
          <el-input v-model="uploadComment.userNickname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="评论的qq" :label-width="formLabelWidth">
          <el-input v-model="uploadComment.userQq" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="评论的博文id" :label-width="formLabelWidth">
          <el-input v-model="uploadComment.articleId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="评论的内容" :label-width="formLabelWidth">
          <el-input v-model="uploadComment.commentContent" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="评论的博文title" :label-width="formLabelWidth">
          <el-input v-model="uploadComment.articleTitle" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="upload">确 定</el-button>
      </div>
    </el-dialog>
    <el-table :data="commentinfo" style="width: 100%">
      <el-table-column label="序号" prop="commentId"></el-table-column>
      <el-table-column label="评论昵称" prop="userNickname"></el-table-column>
      <el-table-column label="评论qq" prop="userQq"></el-table-column>
      <el-table-column label="博文id" prop="articleId"></el-table-column>
      <el-table-column label="博文标题" prop="articleTitle"></el-table-column>
      <el-table-column label="评论内容" prop="commentContent"></el-table-column>
      <el-table-column label="创建时间" prop="createTime"></el-table-column>
      <el-table-column label="修改时间" prop="updateTime"></el-table-column>
      <el-table-column label="是否删除" prop="isDelete">
        <template slot-scope="scope">
          {{scope.row.isDelete==1?"已删除":"未删除"}}
        </template>
      </el-table-column>
      <el-table-column align="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete( scope.row)"
          >Delete
          </el-button
          >
        </template>
      </el-table-column>
    </el-table>
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
</template>

<script>
  import * as commentapi from '@/api/comment'

  export default {
    name: "list",
    data() {
      return {
        commentinfo: [],
        current: 1, //当前页
        limit: 5, //当前显示多少数据
        total: 20,//总数据
        pages: 0,
        dialogFormVisible: false,
        uploadComment: {},
        formLabelWidth: '120px'
      }
    },
    created() {
      this.showComment();
    },
    methods: {
      clearcomment() {
        this.uploadComment = {};
        this.dialogFormVisible = true;
      },
      showComment() {
        commentapi.commentlist(this.current, this.limit).then(resp => {
          console.log("当前返回的数据", resp)
          this.commentinfo = resp.data.item.records;
          this.current = resp.data.item.current;
          this.limit = resp.data.item.size;
          this.total = resp.data.item.total;
          this.pages = resp.data.item.pages;
        })
      },
      upload() {
        console.log("我被调用了")
        commentapi.saveComment(this.uploadComment).then(resp => {
          console.log(resp)
          if (resp.code == 200) {
            this.$message({
              type: 'success',
              message: "添加成功"
            })
            this.dialogFormVisible = false;
            this.showComment();
          } else {
            this.$message({
              type: "添加失败"
            })
            this.dialogFormVisible = false;
            this.showComment();
          }
        })
      },
      handleSizeChange() {
        this.showComment();
      },
      handleCurrentChange() {
        this.showComment();
      },
      handleDelete(row) {
        this.$confirm('确定要删除当前评论吗, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          commentapi.deletecomment(row.commentId).then(response => {
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            this.showComment();
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
