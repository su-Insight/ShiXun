<template>
  <div style="min-height: 100vh">
    <el-container style="min-height: 100vh; border: 1px solid #eee">
      <el-aside width=" sideWidth + 'px'" style="background-color: rgb(238, 241, 246)">
        <el-menu :default-openeds="['1', '3']" style="min-height: 100%; overflow-x: hidden"
                  :collapse="isCollapse"
                  background-color="rgb(48, 65, 86)"
                  text-color="#fff"
                  active-text-color="#ffd04b"
                  :collapse-transition="false"
                  class="el-menu-vertical-demo">
          <div style="height: 60px; line-height: 60px; text-align: center">
            <img src="../assets/logo.png" style="width: 20px">
            <b style="color: white; padding: 0 10px" v-show="logoTextShow">后台管理系统</b>
          </div>
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-message"></i>
              <span slot="title">导航一</span>
            </template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="1-1">选项1</el-menu-item>
              <el-menu-item index="1-2">选项2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="1-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="1-4">
              <template slot="title">选项4</template>
              <el-menu-item index="1-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span slot="title">导航二</span>
            </template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="2-1">选项1</el-menu-item>
              <el-menu-item index="2-2">选项2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="2-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="2-4">
              <template slot="title">选项4</template>
              <el-menu-item index="2-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-setting"></i>
              <span slot="title">导航三</span>
            </template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="3-1">选项1</el-menu-item>
              <el-menu-item index="3-2">选项2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="3-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="3-4">
              <template slot="title">选项4</template>
              <el-menu-item index="3-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
        </el-menu>
      </el-aside>

      <el-container>
        <el-header style="font-size: 12px; background-color: #fff; border-bottom: #ccc 1px solid; line-height: 60px; display: flex">
          <div style="flex: 1; font-size: 18px">
            <button :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></button>
          </div>
          <el-dropdown style="width: 70px; cursor: pointer">
            <span>王小虎</span>
            <i class="el-icon-arrow-down" style="margin-left: 5px"></i>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>个人信息</el-dropdown-item>
              <el-dropdown-item>退出</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>

        </el-header>

        <el-main>

          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item><a href="/">活动管理</a></el-breadcrumb-item>
            <el-breadcrumb-item>活动列表</el-breadcrumb-item>
            <el-breadcrumb-item>活动详情</el-breadcrumb-item>
          </el-breadcrumb>

          <!-- 搜索 -->
          <div style="margin: 10px 0">
	          <!-- 搜索下拉菜单 -->
	          <el-select v-model="category" style="width: 75px;margin-right: 10px">
		          <el-option
				          v-for="item in options"
				          :key="item.value"
				          :label="item.label"
				          :value="item.value">
		          </el-option>
	          </el-select>
            <el-input style="width: 250px" suffix-icon="el-icon-search" placeholder="请输入搜索内容" v-model="content" @keyup.enter.native="loadPage"></el-input>
            <el-button style="margin-left: 10px" type="primary" @click="loadPage">搜素</el-button>
            <el-button type="warning" @click="reset">重置</el-button>

	          <el-button style="float: right;margin: 0 5px" type="primary">导出<i class="el-icon-top"></i></el-button>
	          <el-button style="float: right;margin: 0 5px" type="primary">导入<i class="el-icon-bottom"></i></el-button>
	          <el-popconfirm
			          confirm-button-text='确认'
			          cancel-button-text='取消'
			          icon="el-icon-info"
			          icon-color="red"
			          title="确认删除用户信息？"
			          @confirm="delBatch"
	          >
		          <el-button style="float: right;margin: 0 5px" type="danger" slot="reference">批量删除<i class="el-icon-remove-outline"></i></el-button>
	          </el-popconfirm>

	          <el-button style="float: right;margin: 0 5px" type="primary" @click="addHandle">新增<i class="el-icon-circle-plus-outline"></i></el-button>

          </div>

	        <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
		        <el-form label-width="80px" size="small">
			        <el-form-item label="用户名">
				        <el-input v-model="form.username" autocomplete="off" width="50px"></el-input>
			        </el-form-item>
			        <el-form-item label="昵称">
				        <el-input v-model="form.nickname" autocomplete="off"></el-input>
			        </el-form-item>
			        <el-form-item label="邮箱">
				        <el-input v-model="form.email" autocomplete="off"></el-input>
			        </el-form-item>
			        <el-form-item label="电话">
				        <el-input v-model="form.phone" autocomplete="off"></el-input>
			        </el-form-item>
			        <el-form-item label="地址">
				        <el-input v-model="form.address" autocomplete="off"></el-input>
			        </el-form-item>
		        </el-form>
		        <div slot="footer" class="dialog-footer">
			        <el-button @click="cancle">取 消</el-button>
			        <el-button type="primary" @click="save">确 定</el-button>
		        </div>
	        </el-dialog>


          <el-table :data="tableData"
                    @selection-change="handleSelectionChange"
                    border stripe>

	          <el-table-column
			          type="selection"
			          width="55">
	          </el-table-column>
	          <el-table-column prop="id" label="ID" width="140">
	          </el-table-column>
            <el-table-column prop="username" label="用户名" width="140">
            </el-table-column>
            <el-table-column prop="nickname" label="昵称" width="120">
            </el-table-column>
            <el-table-column prop="email" label="邮箱">
            </el-table-column>
            <el-table-column prop="phone" label="电话">
            </el-table-column>
            <el-table-column prop="address" label="地址">
            </el-table-column>
            <el-table-column label="操作" align="center">
              <template slot-scope="scope">
                <el-button type="success" @click="editHandle(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
	              <el-popconfirm
			              confirm-button-text='确认'
			              cancel-button-text='取消'
			              icon="el-icon-info"
			              icon-color="red"
			              title="确认删除用户信息？"
			              @confirm="delHandle(scope.row.id)"
	              >
		              <el-button type="danger" slot="reference">删除<i class="el-icon-remove-outline"></i></el-button>
	              </el-popconfirm>
              </template>
            </el-table-column>
          </el-table>
          <!-- 分页 -->
          <div style="padding: 10px 0">
            <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="pageNum"
                :page-sizes="[5, 10, 20, 50]"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>


