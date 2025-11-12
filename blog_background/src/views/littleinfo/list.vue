<template>
    <div>
      <div style="color: blue;cursor: pointer" @click="clearSaveInfo">点击我添加数据</div>
      <el-table
        :data="littleinfo.filter(data => !search || data.content.toLowerCase().includes(search.toLowerCase()))"
        style="width: 100%">
        <el-table-column
          label="id"
          prop="id">
        </el-table-column>
        <el-table-column
          label="内容"
          prop="content">
        </el-table-column>
        <el-table-column
          label="创建时间"
          prop="createTime">
        </el-table-column>
        <el-table-column
          label="修改时间"
          prop="updateTime">
        </el-table-column>
        <el-table-column
          label="是否删除"
          prop="isDeleted">
        </el-table-column>
        <el-table-column
          align="right">
          <template slot="header" slot-scope="scope">
            <el-input
              v-model="search"
              size="mini"
              placeholder="输入关键字搜索"/>
          </template>
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)">Delete
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-dialog title="tittleinfo" :visible.sync="dialogFormVisible">
        <el-form :model="saveLittleInfo">
          <el-form-item label="发布内容" :label-width="formLabelWidth">
            <el-input v-model="saveLittleInfo.content" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="save()">确 定</el-button>
        </div>
      </el-dialog>
    </div>
</template>

<script>
  import * as littleInfoapi from '@/api/littleinfo.js'
    export default {
        name: "list.vue",
      data(){
          return{
            littleinfo:[],
            saveLittleInfo:{},
            dialogFormVisible:false,
            search:"",
            formLabelWidth:"120px",
          }
      },
      created() {
          this.showLittleInfo();
      },
      methods:{
        clearSaveInfo(){
          this.dialogFormVisible=true;
          this.saveLittleInfo={};
        },
          showLittleInfo(){
          littleInfoapi.getLittleInfo().then(resp=>{
            console.log(resp)
             this.littleinfo=resp.data.item;
          })
        },
        handleDelete(index,row){
          console.log(index, row);
          this.$confirm('是否要删除当前的audio, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            littleInfoapi.removeLittleInfo(row.id).then(resp=>{
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
              this.showLittleInfo();
            })
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消删除'
            });
          });
        },
        save(){
         littleInfoapi.savelittleInfo(this.saveLittleInfo).then(resp=>{
           this.$message({
             type:"success",
             message:"添加成功",
           })
           this.dialogFormVisible=false;
           this.showLittleInfo();
         })
        },
      }
    }
</script>

<style scoped>

</style>
