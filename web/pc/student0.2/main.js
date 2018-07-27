import app from './app.ui'
import '#/static/css/box.less'

let options = {
  app: app
}

ui.extend({
  pageHeight(val){
    return  ui.DEFAULT_CONTENT_HEIGHT + val
  },
})

ui.start(options)