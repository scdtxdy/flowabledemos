import Vue from 'vue'
import VueClipboard from 'vue-clipboard2'
import Cookies from 'js-cookie'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'


// lang i18n
// import locale from 'element-ui/lib/locale/lang/en'
import zhLocale from 'element-ui/lib/locale/lang/zh-CN'
import '@/styles/index.scss' // global css
import FormMaking from 'form-making'
import 'form-making/dist/FormMaking.css'

import App from './App'
import store from './store'
import router from './router'

import '@/icons' // icon
import '@/permission' // permission control
import {getDicts,formatDictText} from '@/utils/util'

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online ! ! !
 */
if (process.env.NODE_ENV === 'production') {
  const { mockXHR } = require('../mock')
  mockXHR()
}
let size = Cookies.get('size') || 'mini';

// 如果想要中文版 element-ui，按如下方式声明
Vue.use(ElementUI, {zhLocale,size})
Vue.use(VueClipboard)
Vue.use(FormMaking)


Vue.config.productionTip = false
Vue.prototype.getDicts = getDicts
Vue.prototype.formatDictText = formatDictText

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
