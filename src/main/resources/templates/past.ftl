<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>签到</title>
    <link href="/css/sign.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="/js/jquery.min.js" ></script>
    <script type="text/javascript" src="/js/laytpl.js"></script>
    <script src="/js/d3.v3.min.js" language="JavaScript"></script>
    <script src="/js/liquidFillGauge.js" language="JavaScript"></script>
    <script id="post_list" type="text/html">
        {{# for(var i = 0, len = d.length; i < len; i++){ }}
            <div class="mid">
                <div class="mid-top">
                    <div class="mid-left">
                        <p class="drink">今天喝掉</p>
                        <p class="ml">{{d[i].today_drunk}}ml</p>
                    </div>
                    <div class="mid-right">
                        <p class="drink">当前喝掉</p>
                        <p class="ml">{{d[i].cycle_drunk}}ml</p>
                    </div>
                </div>
                <div class="bottle">
                    <svg id="fillgauge6" width="22%" height="120" onclick="gauge6.update(NewValue());"></svg>
                </div>
            </div>
        <div class="bottom">
            <div class="bottom-box">
                <div class="bottom-d">
                    <p class="drink">今日您已喝掉</p>
                    <p class="mll">{{d[i].today_drunk}}ml</p>
                </div>
                <div class="bottom-d">
                    <p class="drink">朋友已帮你喝掉</p>
                    <p class="mll">{{d[i].today_other_drunk}}ml</p>
                </div>
            </div>
        </div>
        <input type="button" value="我也要干杯" class="chess">
        {{# } }}
    </script>
</head>
<body>
<div class="container" id="msglist">

</div>
<script language="JavaScript">
    var config5 = liquidFillGaugeDefaultSettings();
    config5.circleThickness = 0.1;
    config5.circleColor = "#ED1E37";
    config5.textColor = "#ED1E37";
    config5.waveTextColor = "#FD8F94";
    config5.waveColor = "#FFDDDD";
    config5.textVertPosition = 0.52;
    config5.waveAnimateTime = 2000;
    config5.waveHeight = 0.1;
    config5.waveAnimate = true;
    config5.waveCount = 2;
    config5.waveOffset = 0.5;
    config5.textSize = 1;
    config5.minValue = 0;
    config5.maxValue = 150;//总容量
    config5.displayPercent = false;
    var gauge6 = loadLiquidFillGauge("fillgauge6", 120, config5);

    function NewValue(){//喝完以后给出剩余值
        return 104.02;
    }
    var isload = true;
    $(document).ready(function(){
        if(isload){
            loadMore(); //加载所有瀑布流的数据
        }
    });
    function loadMore(){
        var target = $('#firstDiv').get(0);
        $.ajax({
            type:'GET',
            url:'/pt/info.action',
            timeout : 10000, //超时时间设置，单位毫秒
            data:"ac=index_data",
            dataType:'json',
            async: false,
            success : function(re_json){
                if(re_json != " "){
                    if( re_json.length > 0){
                        appendHtml(re_json);
                    }
                    else {
                        isload = false;
                    }
                }
                else {
                    isload = false;
                }
            },
            complete : function(XMLHttpRequest,status){ //请求完成后最终执行参数
                if(status=='timeout'){//超时,status还有success,error等值的情况
                    //ajaxTimeoutTest.abort();
                    alert("超时");
                }
                if(status=="parsererror"){
                    isload = false;
                    console.log("pasererror");
                }
            }
        });
    }
    function appendHtml(json){
        var gettpl = document.getElementById('post_list').innerHTML;
        laytpl(gettpl).render(json, function(html){
            $("#msglist").append(html);
        });
    }
</script>
</body>
</html>