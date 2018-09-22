
function connectWebViewJavascriptBridge(callback) {
    if (window.WebViewJavascriptBridge) {
        callback(WebViewJavascriptBridge)
    } else {
        document.addEventListener(
            'WebViewJavascriptBridgeReady'
            , function() {
                callback(WebViewJavascriptBridge)
            },
            false
        );
    }
}

connectWebViewJavascriptBridge(function(bridge) {
        bridge.init(function(message, responseCallback) {
            console.log('JS got a message', message);
            var data = {
                'Javascript Responds': '测试中文!'
            };

            if (responseCallback) {
                console.log('JS responding with', data);
                responseCallback(data);
            }
        });

        bridge.registerHandler("functionInJs", function(data, responseCallback) {
            document.getElementById("show").innerHTML = ("原生调用js传输的数据: = " + data);
            if (responseCallback) {
                var responseData = "Javascript Says Right back aka!";
                responseCallback(responseData);
            }
        });
    })
