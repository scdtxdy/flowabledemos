<template>
  <div v-if="show" class="dashboard-container">
    <vue-bpmn @save="btnSave" :modelData="modelData"/>
  </div>
</template>

<script>
  import { mapGetters } from 'vuex';
  import {getAction, putAction} from '@/api/manage'
  import VueBpmn from "@/components/VueBpmn";
  import {Message} from 'element-ui';


  export default {
    name: 'modelDesigner',
    components: {VueBpmn},
    data() {
      return {
        modelId: undefined,
        modelData: {
          id: undefined,
          editor: undefined
        },
        show: false
      };
    },
    computed: {
      ...mapGetters([
        'name'
      ])
    },
    mounted() {
      if (this.$route.query && this.$route.query.modelId) {
        this.modelId = this.$route.query.modelId
      }
      this.getModelData()
    },
    methods: {
      getModelData() {
        if (!this.modelId) {
          Message.error('modelId is null or undefined')
          return
        }
        getAction('/vue-admin-template/rest/model/getModelsById', {modelId: this.modelId}).then(({data}) => {
          this.modelData.id = data.id
          this.modelData.editor = data.editor
          this.modelData.key = data.key
          this.modelData.name = data.name
          this.modelData.category = data.category
          this.modelData.description = data.description
          this.show = true
        })
      },
      btnSave(modelData){
        putAction('/vue-admin-template/rest/model/saveModelEditor', modelData).then(({msg, data}) => {
          Message.success(msg)
        })
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
