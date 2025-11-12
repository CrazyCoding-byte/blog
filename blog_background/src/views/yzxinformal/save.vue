<template>
  <div>
    <!-- 表单 -->
    <el-form ref="form" :model="informal" label-width="80px">
      <el-form-item label="随感的标题" style="width: 300px">
        <el-input v-model="informal.informalTitle"></el-input>
      </el-form-item>
      <el-form-item label="标签">
        <el-select
          v-model="informal.tagId"
          placeholder="请选择"
        >
          <el-option
            v-for="one in tagInfo"
            :key="one.id"
            :label="one.tagName"
            :value="one.id"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-upload
          list-type="picture-card"
          class="avatar-uploader"
          action="http://47.92.28.197:8003/oss/upload"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
        >
          <img v-if="imurl" :src="imurl" class="avatar"/>
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>
      <el-form-item label="随感的内容">
        <tinymce
          :key="tinymceFlag"
          :height="300"
          v-model="informal.informalContent"
        ></tinymce>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="save">提交随感</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import * as informalapi from '@/api/informal'
  import tinymce from "@/components/Tinymce";
  import * as tagapi from '@/api/tag'

  export default {
    name: "save.vue",
    data() {
      return {
        informal: {},
        imurl: "",
        tagInfo: [],
        tinymceFlag:1,
      }
    },
    components: {
      tinymce
    },
    created() {
      this.add();
      this.showTag();
      console.log("当前的id", this.$route.params.id)
      if (this.$route.params.id && !isNaN(this.$route.params.id)) {
        informalapi.getinformal(this.$route.params.id).then(resp => {
          this.informal = resp.data.item;
          this.imurl = resp.data.item.cover;
          console.log("当前的信息", resp);
        })
      }
    },
    add(){
      this.tinymceFlag++;
    },
    methods: {
      showTag() {
        tagapi.getTag().then(resp => {
          this.tagInfo = resp.data.item;
        })
      },
      save() {
        //如果当前要添加的数据id不为空那么就是修改
        if (this.$route.params.id && !isNaN(this.$route.params.id)) {
          informalapi.updateinformal(this.informal).then(resp => {
            this.$message({
              type: "success",
              message: "修改成功",
            })
          })
        } else {
          //添加操作
          informalapi.saveinformal(this.informal).then(resp => {
            this.$message({
              type: "success",
              message: "添加成功"
            })
            this.$router.push({
              path: "/yzxinformal/list"
            })
          })
        }
      },
      handleAvatarSuccess(res, file) {
        //显示图片
        this.imurl = URL.createObjectURL(file.raw);
        this.informal.cover = res.data.url;
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
      }
    },
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
