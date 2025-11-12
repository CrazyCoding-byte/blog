<template>
  <div>
    <div style="color: blue;cursor: pointer" @click="clearSaveInfo">点击我添加数据</div>
    <el-table
      :data="audioinfo.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
      style="width: 100%">
      <el-table-column
        label="名称"
        prop="id">
      </el-table-column>
      <el-table-column
        label="作者"
        prop="artist">
      </el-table-column>
      <el-table-column
        label="名称"
        prop="name">
      </el-table-column>
      <el-table-column
        label="播放路径"
        prop="url">
      </el-table-column>
      <el-table-column
        label="封面"
        prop="url">
        <template slot-scope="scope">
          <el-image
            style="width: 100px; height: 100px"
            :src="scope.row.cover"
          ></el-image>
        </template>
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

    <el-dialog title="发布歌曲" :visible.sync="dialogFormVisible">
      <el-form :model="saveAudioinfo">
        <el-form-item label="歌曲名称" :label-width="formLabelWidth">
          <el-input v-model="saveAudioinfo.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="歌曲作者" :label-width="formLabelWidth">
          <el-input v-model="saveAudioinfo.artist" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="歌曲源文件" :label-width="formLabelWidth">
          <el-input v-model="saveAudioinfo.url" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
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
        </el-form-item>
        <el-form-item label="歌曲主题颜色" :label-width="formLabelWidth">
          <el-input v-model="saveAudioinfo.theme" autocomplete="off"></el-input>
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
  import * as  audioapi from "@/api/audio.js"

  export default {
    name: "list",
    data() {
      return {
        search: '',
        audioinfo: [],
        dialogFormVisible: false,
        saveAudioinfo: {},
        formLabelWidth: "120px",
        imurl: "",
      }
    },
    created() {
      this.showAudio();
    },
    methods: {
      handleEdit(index, row) {
        console.log(index, row);
      },
      clearSaveInfo() {
        this.saveAudioinfo = {};
        this.dialogFormVisible = true;
        this.imurl=""
      },
      showAudio() {
        audioapi.showAudio().then(resp => {
          this.audioinfo = resp.data.item;
          console.log("audio", this.audioinfo)
        })
      },
      handleAvatarSuccess(res, file) {
        //显示图片
        this.imurl = URL.createObjectURL(file.raw);
        this.saveAudioinfo.cover = res.data.url;
      },
      save() {
        if(!this.saveAudioinfo||this.saveAudioinfo==null||this.saveAudioinfo==undefined||JSON.stringify(this.saveAudioinfo)=='{}'){
          this.$message({
            type:"error",
            message:"添加的数据不能为空"
          })
        }else{
          audioapi.saveAudio(this.saveAudioinfo).then(resp=>{
            this.$message({
              type:"success",
              message:"添加成功"
            })
            this.showAudio();
            this.dialogFormVisible=false
          })
        }

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
      handleDelete(index, row) {
        console.log(index, row);
        this.$confirm('是否要删除当前的audio, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          audioapi.removeAudio(row.id).then(resp=>{
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            this.showAudio();
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
