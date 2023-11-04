<template>
	<div style="width: 100%; display: flex; flex-direction: row">
		<!--  left	-->
		<div style="width: 75%">
			<!--	Num	-->
			<div style="display: flex; justify-content: space-around">
				<div class="card" style="display: flex; justify-content: center" v-for="item in numsData" :key="numsData">
					<div style="display: flex">
						<div class="harf-transparent" :style="'margin-right: 30px; display: flex; align-items: center; justify-content: center; background-color: ' + item.bgColor">
							<div :class="item.icon + ' fs-36'" :style="'color: ' + item.color"></div>
						</div>
						<div style="display: flex; flex-direction: column; justify-content: space-around">
							<div class="fs-14 color-gray">{{ item.title }}</div>
							<div class="fs-24 color-black fw">{{ item.num }}</div>
						</div>
					</div>
				</div>
			</div>
			<!--	Article	-->
			<div style="display: flex; justify-content: space-around">
				<div class="card" style="flex: 2; padding: 25px 58px; display: flex; justify-content: space-around">
					<div style="display: flex" v-for="item in articleData" :key="articleData">
						<div class="harf-transparent" :style="'margin-right: 30px; display: flex; align-items: center; justify-content: center; background-color: ' + item.bgColor">
							<div :class="item.icon + ' fs-36'" :style="'color: ' + item.color"></div>
						</div>
						<div style="display: flex; flex-direction: column; justify-content: space-around">
							<div class="fs-14 color-gray">{{ item.title }}</div>
							<div class="fs-24 color-black fw">{{ item.num }}</div>
						</div>
					</div>
				</div>
				<div class="card" style="flex: 1">
					<div style="display:flex; flex-direction: column">
						<div class="color-gray" style="margin-bottom: 10px">文章数据</div>
						<div class="data">
							<i class="el-icon-star-on" style="margin-left: 0; color: #42b983"></i><i>5</i><i class="el-icon-s-comment" style="color: #efa078"></i><i>9</i>
						</div>
					</div>
				</div>
				<div class="card" style="flex: 1">
					<div style="display:flex; flex-direction: column">
						<div class="color-gray" style="margin-bottom: 10px">动态数据</div>
						<div class="data">
							<i class="el-icon-star-on" style="margin-left: 0; color: #42b983"></i><i>5</i><i class="el-icon-s-comment" style="color: #efa078"></i><i>9</i>
						</div>
					</div>
				</div>
			</div>
			<div class="card">
				<div style="padding-bottom: 20px" class="fs-16 fw">今日数据概览</div>
				<div style="display: flex; justify-content: space-around">
					<div class="card-gray" v-for="item in comparationData" :key="comparationData">
						<div class="color-gray fs-14" style="margin-bottom: 10px">{{ item.title }}</div>
						<div style="display: flex; justify-content: space-between">
							<span class="fw fs-18">{{ item.today }}</span>
							<span class="color-gray fs-12">昨天：{{ item.yesterday }}</span>
						</div>
					</div>
				</div>
			</div>
			<div ref="mapChartsDom" class="card" style="width: 100%; height: 500px"></div>
		</div>
		<!-- right	-->
		<div style="width: 25%; margin-left: 20px; height: 100%">
			<div class="card" style="width: 90%;">
				<div style="padding:20px; background-color: rgba(160,228,255,0.47); border-radius: 5px;text-align: center; font-size: 18px; color: #2fd5dc">已运行<strong style="font-weight: bold; padding: 0 5px; font-size: 40px">6</strong>天</div>
			</div>
			<div id="main" class="card" style="width: 90%; height: 600px"></div>
		</div>
	</div>

</template>

