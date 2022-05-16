
//화면 로드 시 api 호출, DB저장, 화면표시
$.ajax({
    url: "api/weather",
    type: "POST",
    contentType: "application/json; charset=UTF-8",

    success: function (weatherDto) {
        grid(weatherDto, "ALL");
        chart(weatherDto);
    },
    error: function () {
        alert("api 호출 실패")
    }
})

//grid
function grid(res,data_type) {

    let weather = [];
    let time = [];
    let weather_data = {};
    let column = [
        {
            header: '날짜',
            name: '날짜',
            filter: 'select',
            align: 'center'
        },
        {
            header: '시간',
            name: '시간',
            editor: 'text',
            filter: 'select',
            align: 'center'
        }
    ];

    if(data_type === "ALL") {

        for (let i = 0; i <= (res.length / 2) - 1; i++) {
            time.push((res[i].fcstTime.substring(1, 3)) + '시');
            weather_data = {"날짜": res[i].fcstDate, "시간": time[i], "습도": res[i].fcstValue}
            weather.push(weather_data);
        }

        for (let i = res.length / 2; i <= res.length - 1; i++) {
            let index = i % (res.length / 2)
            weather[index].온도 = (res[i].fcstValue);
        }

        column.push(
            {
                header: '온도 (°C)',
                name: '온도',
                filter: 'number',
                align: 'center'
            }
        )
        column.push(

            {
                header: '습도 (%)',
                name: '습도',
                filter: 'number',
                align: 'center'
            }
        )


    }
    else if(data_type === "REH"){
        for(let i = 0; i < res.length; i++){
            time.push((res[i].fcstTime.substring(1, 3)) + '시');
            weather_data = {"날짜": res[i].fcstDate, "시간": time[i], "습도": res[i].fcstValue}
            weather.push(weather_data);
        }
        column.push(
            {
                header: '습도 (%)',
                name: '습도',
                filter: 'number',
                align: 'center'
            }
        );
    }
    else if(data_type === "TMP"){
        for(let i = 0; i < res.length; i++){
            time.push((res[i].fcstTime.substring(1, 3)) + '시');
            weather_data = {"날짜": res[i].fcstDate, "시간": time[i], "온도": res[i].fcstValue}
            weather.push(weather_data);
        }
        column.push(
            {
                header: '온도 (°C)',
                name: '온도',
                filter: 'number',
                align: 'center'
            }
        )
    }

    $('.tui-grid-container').remove();

    const grid = new tui.Grid({
        el: document.getElementById("grid"),
        data: weather,
        columns: column,
        pageOptions: {
            useClient: true,
            perPage: 24
        }
    });

    tui.Grid.applyTheme('clean');

}

//chart
function chart(res) {

    let tmp = [];
    let reh = [];
    let date_time = [];

    for (let i = 0; i < res.length; i++) {

        date_time.push((res[i].fcstDate.substring(4) + ' (' + (res[i].fcstTime.substring(1, 3)) + '시)'));

        if (res[i].category === 'TMP') {
            tmp.push(res[i].fcstValue)
        } else (
            reh.push(res[i].fcstValue)
        )
    }

    Highcharts.chart('chart', {
        accessibility: {
            enabled: false
        },
        chart: {
            type: 'line'
        },
        title: {
            text: '날씨예보 (온/습도)'
        },
        xAxis: {
            categories: date_time,
            opposite: true
        },
        yAxis: [
            { // 첫번째 yAxis: 온도
                labels: {
                    format: '{value}°C',
                    style: {
                        color: '#5100ff'
                        //Highcharts.getOptions().colors[0] : Highchart color array preset
                    }
                },
                title: {
                    text: '온도',
                    style: {
                        color: '#5100ff'
                    }
                },
            },

            { // 두번째 yAxis: 습도
                labels: {
                    format: '{value} %',
                    style: {
                        color: '#7cb5ec'
                    }
                },
                //gridLineWidth: 0,
                title: {
                    text: '습도',
                    style: {
                        color: '#7cb5ec'
                    }
                },
                opposite: true
            }
        ],
        plotOptions: {
            line: {
                dataLabels: {
                    enabled: true
                },
                enableMouseTracking: true
            }
        },
        tooltip: {
            shared: true
        },
        series: [
            {
                name: '온도',
                data: tmp,
                color: '#5100ff',
                tooltip: {
                    valueSuffix: ' °C'
                }
            },

            {
                name: '습도',
                data: reh,
                color: '#7cb5ec',
                yAxis: 1,
                tooltip: {
                    valueSuffix: ' %'
                }
            }
        ]
    })
}


$(function () {
    //새로고침 버튼
    $("#reload_btn").click(function () {
        location.reload();
    });

    //날짜 미니멈 맥시멈 설정
    let today = new Date();

    let year = today.getFullYear();
    let month = ("0" + (today.getMonth() + 1)).slice(-2);
    let day = ("0" + (today.getDate())).slice(-2);
    let min_date = year + "-" + month + "-" + day;

    //max_date
    let tomorrow = (new Date(today.setDate(today.getDate() + 3)));
    let tmr_year = tomorrow.getFullYear();
    let tmr_month = ("0" + (tomorrow.getMonth() + 1)).slice(-2); //한자리 일때 두자리로 자릿수 맞추기
    let tmr_day = ("0" + (tomorrow.getDate())).slice(-2);
    let max_date = tmr_year + "-" + tmr_month + "-" + tmr_day;

    // console.log("내일: " + max_date);
    // console.log("어제: " + min_date);

    $('#start_date').attr('value', min_date);
    $('#end_date').attr('value',max_date);
    $('.date_pick').attr('min', min_date);
    $('.date_pick').attr('max', max_date);


    $("#start_date").change(function () {
        $('#end_date').attr('min', $('#start_date').val());
        $('#end_date').attr('max', max_date);
    });

    $(".change").change(function(){
        select_data();
    })
})

//날짜, 데이터타입 설정시 DB받아오기
function select_data(){
    let start_date = $("#start_date").val().replace(/-/g, "");
    let end_date = $("#end_date").val().replace(/-/g, ""); // 날짜 formatting: removing '-'
    let data_type = $("#data_type option:selected").val();

    let data = {"start_date": start_date, "end_date": end_date, "data_type": data_type}

    $.ajax({
        url: "select",
        type: "POST",
        data: data,
        success: function (res) {
            grid(res,data_type);
            chart(res);

        },
        error: function () {
            alert("조회실패")
        }

    });
}