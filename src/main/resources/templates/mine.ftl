<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>个人资料</title>
    <link href="/css/header.css" type="text/css" rel="stylesheet">
    <link href="/css/lottery.css" type="text/css" rel="stylesheet">
    <style>
        .container {
            position: absolute;
            width: 100%;
            height: 93%;
            top: 7%;
            left: 0px;
            background: #f3f3f3;
        }

        .head {
            background: url("/images/mine-bg.png") no-repeat;
            background-size: 100% 100%;
            width: 100%;
            height: 30%;
        }

        .head img {
            position: absolute;
            width: 250px;
            height: 250px;
            left: 36%;
            top: 5%;
        }

        .head p {
            text-align: center;
            font-size: 2.8rem;
            color: #fff;
            padding-top: 36%;
        }

        .mainlist {
            margin-top: 5%;
            width: 100%;
            height: 32%;
            background: #fff;
        }

        .mainlist li {
            height: 9%;
            border-bottom: 2px solid #ccc;
            padding: 4%;
        }

        .mainlist img, .mainlist p {
            float: left;
            font-size: 2.4rem;
        }

        .mainlist img {
            width: 50px;
            height: 50px;
            margin: 0 5%;
        }

        .mainlist a {
            float: right;
            font-size: 3.6rem;
            color: #ccc;
            margin-right: 5rem;
            line-height: 50px;
        }

        .quit {
            width: 40%;
            height: 5%;
            margin-top: 6%;
            margin-left: 30%;
        }

        .quit img {
            width: 100%;
            height: 100%;
        }
    </style>
</head>
<body style="background-color: #f3f3f3;">
<header>
    <a class="left" onclick="window.history.back();">
        <img src="/images/back.png"/>
    </a>

    <p>个人资料</p>
</header>
<div class="container">
    <div class="head">
        <img src="/images/portrait.png"/>

        <p>18674118888</p>
    </div>
    <ul class="mainlist">

        <li onclick="window.location.href='/my/mine_coupon.action'">
            <img src="/images/ticket-b.png"/>

            <p>优惠劵</p>
            <a>&gt;</a>
        </li>


        <li onclick="window.location.href='/my/mine_auction.action'">
            <img src="/images/auction-b.png"/>

            <p>拍卖</p>
            <a>&gt;</a>
        </li>


        <li onclick="window.location.href='/my/mine_overcharged.action'">
            <img src="/images/date-b.png"/>
            <p>砍价</p>
            <a>&gt;</a>
        </li>


        <li onclick="window.location.href='/my/mine_banquet.action'" style="border-bottom:none;">
            <img src="/images/discount-b.png"/>
            <p>约饭</p>
            <a>&gt;</a>
        </li>

    </ul>
    <div class="quit">
        <img src="/images/quit.png"/>
    </div>
</div>
</body>
</html>