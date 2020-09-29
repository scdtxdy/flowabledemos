<template>
  <!--<div class="dashboard-container">
    <div class="dashboard-text">name: {{ name }}</div>
  </div>-->
  <div>
    <div class="containers">
      <div
        ref="canvas"
        class="canvas"
      />
    </div>
    <div ref="propertyPanel" class="property-panel">
      <el-form :inline="true" :model="form" label-width="100px">
        <el-form-item label="节点ID">
          <el-input v-model="form.id" disabled />
        </el-form-item>
        <el-form-item label="节点名称">
          <el-input v-model="form.name" />
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import BpmnModeler from 'bpmn-js/lib/Modeler' // 引入 bpmn-js
import customTranslate from '../../commons/customTranslate/customTranslate'

export default {
  name: 'Dashboard',
  data() {
    return {
      bpmnModeler: null,
      form: {
        id: 'id',
        name: 'name'
      }
    }
  },
  computed: {
    ...mapGetters([
      'name'
    ])
  },
  mounted() {
    var customTranslateModule = {
      translate: ['value', customTranslate]
    }
    const canvas = this.$refs.canvas
    // 生成实例
    this.bpmnModeler = new BpmnModeler({
      container: canvas,
      additionalModules: [
        customTranslateModule
      ]
    })
    this.createNewDiagram() // 新增流程定义
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
      `
      // 将字符串转换成图显示出来
      this.bpmnModeler.importXML(bpmnXmlStr, (err) => {
        if (err) {
          console.error(err)
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
  /*.dashboard {*/
  /*  &-container {*/
  /*    margin: 30px;*/
  /*  }*/

  /*  &-text {*/
  /*    font-size: 30px;*/
  /*    line-height: 46px;*/
  /*  }*/
  /*}*/

  /*左边工具栏以及编辑节点的样式*/
  @import '~bpmn-js/dist/assets/diagram-js.css';
  @import '~bpmn-js/dist/assets/bpmn-font/css/bpmn.css';
  @import '~bpmn-js/dist/assets/bpmn-font/css/bpmn-codes.css';
  @import '~bpmn-js/dist/assets/bpmn-font/css/bpmn-embedded.css';
  .containers{
    position: absolute;
    background-color: #ffffff;
    width: 100%;
    height: 100%;
    .canvas{
      width: 100%;
      height: 100%;
    }
    .bjs-powered-by {
      display: none;
    }
  }
</style>
