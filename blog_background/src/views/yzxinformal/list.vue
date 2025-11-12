<template>
  <div>
    <el-table
      :data="informal"
      border
      style="width: 100%">
      <el-table-column
        prop="id"
        label="序号"
      >
      </el-table-column>
      <el-table-column
        prop="informalTitle"
        label="随感标题"
      >
      </el-table-column>
      <el-table-column
        label="随感头像"
        width="180"
        prop="salt"
      >
        <template slot-scope="scope">
          <el-image
            style="width: 100px; height: 100px"
            :src="scope.row.cover"
            fit="fit"></el-image>
        </template>
      </el-table-column>
      <el-table-column
        prop="informalContent"
        label="随感内容"
      >
      </el-table-column>
      <el-table-column
        prop="tagId"
        label="tagId"
      >
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="创建日期"
      >
      </el-table-column>
      <el-table-column
        prop="updateTime"
        label="修改日期"
      >
      </el-table-column>
      <el-table-column
        prop="isDelete"
        label="是否删除"
      >
      </el-table-column>
      <el-table-column
        label="操作"
      >
        <template slot-scope="scope">
          <el-button @click="updateinformal(scope.row.id)" type="text" size="small">编辑</el-button>
          <el-button type="text" size="small" @click="deleteinformal(scope.row.id)">删除</el-button>
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
  import * as informalapi from '@/api/informal'
  export default {
    name: "list.vue",
    data() {
      return {
        informal: [],
        current: 1, //当前页
        limit: 5, //当前显示多少数据
        total: 20,//总数据
        pages: 0,
      }
    },
    created() {
      this.showinformal();

    },
    methods: {
      showinformal() {
        informalapi.listinformal(this.current, this.limit).then(resp => {
          this.informal = resp.data.item.records;
          this.current = resp.data.item.current;
          this.limit = resp.data.item.size;
          this.total = resp.data.item.total;
          this.pages = resp.data.item.pages;
        })
      },
      handleCurrentChange() {
        this.showinformal();
      },
      handleSizeChange() {
        this.showinformal();
      },
      updateinformal(id) {
        this.$router.push({
          path: `/yzxinformal/save/${id}`
        })
      },
      deleteinformal(id) {
        this.$confirm('确定要删除当前的随感吗?, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          informalapi.deleteinformal(id).then(resp => {
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            this.showinformal();
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      }

    },


  }
</script>

<style scoped>

</style>