<style>
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
}

.headerBg{
  background: #eee!important;
}
</style>
<script>
// @ is an alias to /src


import request from "@/utils/request";

export default {
  name: 'HomeVue',
  data() {
    return {
      // global
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true,
	    dialogFormVisible: false,
	    form: {},
	    multipleSelection: [],

      // page
      content: "",
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,

      // select
      options: [{
        value: 'all',
        label: '全部'
      }, {
        value: 'username',
        label: '姓名'
      }, {
        value: 'nickname',
        label: '昵称'
      }, {
        value: 'email',
        label: '邮箱'
      }, {
        value: 'phone',
        label: '手机号'
      }, {
        value: 'address',
        label: '地址'
      }],
      category: 'all'
    }
  },
  created() {
    this.loadPage()
  },
  methods: {
		cancle(){
			this.dialogFormVisible = false
			this.loadPage()
		},
	  delHandle(id){
		  request.delete("/user/" + id).then(res => {
			  if (res){
				  this.$message.success("删除成功！")
				  this.loadPage()
			  }else {
				  this.$message.error("删除失败！")
			  }
		  })
	  },
		save(){
			this.dialogFormVisible = false
			request.post("/user", this.form).then(res => {
				if (res){
					this.$message.success("保存成功！")
					this.loadPage()
				}else {
					this.$message.error("保存失败！")
				}
			})
		},
		addHandle(){
			this.dialogFormVisible = true
			this.form = {}
		},
	  editHandle(row){
			this.form = row
		  this.dialogFormVisible = true
	  },
	  delBatch(){
			let ids = this.multipleSelection.map(v => v.id)
		  request.post("/user/del/batch", ids).then(res => {
			  if (res){
				  this.$message.success("删除成功！")
				  this.loadPage()
			  }else {
				  this.$message.error("删除失败！")
			  }
		  })
	  },

    collapse(){
      this.isCollapse = !this.isCollapse
      if(this.isCollapse){
        this.sideWidth = 64
        this.collapseBtnClass = 'el-icon-s-unfold'
        this.logoTextShow = false
      }else{
        this.sideWidth = 200
        this.collapseBtnClass = 'el-icon-s-fold'
        this.logoTextShow = true
      }
    },
    reset(){
      this.content = ""
      this.loadPage()
    },
    loadPage(){
      // 请求分页查询数据
      request.get("/user/page",{
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          content: this.content,
	        category: this.category
        }
    }).then(res => {
        this.tableData = res.records
        this.total = res.total
      })
      // fetch("http://localhost:8088/user/page?pageNum="+this.pageNum+"&pageSize="+this.pageSize+"&content="+this.content).then(res => res.json()).then(res => {
      //   console.log(res)
      //   this.tableData = res.records
      //   this.total = res.total
      // })
    },
    handleSizeChange(pageSize){
      console.log(pageSize)
      this.pageSize = pageSize
      this.loadPage()
    },
    handleCurrentChange(pageNum){
      console.log(pageNum)
      this.pageNum = pageNum
      this.loadPage()
    },
	  handleSelectionChange(val){
		  console.log(val)
		  this.multipleSelection = val
	  }
  }
}
</script>
