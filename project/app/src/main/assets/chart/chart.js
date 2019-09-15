window.onload=function(){
    // 绘制图表。
    echarts.init(document.getElementById('main')).setOption({
        series: {
            type: 'pie',
            data: [
                {name: 'A', value: 1212},
                {name: 'B', value: 2323},
                {name: 'C', value: 1919}
            ]
        }
    });
    
};