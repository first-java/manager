$(function () {
    var loc = location.href;
    var n1 = loc.length;
    var n2 = loc.indexOf('=');
    var txt = decodeURI(loc.substr(n2 + 1, n1 - n2));
    $("#work").val(txt)
    $("#divid").hide()
    $("#divbin").hide()
    $("#demo").hide();
    $("#divlottype").hide()
})

function selectBinUnit() {
    layui.use('layer', function () {
        var layer = layui.layer;

        var binUnitId = $("#binUnitId").prop("value");
        if (binUnitId == "") {
            layer.alert("请先输入蓝膜号！", function (index) {
                layer.close(index);
                $("#binUnitId").select()
            });
            return;
        }
        var flag = binUnitId.startsWith("S") ? "S" : binUnitId.startsWith("A") ? "A" : "E";
        if (flag != "A" && flag != "S") {
            layer.alert("扫描的信息不符合静电袋或编带批次号的要求，请扫Y开头的静电袋或S开头编带批次号！", function (index) {
                layer.close(index);
                $("#binUnitId").select()
            });
            return;
        }

        $.post("/selectBinUnit", {
            "binUnitId": binUnitId,
            "flag": flag
        }, function (data) {
            console.log("data:" + data);
            if (data.code == 1) {
                layer.alert(data.msg, function (index) {
                    layer.close(index);
                    $("#binUnitId").select()
                });
                return;
            }
            $("#divid").show()
            $("#divbin").show()
            $("#divlottype").show()
            $("#materialName").val(data.MATERIALNAME)
            $("#unitIdQty").val(data.QTY)
            $("#lotHead").val(data.LOTHEAD)
            $("#barcode").select()
        });
    });
}

function selectBarcode() {
    layui.use('layer', function () {
        var layer = layui.layer;
        var barcode1 = $("#barcode").prop("value");
        if (barcode1 == "") {
            layer.alert("请先输入卷轴号！", function (index) {
                layer.close(index);
                $("#barcode").select()
            });
            return;
        }
        $.post("/selectBarcode", {
            "barcode": barcode1
        }, function (data) {
            console.log("data:" + data);
            if (data.code == 1) {
                layer.alert(data.msg, function (index) {
                    layer.close(index);
                    $("#barcode").select()
                });
                return;
            }
            var barcode = document.getElementsByName("barcode")
            var lotHead = $("#lotHead").prop("value");
            var materialName = $("#materialName").prop("value");
            if (lotHead.substr(0, 5) != data.ROOT_LOT_ID || data.MATERIAL_NAME != materialName) {
                layer.alert("批次头或者级别不一致，不能补料！", function (index) {
                    layer.close(index);
                    $("#barcode").select()
                });
                return;
            }
            for (var i = 0; i < barcode.length; i++) {
                if (barcode1 == barcode[i].value) {
                    layer.msg("数据重复，此卷轴号[" + barcode1 + "]已经在下列表当中！", {
                        icon: 2
                    });
                    return;
                }
            }
            s = "<tr name='sss'><td>" + (Number(barcode.length) + 1) + "</td><td><input type='hidden' name='barcode'  value=" + data.TAPE_ID + " />" + data.TAPE_ID
                + "</td><td><input type='hidden' name='lotHead' value=" + lotHead + " />" + lotHead + "</td><td><input type='hidden' name='materialName' value=" + materialName + " />" + materialName +
                "</td><td><div class='layui-input-inline'><input onkeypress='if(event.keyCode==13) selectInput(this," + (Number(barcode.length) + 1) + ")' value='0' id='id"
                + (Number(barcode.length) + 1) + "' type='number'  name='qty' lay-verify='required'" + "autocomplete='off' class='layui-input'></div>"
                + "</td><td><button type='button' onclick='deleteRow(this)' class='layui-btn layui-btn-sm layui-btn-normal'><i class='layui-icon'></i> 删除</button></tr>";
            $("#demo").append(s);
            $("#demo").show();
        });
    });
}

function inputBarocde() {
    layui.use('layer', function () {
        var aInput = document.getElementsByName("lotHead");
        var binUnitId = $("#binUnitId").prop("value");

        var lotHead = $("#lotHead").prop("value");
        var materialName = $("#materialName").prop("value");
        var workId = $("#workId").prop("value");
        var unitIdQty = $("#unitIdQty").prop("value");
        var luck = document.getElementsByName("qty");
        var count1 = 0;
            for (var i = 0; i < luck.length; i++) {
                if (luck[i].value == 0) {
                    layer.msg("补料数量不能为0！", {
                        icon: 2
                    });
                    return;
                }
                count1 += Number(luck[i].value);
            }
            if (Number(unitIdQty) < Number(count1)) {
                layer.msg("静电袋数量为：[" + unitIdQty + "],补料总数不能大于静电袋 数量！", {
                    icon: 2
                });
                return;
            }
        if (aInput.length < 1) {
            layer.msg("没有数据提交，不要瞎点！", {
                icon: 2
            });
            $("#barcode").select();
            return false;
        }
        var barcode = "[";
        $("[name=barcode]").each(function () {
            barcode += '{"barcode":"' + this.value + '"},';
        });
        var reg = /,$/gi;
        barcode = barcode.replace(reg, "");
        barcode += "]";

        var qty = "[";
        $("[name=qty]").each(function () {
            qty += '{"qty":"' + this.value + '"},';
        });
        var reg = /,$/gi;
        qty = qty.replace(reg, "");
        qty += "]";

        $.post("/saveBarcodeFedBatch", {
            "binUnitId": binUnitId,
            "qty": qty,
            "lotHead": lotHead,
            "barcode": barcode,
            "materialName": materialName,
            "unitIdQty": unitIdQty,
            "workId": workId
        }, function (data) {
            console.log("data:" + data);
            layer.alert(data.msg, function (index) {
                layer.close(index);

                window.location.href = "/barcodeFedBatch?workId=" + data.workId;
            });

        });
    });

}

function deleteRow(r) {
    var i = r.parentNode.parentNode.rowIndex;
    document.getElementById('demo').deleteRow(i);
}

function selectInput(obj, iddd) {
    var idd = Number(iddd) + 1;
    $("#id" + idd + "").select();
    var bagnameQty = $("#bagnameQty").prop("value");
    var luck = document.getElementsByName("qty");
    var count1 = 0;
    for (var i = 0; i < luck.length; i++) {
        count1 += Number(luck[i].value);
    }
    if (Number(bagnameQty) < Number(count1)) {
        layer.msg("静电袋数量为：[" + bagnameQty + "],补料总数不能大于静电袋 数量！", {
            icon: 2
        });
        return;
    }
}