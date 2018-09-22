function testClick1() {
    window.WebViewJavascriptBridge.callHandler(
        'submitFromWeb'
        , {'param': '中文测试'}
        , function(responseData) {
            document.getElementById("show").innerHTML = "send get responseData from java, data = " + responseData
        }
    );
}
