<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>抽奖活动</title>
    <link href="/css/header.css" type="text/css" rel="stylesheet">
    <link href="/css/auction.css" type="text/css" rel="stylesheet">
</head>
<body style="background-color: #f3f3f3;">
<header>
    <a class="left" onclick="window.history.back();">
        <img src="/images/back.png"/>
    </a>

    <p>拍卖活动一</p>
</header>
<div id="banner">
    <img src="${auction.good_photo!}"/>
</div>
<div class="auction">
    <div class="msg-auct">
        <img src="/images/auction_on.png"/>

        <div class="title">
            <h3>${auction.title!}</h3>

            <p>${auction.good_name!}<br>
            <#if auctionRecords?exists && auctionRecords?size!=0>
                <#list auctionRecords as ar>
                    <#if ar_index==0>
                        当前价格:${ar.price!}&nbsp;&nbsp;加价幅度:${auction.setp_amount!}元
                    </#if>
                </#list>
            <#else>
                起拍价格:${auction.amount!}&nbsp;&nbsp;加价幅度:${auction.setp_amount!}元
            </#if>
            </p>
        </div>
    </div>
    <div class="msg-time">
        <img src="/images/clock.png"/>
    <#if seconds gt 0>
        剩余时间<span id="day_show">0</span>天
        <span id="hour_show">0</span>时
        <span id="minute_show">0</span>分
        <span id="second_show">0</span>秒
    <#else>
        拍卖结束!
    </#if>

    </div>
</div>
<div class="auct-name">
    <div class="msgname">
    <#--<img src="/images/auct-header.png"/>-->
    <#--<p>拍卖者信息</p>-->
    <#if auctionRecords?exists && auctionRecords?size!=0>
        <#list auctionRecords as ar>
            <p>${ar.phone!}|出价:${ar.price!}</p>
        </#list>
    <#else>
        <p>当前无人竞拍！</p>
    </#if>
    </div>
</div>
<div id="bg-auct">
    <div class="auct-success">
        <div class="succ-top">
            <p class="title-succ">拍卖成功!</p>

            <p id="close">x</p>
        </div>
        <div class="succ-main">
            <div class="msg-succ">
                <img src="/images/auct.png"/>

                <div class="msg-r">
                    <h4>${auction.good_name!}</h4>

                    <p>数量 x1</p>

                    <p>成交价格 389元</p>
                </div>
            </div>
        </div>
    </div>
</div>
<#--<div class="footer">-->
<#--<div class="footer-left"><p>出价:<input type="text" name="price" id="price" value="0"></p></div>-->
<#--<div class="footer-right" id="auction"><p>拍下来</p></div>-->
<#--</div>-->
<div class="footer">
<#if seconds gt 0>
    <#if isSuccess>
        <div class="footer">
            <div class="footer-left"><p>出价:<input type="text" name="price" id="price" value="0"></p></div>
            <div class="footer-right" id="auction"><p>拍下来</p></div>
        </div>
    </#if>
<#else>
    <div id="deposit">报名交保证金</div>
</#if>
</div>

<script src="/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<script type="text/javascript">
    var auct = document.getElementById("auction");
    var floatbg = document.getElementById("bg-auct");
    var closebtn = document.getElementById("close");
    var deposit = document.getElementById("deposit");
    auct.onclick = function () {
//        floatbg.style.display = "block";
        var price = $("#price").val();
        $.ajax({
            url: "bid.action?price=" + price + "&aid=${auction.id}",
            type: "POST",
            success: function (result) {
                var r = jQuery.parseJSON(result);
                if (r.code == 1) {
                    alert(r.msg);
                } else {
                    alert(r.msg);
                }
            }
        });
    }
    deposit.onclick = function () {
        window.location.href = "auction_margin.action";
    }
    closebtn.onclick = function () {
        floatbg.style.display = "none";
    }

    function timer(intDiff) {
        window.setInterval(function () {
            var day = 0,
                    hour = 0,
                    minute = 0,
                    second = 0;//时间默认值
            if (intDiff > 0) {
                day = Math.floor(intDiff / (60 * 60 * 24));
                hour = Math.floor(intDiff / (60 * 60)) - (day * 24);
                minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
                second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
            }
            if (minute <= 9) minute = '0' + minute;
            if (second <= 9) second = '0' + second;
            $('#day_show').html(day);
            $('#hour_show').html(hour);
            $('#minute_show').html(minute);
            $('#second_show').html(second);
            intDiff--;
        }, 1000);
    }
    $(function () {
        var seconds =${seconds?c};
        //var intDiff = parseInt(${seconds});//倒计时总秒数量
        timer(seconds);
    });


</script>
</body>
</html>