<script>
import * as echarts from "echarts"
import getMap from '../utils/getMap'
export default {
	name: "Home",
	data(){
		return {
			comparationData: [
				{
					title: "今日新增用户(人)",
					yesterday: 2,
					today: '-'
				},
				{
					title: "今日访问人数(人)",
					yesterday: '-',
					today: 5
				},
				{
					title: "今日浏览量(人次)",
					yesterday: 3,
					today: 6
				},
				{
					title: "今日获赞(个)",
					yesterday: 9,
					today: 1
				}
			],
			articleData: [
				{
					icon: "el-icon-edit",
					color: "#047bff",
					bgColor: "rgba(79,157,232,0.5)",
					title: "文章数量",
					num: 25
				},
				{
					icon: "el-icon-s-opportunity",
					color: "#00e1ff",
					bgColor: "rgba(52,217,241,0.5)",
					title: "原创文章",
					num: 236
				}
			],
			numsData: [
				{
					icon: "el-icon-s-home",
					color: "#43049a",
					bgColor: "rgba(102,14,232,0.3)",
					title: "总用户数量",
					num: 25
				},
				{
					icon: "el-icon-s-open",
					color: "#ff0000",
					bgColor: "rgba(250,91,121,0.3)",
					title: "总浏览次数",
					num: 236
				},
				{
					icon: "el-icon-star-on",
					color: "#12a600",
					bgColor: "rgba(63,215,74,0.5)",
					title: "总点赞次数",
					num: 35
				},
				{
					icon: "el-icon-s-comment",
					color: "#bd6000",
					bgColor: "rgba(225,149,100,0.5)",
					title: "总评论条数",
					num: 12
				}
			]
		}
	},
	// 页面元素渲染后触发
	mounted() {

		// 初始化统计图对象
		let myMapChart = echarts.init(this.$refs["mapChartsDom"]);
		// 显示 loading 动画
		myMapChart.showLoading();
		// 再得到数据的基础上，进行地图绘制
		getMap.then(res => {
			// 得到结果后，关闭动画
			myMapChart.hideLoading();
			// 注册地图(数据放在axios返回对象的data中哦)
			echarts.registerMap('China', res.data);
			let mapOption = {
				visualMap: {
					min: 1,
					max: 150,
					realtime: false,
					calculable: true,
					inRange: {
						color: ['#1E9600', '#FFF200', '#FF0000']
					}
				},
				title:{
					text:'访客地区分布'
				},
				tooltip: {},// 配置提示框，有这个配置项即可
				series: [
					{
						name: '访客数量',
						type: 'map',
						map: 'China',// 这个是上面注册时的名字哦，registerMap（'这个名字保持一致'）
						label: {
							show: false
						},
						data: [
							{ name: "北京市", value: 177 },
							{ name: "天津省", value: 42 },
							{ name: "河北省", value: 102 },
							{ name: "山西省", value: 81 },
							{ name: "内蒙古自治区", value: 47 },
							{ name: "辽宁省", value: 67 },
							{ name: "吉林省", value: 82 },
							{ name: "黑龙江省", value: 66 },
							{ name: "上海市", value: 0 },
							{ name: "江苏省", value: 92 },
							{ name: "浙江省", value: 114 },
							{ name: "安徽省", value: 109 },
							{ name: "福建省", value: 116 },
							{ name: "江西省", value: 91 },
							{ name: "山东省", value: 119 },
							{ name: "河南省", value: 137 },
							{ name: "湖北省", value: 116 },
							{ name: "湖南省", value: 114 },
							{ name: "重庆市", value: 91 },
							{ name: "四川省", value: 125 },
							{ name: "贵州省", value: 62 },
							{ name: "云南省", value: 83 },
							{ name: "西藏自治区", value: 9 },
							{ name: "陕西省", value: 80 },
							{ name: "甘肃省", value: 56 },
							{ name: "青海省", value: 10 },
							{ name: "宁夏回族自治区", value: 18 },
							{ name: "新疆维吾尔自治区", value: 67 },
							{ name: "广东省", value: 123 },
							{ name: "广西省", value: 59 },
							{ name: "海南省", value: 14 },
							{ name: "台湾省", value: 14 }
						]
					}
				]
			};
			myMapChart.setOption(mapOption);
		})


		let chartDom = document.getElementById('main');
		let myChart = echarts.init(chartDom);
		let option;

		option = {
			tooltip: {
				trigger: 'item'
			},
			legend: {
				top: '50',
				left: 'center'
			},
			title: {
				text: "访客设备",
				padding: 20
			},
			series: [
				{
					name: '访客设备',
					type: 'pie',
					radius: ['40%', '70%'],
					avoidLabelOverlap: false,
					itemStyle: {
						borderRadius: 10,
						borderColor: '#fff',
						borderWidth: 2
					},
					label: {
						show: false,
						position: 'center'
					},
					emphasis: {
						label: {
							show: true,
							fontSize: 40,
							fontWeight: 'bold'
						}
					},
					labelLine: {
						show: false
					},
					data: [
						{ value: 50, name: 'Windows' },
						{ value: 735, name: 'Mac' },
						{ value: 580, name: 'Linux' },
						{ value: 484, name: 'Browser' },
						{ value: 300, name: 'Android' },
						{ value: 300, name: 'Android' }
					]
				}
			]
		};

		this.request.get("/echarts/test").then(res => {
			// 填充数据
			for(let i = 0; i < res.data.x.length; i++){
				option.series[0].data[i].name = res.data.x[i];
				option.series[0].data[i].value = res.data.y[i];
			}
			myChart.setOption(option);
		})
	}
}

</script>

<style>

	.el-main{
		padding: 5px;
	}
	.card{
		padding: 25px;
		background-color: #fff;
		border-radius: 6px;
		margin: 10px;
		width: 100%;
	}
	.card-gray{
		padding: 25px;
		background-color: #eee;
		border-radius: 6px;
		margin: 10px;
		width: 100%;
	}
	.harf-transparent{
		border-radius: 10px;
		opacity: 0.2;
		width: 60px;
		height: 60px;
		text-align: center;
	}
	.fs-36{
		font-size: 36px;
	}
	.fs-24{
		font-size: 24px;
	}
	.fs-20{
		font-size: 20px;
	}
	.fs-16{
		font-size: 16px;
	}
	.fs-14{
		font-size: 14px;
	}
	.fs-12{
		font-size: 12px;
	}
	.fw{
		font-weight: bold;
	}
	body{
		background-color: #F5F7F9;
	}

	.color-gray{
		color: rgb(128, 128, 128);
	}
	.color-black{
		color: black;
	}
	.data>i{
		margin: 0 10px;
	}
</style>
