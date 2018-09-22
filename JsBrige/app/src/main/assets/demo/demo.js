function testClick1() {
    window.WebViewJavascriptBridge.callHandler(
        'submitFromWeb'
        , {'param': '中文测试'}
        , function(responseData) {
            document.getElementById("show").innerHTML = "js调用java = " + responseData
        }
    );
}
