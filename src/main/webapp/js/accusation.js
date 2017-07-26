/**
 * Created by Aaron on 2017/7/4.
 */
$(document).ready(function () {
    var num = 0;
    $(".title-tip").each(function () {
        $(this).prepend(++num + ".");
    });
});
$("#commit").click( function () {
    if(!$("#code").val()){
        alert("請輸入驗證碼");
        return;
    }
    alert("提交成功");
});
$("#occurrenceTime").focus(function () {
    $(this).jeDate({
        skinCell:"jedateblue",
        format : "YYYY/MM/DD hh:mm",
        isinitVal : true,
        isTime : true,
        isClear : true
    })
});
var clock = 60, num = clock;
var timer;
$("#getMesCode").click(function () {
    $(this).attr("disabled", true);
    $("#getMesCode").html(num + "s 后重新獲取");
    timer = setInterval(function () {
        $("#getMesCode").html(--num + "s 后重新獲取");
        if(num == -1){
            num = clock;
            $("#getMesCode").removeAttr("disabled");
            $("#getMesCode").html("獲取");
            console.log("has stop");
            clearInterval(timer);
        }
    }, 1000);
});

var beAccusationList = [];
$("#addBeAccusation").click(function () {
    if(!$("#accName").val()){
        alert("舉報人姓名不能為空");
        return;
    }
    if(!$("#accJob").val()){
        alert("舉報人職稱不能為空");
        return;
    }
    /*if(!$("#accDepartment").val()){
     alert("舉報人部門不能為空");
     return;
     }*/
   var beAccusation = [];
   beAccusation.name = $("#accName").val();
   beAccusation.job = $("#accJob").val();
   beAccusationList.push(beAccusation);
   $("#accName").val("");
   $("#accJob").val("");
   showBeAccusation();
});

function showBeAccusation() {
    if(beAccusationList.length > 0){
        $("#beAccusationTable tbody").html("");
        var html = "";
        for(var i in beAccusationList){
            html += "<tr>";
            html += "<td>" + beAccusationList[i].name + "</td>";
            html += "<td>" + beAccusationList[i].job + "</td>";
            html += "<td><button class='btn btn-xs btn-danger' " +
                "onclick='deleteBeAccusation("+ i +")'><b class='glyphicon glyphicon-minus'></b></button></td>";
            html += "</tr>";
        }
        $("#beAccusationTable tbody").html(html);
        $("#beAccusationTable").show();
    }else{
        $("#beAccusationTable").hide();
    }
}

function deleteBeAccusation(index) {
    beAccusationList.splice(index, 1);
    showBeAccusation();
}