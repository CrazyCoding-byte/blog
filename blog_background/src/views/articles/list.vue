<template>
  <div>
    <div id="search">
      <el-input
        style="width: 200px; margin-left: 40px"
        v-model="searchObj.articleTitle"
        placeholder="博文标题"
      ></el-input>
      <el-date-picker
        v-model="searchObj.createTime"
        value-format="yyyy-MM-dd HH:mm:ss"
        type="datetime"
        placeholder="选择日期时间"
      >
      </el-date-picker>
      <el-date-picker
        v-model="searchObj.updateTime"
        value-format="yyyy-MM-dd HH:mm:ss"
        type="datetime"
        placeholder="选择日期时间"
      >
      </el-date-picker>
      <el-button type="primary" icon="el-icon-search" @click="searchTeacher()"
      >搜索
      </el-button
      >
    </div>
    <el-table :data="blogInfo" style="width: 100%">
      <el-table-column label="序号" prop="articleId"></el-table-column>
      <el-table-column label="博文标题" prop="articleTitle"></el-table-column>
      <el-table-column label="博文内容" :show-overflow-tooltip='true' prop="articleContent">
        <template slot-scope='scope'>
          <el-popover
            placement="top-start"
            width="400"
            trigger="hover">
            <span>{{scope.row.articleContent}}</span>
            <span slot="reference">{{scope.row.articleContent.substr(1,20) + '...'}}</span>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="封面" prop="articleCover">
          <template slot-scope="scope">
            <el-image
              style="width: 100px; height: 100px"
              :src="scope.row.articleCover"
              fit="fit"></el-image>
          </template>
      </el-table-column>
      <el-table-column label="浏览量" prop="articleViews"></el-table-column>
      <el-table-column label="标签id" prop="tagId"></el-table-column>
      <el-table-column label="分类" prop="subjectId"></el-table-column>
      <el-table-column label="创建时间" prop="createTime"></el-table-column>
      <el-table-column label="修改时间" prop="updateTime"></el-table-column>
      <el-table-column align="right">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.row)"
          >Edit
          </el-button
          >
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
  import * as articleapi from '@/api/articles'

  export default {
    name: "list",
    data() {
      return {
        searchObj: {
          articleTitle: '',
          createTime: '',
          updateTime: '',
        },
        blogInfo: [],
        current: 1, //当前页
        limit: 5, //当前显示多少数据
        total: 20,//总数据
        pages: 0
      }
    },
    created() {
      this.showInfo();
    },
    methods: {
      showInfo() {
        articleapi.showArticles(this.current, this.limit, this.searchObj).then(response => {
          console.log("当前的数据", response)
          this.blogInfo = response.data.item.records;
          this.current = response.data.item.current;
          this.limit = response.data.item.size;
          this.total = response.data.item.total;
          this.pages = response.data.item.pages;
        })
      },
      handleSizeChange() {
        this.showInfo();
      },
      handleCurrentChange() {
        this.showInfo();
      },
      handleEdit(row) {
        console.log("当前的row",row)
        this.$router.push(
          {
            path:"/articles/save/"+row.articleId,
          }
        )
      },
      handleDelete(row) {
        this.$confirm('确定要删除这篇文章吗, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          articleapi.deleteArticles(row.articleId).then(response=>{
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
            this.showInfo();
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      },
      searchTeacher() {
        this.showInfo();
      },
    }
  }
</script>

<style scoped>

</style>
