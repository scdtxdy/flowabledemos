<template>
  <div class="dashboard-container">
    <!--<div class="filter-container">
      <el-button type="primary"
                 @click="saveDiagram">保存</el-button>
    </div>
    <el-row  :gutter="20" style="height: 700px;">
      <el-col :span="20" style="border: 1px solid #f5f2f2;">
        <div style="height: 700px" class="grid-content containers">
          <div ref="canvas"
               class="canvas"
          />
        </div>
      </el-col>
      <el-col :span="4" style="border: 1px solid #f5f2f2;">
        <div style="height: 700px; overflow: auto;"
             id='js-properties-panel'
             class="panel"
        />
      </el-col>
    </el-row>
  </div>-->
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import BpmnModeler from 'bpmn-js/lib/Modeler'; // 引入 bpmn-js
import customTranslate from '../../commons/customTranslate/customTranslate';
import propertiesPanelModule from '../../components/VueBpmn/bpmn-js-properties-panel/lib'; // 自定义的属性面板
// 引入flowable的节点文件
import flowableModdle from '../../components/VueBpmn/bpmn-js-properties-panel/static/flowModel/flowable.json';
// 而这个引入的是右侧属性栏里的内容
import propertiesProviderModule from '../../components/VueBpmn/bpmn-js-properties-panel/lib/provider/flowable';

import request from '@/utils/request';

import '../../components/VueBpmn/bpmn-js-properties-panel/styles/index.css' // 右边工具栏样式


export default {
  name: 'Dashboard',
  data() {
    return {
      element: {},
      bpmnModeler: null,
      form: {
        id: 'id',
        name: 'name'
      }
    };
  },
  computed: {
    ...mapGetters([
      'name'
    ])
  },
  mounted() {
    // const customTranslateModule = {
    //   translate: ['value', customTranslate]
    // };
    // const canvas = this.$refs.canvas;
    // // 生成实例
    // this.bpmnModeler = new BpmnModeler({
    //   additionalModules: [
    //     customTranslateModule,
    //     // 左边工具栏以及节点
    //     propertiesProviderModule,
    //     // 右边的工具栏
    //     propertiesPanelModule
    //   ],
    //   // 添加控制板
    //   container: canvas,
    //   height: '100%',
    //   moddleExtensions: {
    //     flowable: flowableModdle
    //   },
    //   propertiesPanel: {
    //           parent: '#js-properties-panel'
    //         },
    //   width: '100%'
    // });
    // this.createNewDiagram();// 新增流程定义
    //
    // this.handleModeler();
  },
  methods: {
    createNewDiagram() {
      const bpmnXmlStr = `
    <?xml version="1.0" encoding="UTF-8"?>
      <bpmn2:definitions xmlns:bpmn2="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" id="sample-diagram" targetNamespace="http://bpmn.io/schema/bpmn" xsi:schemaLocation="http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd">
        <bpmn2:process id="process1567044459787" name="流程1567044459787">
          <bpmn2:documentation>描述</bpmn2:documentation>
          <bpmn2:startEvent id="StartEvent_1" name="开始"/>
        </bpmn2:process>
        <bpmndi:BPMNDiagram id="BPMNDiagram_1">
          <bpmndi:BPMNPlane id="BPMNPlane_1" bpmnElement="process1567044459787">
            <bpmndi:BPMNShape id="_BPMNShape_StartEvent_2" bpmnElement="StartEvent_1">
              <dc:Bounds x="184" y="64" width="36" height="36"/>
              <bpmndi:BPMNLabel>
                <dc:Bounds x="191" y="40" width="22" height="14"/>
              </bpmndi:BPMNLabel>
            </bpmndi:BPMNShape>
          </bpmndi:BPMNPlane>
        </bpmndi:BPMNDiagram>
      <processType id="test"/></bpmn2:definitions>
    `;
      // 将字符串转换成图显示出来
      this.bpmnModeler.importXML(bpmnXmlStr, (err) => {
        if (err) {
          console.error(err);
        }
      });
    },
    async saveDiagram(done) {
      // 把传入的done再传给bpmn原型的saveXML函数调用
      const json_xml = await this.bpmnModeler.saveXML({ format: true });
      const params = {
        processId: this.form.id,
        xml: json_xml.xml
      };

      console.log('scd', params);

      request({
        url: 'vue-admin-template/rest/model/addModel',
        method: 'post',
        data: params
      }).then(res => {
        console.log('scd', res);
      }).catch(res => {
        console.log('catch', res);
      });
    },
    handleModeler() {
      // 监听节点选择变化
      this.bpmnModeler.on('selection.changed', e => {
        const element = e.newSelection[0];
        this.element = element;
        if (!element) return;
        // this.form.id = element.businessObject.id
        // this.form.name = element.businessObject.name
        this.form = {
          ...element.businessObject,
          ...element.businessObject.$attrs
        };
        console.log(this.form);
        // if (element.businessObject.$type === "bpmn:UserTask") {
        //   this.dialogFormVisible = true;
        // }

        // this.$message({
        //   message: "当前内容：" + element.businessObject.name,
        //   type: "success"
        // });
        // this.form = {
        //   ...element.businessObject,
        //   ...element.businessObject.$attrs
        // };
        // if (this.form.userType === "candidateUsers") {
        //   this.form["candidateUsers"] =
        //     this.form["candidateUsers"].split(",") || [];
        // }
        // console.log(this.form)
      });
    }
  }
};
</script>

<style lang="less" scoped>
  /*左边工具栏以及编辑节点的样式*/
  @import '~bpmn-js/dist/assets/diagram-js.css';
  @import '~bpmn-js/dist/assets/bpmn-font/css/bpmn.css';
  @import '~bpmn-js/dist/assets/bpmn-font/css/bpmn-codes.css';
  @import '~bpmn-js/dist/assets/bpmn-font/css/bpmn-embedded.css';

  .containers {
    min-height: 700px;
    .canvas {
      width: 100%;
      height: 700px;
    }

    .bjs-powered-by {
      display: none;
    }
  }

</style>
