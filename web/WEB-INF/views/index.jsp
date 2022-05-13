<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>날씨조회</title>

    <!--JQuery / JS & CSS 파일 연결-->
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <link rel="stylesheet" href="/static/style.css"/>
    <script src="/static/weather.js"></script>

    <!--toast grid-->
    <!--Excel 로 내보내기-->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.17.1/xlsx.full.min.js"></script>

    <!--페이징-->
    <link rel="stylesheet" href="https://uicdn.toast.com/tui.pagination/latest/tui-pagination.css"/>
    <script src="https://uicdn.toast.com/tui.pagination/latest/tui-pagination.js"></script>

    <!--grid-->
    <link rel="stylesheet" href="https://uicdn.toast.com/grid/latest/tui-grid.css"/>
    <script src="https://uicdn.toast.com/grid/latest/tui-grid.js"></script>

    <!--Highchart-->
    <script src="https://code.highcharts.com/highcharts.js"></script>

</head>


<body>

<div class="container">
    <div class="select">
        <span>시작일 선택: </span>
        <input type="date" class="date_pick change" id="start_date" style="width: 100px;">
        <span>종료일 선택: </span>
        <input type="date" class="date_pick change" id="end_date" style="width: 100px;">
        <span>데이터 선택: </span>
        <select class="data_type change" id="data_type" title="선택" required onchange=";">
            <option class="op" value="ALL" selected>온/습도</option>
            <option class="op" value="TMP">온도</option>
            <option class="op" value="REH">습도</option>
        </select>

<%--        <input type="button" id="select_btn" value="조회" onclick="select();">--%>
        <input type="button" id="reload_btn" value="새로고침" onclick="reload();">
    </div>

    <div class="draw_container">
        <div id="grid"></div>
        <div id="chart"></div>
    </div>

</div>
</body>
</html>
