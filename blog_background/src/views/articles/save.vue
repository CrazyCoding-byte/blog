<template>
  <div>
    <!-- 表单 -->
    <el-form ref="form" :model="articles" label-width="80px">
      <el-form-item label="博文标题" style="width: 300px">
        <el-input v-model="articles.articleTitle"></el-input>
      </el-form-item>
      <el-form-item label="博文分类">
        <el-select
          v-model="articles.subjectId"
          placeholder="请选择"
        >
          <el-option
            v-for="one in subject"
            :key="one.sortId"
            :label="one.sortName"
            :value="one.sortId"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="标签">
        <el-select
          v-model="articles.tagId"
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
      <el-form-item label="浏览量">
        <el-input-number
          v-model="articles.articleViews"
          controls-position="right"
          :min="0"
          :max="1000000"
        ></el-input-number>
      </el-form-item>
      <el-form-item label="博文内容">
        <tinymce
          :key="tinymceFlag"
          :height="300"
          v-model="articles.articleContent"
        ></tinymce>
      </el-form-item>
      <el-form-item label="评论数">
        <el-input-number
          type="number"
          v-model="articles.articleCommentCount"
          controls-position="right"
          :min="0"
          :max="100000"
        ></el-input-number>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="save">提交博文</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import tinymce from "@/components/Tinymce";
import * as articlesapi from '@/api/articles'
  import * as tagapi from '@/api/tag'
  export default {
    name: "save",
    data() {
      return {
        articles: {},
        imurl: "",
        tinymceFlag:1,
        subject: [],
        tagInfo:[],
      }
    },
    created() {
      articlesapi.subjectlist().then(resp=>{
        this.subject=resp.data.item;
      })
      this.add();
      this.showTag();
      if (this.$route.params.id && !isNaN(this.$route.params.id)) {
        articlesapi.getArticles(this.$route.params.id).then(resp => {
          console.log("当前的数据",resp)
          this.articles = resp.data.item;
          this.imurl=resp.data.item.articleCover;
        })
      }
    },
    methods: {
      add(){
        this.tinymceFlag++;
      },
      showTag(){
        tagapi.getTag().then(resp=>{
          this.tagInfo=resp.data.item;
        })
      },
      save() {
        if (this.$route.params.id && !isNaN(this.$route.params.id)) {
          articlesapi.updateArticles(this.articles).then(resp => {
              if(resp.code==200){
                this.$message({
                  type: "success",
                  message: "修改成功"
                })
                this.$router.push({
                  path:"/articles/list"
                })
              }else{
                this.$message({
                  type: "error",
                  message: "修改失败"
                })
              }
          })
        } else {
          articlesapi.saveArticles(this.articles).then(response => {
            console.log("添加之后的信息", response)
            if(response.code==200){
              this.$message({
                type: "success",
                message: "添加成功"
              })
              this.$router.push({
                path:"/articles/list"
              })
            }else{
              this.$message({
                type: "error",
                message: "添加失败"
              })
            }
          })
        }
      },
      handleAvatarSuccess(res, file) {
        //显示图片
        this.imurl = URL.createObjectURL(file.raw);
        this.articles.articleCover = res.data.url;
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
    components: {
      tinymce
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
