//在项目的根目录创建utils文件夹，再创建一个tool.js文件
//我们可以对setItem事件进行重写，当使用setItem的时候，触发window.dispatchEvent派发事件
function dispatchEventStroage() {
    const signSetItem = localStorage.setItem
    localStorage.setItem = function(key, val) {
        let setEvent = new Event('setItemEvent')
        setEvent.key = key
        setEvent.newValue = val
        window.dispatchEvent(setEvent)
        signSetItem.apply(this, arguments)
    }
}

export default dispatchEventStroage;

