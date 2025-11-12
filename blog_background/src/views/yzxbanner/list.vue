<template>
  <div>
    <div class="savebutton" @click="savebanner">添加</div>
    <el-table
      :data="bannerinfo"
      style="width: 100%">
      <el-table-column
        prop="id"
        label="序号"
        width="180">
      </el-table-column>
      <el-table-column
        prop="articleId"
        label="博文序号">
      </el-table-column>
      <el-table-column
        prop="articleTitle"
        label="博文标题">
      </el-table-column>
      <el-table-column
        prop="articleCover"
        label="博文图片"
        width="180">
        <template slot-scope="scope">
          <el-image
            style="width: 100px; height: 100px"
            :src="scope.row.articleCover"
          ></el-image>
        </template>
      </el-table-column>
      <el-table-column
        prop="subjectId"
        label="分类序号"
        width="180">
      </el-table-column>
      <el-table-column
        prop="subjectName"
        label="分类名称"
        width="180">
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="创建日期"
        width="180">
      </el-table-column>
      <el-table-column
        prop="updateTime"
        label="修改日期"
        width="180">
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="120">
        <template slot-scope="scope">
          <el-button
            @click="deleteRow(scope.row.id)"
            type="text"
            size="small">
            移除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="要添加成为banner的博文" :visible.sync="dialogFormVisible">
          <el-select v-model="saveBannerId.articleId" placeholder="请选择">
            <el-option v-for="item in articles" :key="item.articleId" :label="item.articleTitle" :value="item.articleId" ></el-option>
          </el-select>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import * as bannerapi from '@/api/banner'

  export default {
    name: "list.vue",
    data() {
      return {
        bannerinfo: [],
        dialogFormVisible: false,
        articles: [],
        saveBannerId: {},
      }
    },
    created() {
      this.showbanner();
    },
    methods: {
      showbanner() {
        bannerapi.listbanner().then(resp => {
          this.bannerinfo = resp.data.item;
        })
      },
      deleteRow(id) {
        this.$confirm('确定要删除当前的banner吗?, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          bannerapi.deleteBanner(id).then(resp => {
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            this.showbanner();
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      savebanner() {
        this.articles = [];
        this.dialogFormVisible = true;
        bannerapi.listarticles().then(resp => {
          console.log("当前所有的博文", resp)
          this.articles = resp.data.item;
        })
      },
      save() {
        bannerapi.saveBanner(this.saveBannerId).then(resp=>{
          this.$message({
            type:"success",
            message:"添加成功"
          })
          this.dialogFormVisible=false;
          this.showbanner();
        })
      }
    }
  }
</script>

<style scoped>
  .savebutton {
    width: 100px;
    border-top-left-radius: 20px;
    border-top-right-radius: 20px;
    border-bottom-right-radius: 20px;
    border-bottom-left-radius: 20px;
    height: 50px;
    background-color: #0a76a4;
    cursor: pointer;
    text-align: center;
    line-height: 50px
  }

  .savebutton:hover {
    background-color: #4A9FF9;
  }
</style>
