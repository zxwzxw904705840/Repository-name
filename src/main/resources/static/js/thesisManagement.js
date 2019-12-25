


$(function () {

    $("#input-id").fileinput({
        'theme': 'explorer-fas',
        'uploadUrl': '../../cwupload',
        overwriteInitial: false,
        initialPreviewAsData: true,
        initialPreview: [
            "http://lorempixel.com/1920/1080/nature/1",
            "http://lorempixel.com/1920/1080/nature/2",
            "http://lorempixel.com/1920/1080/nature/3"
        ],
        initialPreviewConfig: [
            {caption: "nature-1.jpg", size: 329892, width: "120px", url: "{$url}", key: 1},
            {caption: "nature-2.jpg", size: 872378, width: "120px", url: "{$url}", key: 2},
            {caption: "nature-3.jpg", size: 632762, width: "120px", url: "{$url}", key: 3}
        ]
    });

    $('#searcMoreRow').css('display','none');

    $('#searchMoreBtn').click(function() {
        if( $('#searchBtn').is(':visible') == false){
            $('#searchBtn').css('display','block');
            $('#projectNameInput').css('display','block');
            $('#searcMoreRow').css('display','none');

        }else{
            $('#searchBtn').css('display','none');
            $('#projectNameInput').css('display','none');
            $('#searcMoreRow').css('display','block');

        }
    })

    $('#datetimepicker1').datetimepicker({
        format: 'YYYY-MM',
        locale: moment.locale('zh-cn')
    });
    $('#datetimepicker2').datetimepicker({
        format: 'YYYY-MM',
        locale: moment.locale('zh-cn')
    });

    $('#datetimepicker3').datetimepicker({
        format: 'YYYY-MM',
        locale: moment.locale('zh-cn')
    });

    $('#datetimepicker4').datetimepicker({
        format: 'YYYY-MM',
        locale: moment.locale('zh-cn')
    });


    $('.mydatetimepicker').datetimepicker({
        format: 'YYYY-MM',
        locale: moment.locale('zh-cn')
    });



    // nav收缩展开
    $('.navMenu li a').on('click', function () {
        var parent = $(this).parent().parent();//获取当前页签的父级的父级
        var labeul = $(this).parent("li").find(">ul")
        if ($(this).parent().hasClass('open') == false) {
            //展开未展开
            parent.find('ul').slideUp(300);
            parent.find("li").removeClass("open")
            parent.find('li a').removeClass("active").find(".arrow").removeClass("open")
            $(this).parent("li").addClass("open").find(labeul).slideDown(300);
            $(this).addClass("active").find(".arrow").addClass("open")
        } else {
            $(this).parent("li").removeClass("open").find(labeul).slideUp(300);
            if ($(this).parent().find("ul").length > 0) {
                $(this).removeClass("active").find(".arrow").removeClass("open")
            } else {
                $(this).addClass("active")
            }
        }

    });
});

/*
* 初始化BootstrapTable,动态从服务器加载数据
* */
$(document).ready(function () {

    $('input[type=radio][name=status]').change(function() {
        if (this.value == '0') {
            document.getElementById("typeDiv").classList.remove("hide");
        }
        else if (this.value == '1') {
            document.getElementById("typeDiv").classList.add("hide");

        }
    });


    //  var teachcourseid=document.getElementById("teachcourseidInput").value;

    dataTableHot = $("#table_list").bootstrapTable({
        //使用get请求到服务器获取数据
        method: "POST",
        //必须设置，不然request.getParameter获取不到请求参数
        contentType: "application/x-www-form-urlencoded",
        //获取数据的Servlet地址
        url: "../../FindThesisList",
        //表格显示条纹
        striped: true,
        //启动分页
        pagination: true,
        //每页显示的记录数
        pageSize: 10,
        //当前第几页
        pageNumber: 1,
        //记录数可选列表
        pageList: [5, 10, 15, 20, 25],
        //是否启用查询
        search: false,
        //表示服务端请求
        sidePagination: "server",
        //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
        //设置为limit可以获取limit, offset, search, sort, order
        //  queryParamsType: "undefined",
        // queryParams: queryParams,
        queryParamsType: "",
        queryParams: getParams,
        //json数据解析
        responseHandler: function (res) {
            console.log("res", res)
            return {
                "rows": res.rows,
                "total": res.total
            };
        },
        //数据列
        columns: [
            {
                checkbox: true
            },
            {
                title: "论文标题",
                field: "thesisTitle",

            }, {
                title: "作者1",
                field: "author1",
                formatter: function (value, row, index) {
                    var operateHtml  = '<span> '+row.author1.userName+'</span>';
                    return operateHtml;
                }
            }, {
                title: "作者2",
                field: "author2",
                formatter: function (value, row, index) {
                    var operateHtml  = '<span> '+row.author2.userName+'</span>';
                    return operateHtml;
                }
            },{
                title: "作者3",
                field: "author3",
                formatter: function (value, row, index) {
                    var operateHtml  = '<span> '+row.author3.userName+'</span>';
                    return operateHtml;
                }
            },{
                title: "刊名",
                field: "journal"
            }, {
                title: "期、卷",
                field: "volume",

            }, {
                title: "论文状态",
                field: "status"
            }, {
                title: "操作",
                field: "editfunction",
                formatter: function (value, row, index) {

                    var operateHtml = '<span  class=" btn_blue"  ' +
                        ' onclick="editModal(\''+  JSON.stringify(row).replace(/"/g, '&quot;')
                        + '\')"> 编辑</span>';
                    return operateHtml;

                }
            }]


    });

});

/**
 *
 * 设置额外BootstrapTable请求参数
 *
 * */
function getParams(params) {

    return {
        //表格显示条纹
        striped: true,
        //启动分页
        pagination: true,
        //     pageSize: 10,
        //当前第几页
        //    pageNumber: 1,
        pageSize: params.pageSize,
        pageNumber: params.pageNumber,
        //记录数可选列表
        pageList: [5, 10, 15, 20, 25],
        thesisTitle: document.getElementById("thesisTitleInput").value,

    }
}


/**
 *
 * 表单内 编辑信息
 * */
function editModal(result) {
    var data = JSON.parse(result);


    console.log(data)


    $("#editProjectModal").on("show.bs.modal", function () {

        var modal = $(this);  //get modal itself
        /*

                modal.find('.modal-body #me_userIdInput').val(data.userId);
                modal.find('.modal-body #me_emailInput').val(data.email);
                modal.find('.modal-body #me_phoneInput').val(data.phone);
                modal.find('.modal-body #me_passwordInput').val(data.password);
                modal.find('.modal-body #me_userNameInput').val(data.userName);
                modal.find('.modal-body #me_instituteNameInput').val(data.institute.instituteName);
                modal.find('.modal-body #me_titleInput').val(data.title);

         */

    }).modal("show");


}

/**
 *
 * 页面：新增按钮
 * */
$("#addBtn").on("click", function () {
    $("#addProjectModal").on("show.bs.modal", function () {

        var modal = $(this);  //get modal itself
        modal.find('.modal-body #ma_projectCodeInput').val("");
        modal.find('.modal-body #ma_projectNameInput').val("");
        modal.find('.modal-body #ma_projectManagerIdInput').val("");

        modal.find('.modal-body #ma_projectSourceInput').val("");
        modal.find('.modal-body #ma_projectEstablishDateInput').val("");
        modal.find('.modal-body #ma_projectPlannedDateInput').val("");
        modal.find('.modal-body #ma_projectFinishDateInput').val("");
        modal.find('.modal-body #ma_projectFundInput').val("");

    }).modal("show");

})


/**
 *
 * 表单内 升级审核
 * */
function agreeModal(userId) {
    layer.confirm('是否确认通过升级审核？', {
        btn: ['通过', '再考虑一下'] //按钮
    }, function () {
        $.ajax({
            url: "/AgreeUserUpdate",
            dataType: "json",
            data: {
                userId: userId
            },
            success: function (data) {
                layer.msg('升级成功' + userId, {icon: 1});
            }

        })


    });
}

/**
 *
 * 页面：导入按钮
 * */
$("#importBtn").on("click", function () {
    $('#importUserModal').modal('show');

})



/**
 * 页面  搜索按钮（组合搜索）
 */
function search() {

    $('#table_list').bootstrapTable(('refresh'));	// 很重要的一步，刷新url！
    document.getElementById("thesisTitleInput").value = "";
};


/**
 * 页面  搜索按钮（组合搜索）
 */
function searchMore() {


    $('#table_list').bootstrapTable(('refresh'));	// 很重要的一步，刷新url！
    document.getElementById("panel_projectCodeInput").value = "";
    document.getElementById("panel_instituteNameInput").value = "";
    document.getElementById("panel_projectEstablishDate").value = "";

};


/**
 * 页面 批量删除
 */
$("#deleteBtn").on("click", function () {
    layer.confirm('是否确认删除？', {
        btn: ['确认', '删除'] //按钮
    }, function () {
        var rows = $("#table_list").bootstrapTable('getSelections');// 获得要删除的数据
        if (rows.length == 0) {// rows 主要是为了判断是否选中，下面的else内容才是主要
            layer.msg('请先选择要删除的记录', {icon: 0});
        } else {
            var ids = new Array();// 声明一个数组
            $(rows).each(function () {// 通过获得别选中的来进行遍历
                ids.push(this.user_id);// cid为获得到的整条数据中的一列
            });
            deleteMs(ids)
        }
    });


})

/**
 * 页面 批量删除续 ajax
 */
function deleteMs(ids) {
    $.ajax({
        url: "/RemoveUser",
        data: "ids=" + ids,
        type: "post",
        success: function (data) {
            layer.msg('删除成功', {icon: 1});
            /*var obj = eval("("+data+")");
            var obj1 = JSON.parse(data);
            console.log(obj.message);
            console.log(obj1.message);*/

            /*     if (data == "success") {
                     layer.msg('删除成功', {icon: 1});
                 } else {
                     layer.msg('删除失败', {icon: 2});
                 }*/
            //   var teachcourseid=document.getElementById("teachcourseidInput").value;
            //   var turl = "../../findTBlist?teachcourseid=" + teachcourseid;

            // $('#table_list').bootstrapTable('refresh',turl);	// 很重要的一步，刷新url！
        },
        error: function (data) {
            layer.msg('操作失败', {icon: 2});
        }
    });
}

/**
 *
 * 模态框 添加——提交
 */

$("#ma_addBtn").on("click", function () {

    var projectInfo = new Object();

    projectInfo.projectCode = document.getElementById("ma_projectCodeInput").value;
    projectInfo.projectCode = document.getElementById("ma_projectCodeInput").value;
    projectInfo.projectManagerId = document.getElementById("ma_projectManagerIdInput").value;
    projectInfo.projectSource = document.getElementById("ma_projectSourceInput").value;

    projectInfo.projectEstablishDate = document.getElementById("ma_projectEstablishDateInput").value;
    projectInfo.projectPlannedDate = document.getElementById("ma_projectPlannedDateInput").value;
    projectInfo.projectFinishDate = document.getElementById("ma_projectFinishDateInput").value;
    projectInfo.projectFund = document.getElementById("ma_projectFundInput").value;

    projectInfo.projectType = $('#ma_projectTypeSelect option:selected').val();

    projectInfo.projectLevel = $('#ma_projectLevelSelect option:selected').val();

    projectInfo.projectProgress = $('#ma_projectProgressSelect option:selected').val();

    projectInfo.projectSourceType = $('#ma_projectSourceTypeSelect option:selected').val();

    projectInfo.projectResearchType = $('#ma_projectResearchTypeSelect option:selected').val();
    //var selectedRadio= $('#radioGroup input:radio:checked').val();

    var projectInfos = JSON.stringify(projectInfo)
    $.ajax({
        url: "/AddProject",
        contentType: "application/json",
        data: projectInfos,
        type: "post",

        success: function (data) {
            layer.msg('成功', {icon: 1});
            /* var obj = eval("("+data+")");
             var obj1 = JSON.parse(data);
             console.log(obj.message);
             console.log(obj1.message);*/
            //   layer.msg('操作成功', {icon: 0});

            $('#addProjectModal').modal('hide');//隐藏modal
            $('.modal-backdrop').remove();//去掉遮罩层
        },
        error: function (data) {
            layer.msg('操作失败', {icon: 2});
        }

    })


})

/**
 * 模态框 导入按钮
 * */

$("#ImportUserExcelBtn").on("click", function () {
    var userFile = $('#userFile').val();

    if (userFile != '') {
        var formData = new FormData($("#importUserForm")[0]);
        formData.append("userFile", userFile);
        $.ajax({
            url: "/ImportUserExcel",
            type: 'POST',
            data: formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                layer.alert('删除成功', {icon: 1});
                $('#importUserModal').modal('hide');//隐藏modal
                $('.modal-backdrop').remove();//去掉遮罩层

            },
            error: function (data) {
                layer.alert('操作失败', {icon: 2});
            }
        });
    } else {
        layer.alert('请先选择文件', {icon: 0});
    }
})

/**
 * 页面 导出按钮——待讨论
 */
$("#exportBtn").on("click", function () {
    layer.confirm('是否确认导出？', {
        btn: ['确认', '取消'] //按钮
    }, function () {
        window.location.href = "/userExcelExport";
    });

})

/**
 * 模态框  查看编辑 - 修改projectCode
 */

$("#me_projectCode_a").on("click", function () {
    if (document.getElementById("me_projectCode_a").innerText == "修改") {
        document.getElementById("me_projectCodeInput").disabled = "";
        document.getElementById("me_projectCode_a").innerText = "完成";
        document.getElementById("me_projectCode_a").classList.remove("ahide");
    } else {
        document.getElementById("me_projectCodeInput").disabled = "disabled";
        document.getElementById("me_projectCode_a").innerText = "修改";
        document.getElementById("me_projectCode_a").classList.add("ahide");
        var projectCode = document.getElementById("me_projectCodeInput").value;
        var projectId = document.getElementById("me_projectIdInput").value;

        console.log(userName, userId)
        $.ajax({
            url: "/SetProjectCode",
            dataType: "json",
            data: {
                projectCode: projectCode,
                projectId:projectId

            },
            success: function (data) {
                layer.msg('修改成功' , {icon: 1});
            }

        })
    }


})

$("#me_projectName_a").on("click", function () {
    if (document.getElementById("me_projectName_a").innerText == "修改") {
        document.getElementById("me_projectNameInput").disabled = "";
        document.getElementById("me_projectName_a").innerText = "完成";
        document.getElementById("me_projectName_a").classList.remove("ahide");
    } else {
        document.getElementById("me_projectNameInput").disabled = "disabled";
        document.getElementById("me_projectName_a").innerText = "修改";
        document.getElementById("me_projectName_a").classList.add("ahide");
        var projectName = document.getElementById("me_projectNameInput").value;
        var projectId = document.getElementById("me_projectIdInput").value;

        console.log(userName, userId)
        $.ajax({
            url: "/SetProjectName",
            dataType: "json",
            data: {
                projectName: projectName,
                projectId:projectId

            },
            success: function (data) {
                layer.msg('修改成功' , {icon: 1});
            }

        })
    }


})


$("#me_projectType_a").on("click", function () {
    if (document.getElementById("me_projectType_a").innerText == "修改") {
        document.getElementById("me_projectTypeSelect").classList.remove("hide");
        document.getElementById("me_projectTypeInput").classList.add("hide");
        document.getElementById("me_projectType_a").innerText = "完成";
        document.getElementById("me_projectType_a").classList.remove("ahide");
    } else {
        document.getElementById("me_projectTypeSelect").classList.add("hide");
        document.getElementById("me_projectTypeInput").classList.remove("hide");
        document.getElementById("me_projectType_a").innerText = "修改";
        document.getElementById("me_projectType_a").classList.add("ahide");
        var projectType = $("#me_projectTypeSelect option:selected").val();
        var projectTypeName = $("#me_projectTypeSelect option:selected").text();
        document.getElementById("me_projectTypeInput").innerText = projectTypeName;
        var projectId = document.getElementById("me_projectIdInput").value;
        console.log(projectType,projectTypeName, projectId)

        $.ajax({
            url: "/SetProjectType",
            dataType: "json",
            data: {
                projectType: projectType,
                projectId:projectId
            },
            success: function (data) {
                layer.msg('修改成功' , {icon: 1});
            }

        })

    }

})

$("#me_projectLevel_a").on("click", function () {
    if (document.getElementById("me_projectLevel_a").innerText == "修改") {
        document.getElementById("me_projectLevelSelect").classList.remove("hide");
        document.getElementById("me_projectLevelInput").classList.add("hide");
        document.getElementById("me_projectLevel_a").innerText = "完成";
        document.getElementById("me_projectLevel_a").classList.remove("ahide");
    } else {
        document.getElementById("me_projectLevelSelect").classList.add("hide");
        document.getElementById("me_projectLevelInput").classList.remove("hide");
        document.getElementById("me_projectLevel_a").innerText = "修改";
        document.getElementById("me_projectLevel_a").classList.add("ahide");
        var projectLevel = $("#me_projectLevelSelect option:selected").val();
        var projectLevelName = $("#me_projectLevelSelect option:selected").text();
        document.getElementById("me_projectLevelInput").innerText = projectLevelName;
        var projectId = document.getElementById("me_projectIdInput").value;
        console.log(projectLevel,projectLevelName, projectId)

        $.ajax({
            url: "/SetProjectLevel",
            dataType: "json",
            data: {
                projectLevel: projectLevel,
                projectId:projectId
            },
            success: function (data) {
                layer.msg('修改成功' , {icon: 1});
            }

        })

    }
})

$("#me_projectProgress_a").on("click", function () {
    if (document.getElementById("me_projectProgress_a").innerText == "修改") {
        document.getElementById("me_projectProgressSelect").classList.remove("hide");
        document.getElementById("me_projectProgressInput").classList.add("hide");
        document.getElementById("me_projectProgress_a").innerText = "完成";
        document.getElementById("me_projectProgress_a").classList.remove("ahide");
    } else {
        document.getElementById("me_projectProgressSelect").classList.add("hide");
        document.getElementById("me_projectProgressInput").classList.remove("hide");
        document.getElementById("me_projectProgress_a").innerText = "修改";
        document.getElementById("me_projectProgress_a").classList.add("ahide");
        var projectProgress = $("#me_projectProgressSelect option:selected").val();
        var projectProgressName = $("#me_projectProgressSelect option:selected").text();
        document.getElementById("me_projectProgressInput").innerText = projectProgressName;
        var projectId = document.getElementById("me_projectIdInput").value;
        console.log(projectProgress,projectProgressName, projectId)

        $.ajax({
            url: "/SetProjectProgress",
            dataType: "json",
            data: {
                projectProgress: projectProgress,
                projectId:projectId
            },
            success: function (data) {
                layer.msg('修改成功' , {icon: 1});
            }

        })

    }
})


$("#me_projectSource_a").on("click", function () {
    if (document.getElementById("me_projectSource_a").innerText == "修改") {
        document.getElementById("me_projectSourceInput").disabled = "";
        document.getElementById("me_projectSource_a").innerText = "完成";
        document.getElementById("me_projectSource_a").classList.remove("ahide");
    } else {
        document.getElementById("me_projectSourceInput").disabled = "disabled";
        document.getElementById("me_projectSource_a").innerText = "修改";
        document.getElementById("me_projectSource_a").classList.add("ahide");
        var projectSource = document.getElementById("me_projectSourceInput").value;
        var projectId = document.getElementById("me_projectIdInput").value;

        console.log(userName, userId)
        $.ajax({
            url: "/SetProjectSource",
            dataType: "json",
            data: {
                projectSource: projectSource,
                projectId:projectId

            },
            success: function (data) {
                layer.msg('修改成功' , {icon: 1});
            }

        })
    }


})



$("#me_projectEstablishDate_a").on("click", function () {
    if (document.getElementById("me_projectEstablishDate_a").innerText == "修改") {
        document.getElementById("me_projectEstablishDateInput").disabled = "";
        document.getElementById("me_projectEstablishDate_a").innerText = "完成";
        document.getElementById("me_projectEstablishDate_a").classList.remove("ahide");
    } else {
        document.getElementById("me_projectEstablishDateInput").disabled = "disabled";
        document.getElementById("me_projectEstablishDate_a").innerText = "修改";
        document.getElementById("me_projectEstablishDate_a").classList.add("ahide");
        var projectEstablishDate = document.getElementById("me_projectEstablishDateInput").value;
        var projectId = document.getElementById("me_projectIdInput").value;

        console.log(userName, userId)
        $.ajax({
            url: "/SetProjectEstablishDate",
            dataType: "json",
            data: {
                projectEstablishDate: projectEstablishDate,
                projectId:projectId

            },
            success: function (data) {
                layer.msg('修改成功' , {icon: 1});
            }

        })
    }


})



$("#me_projectPlannedDate_a").on("click", function () {
    if (document.getElementById("me_projectPlannedDate_a").innerText == "修改") {
        document.getElementById("me_projectPlannedDateInput").disabled = "";
        document.getElementById("me_projectPlannedDate_a").innerText = "完成";
        document.getElementById("me_projectPlannedDate_a").classList.remove("ahide");
    } else {
        document.getElementById("me_projectPlannedDateInput").disabled = "disabled";
        document.getElementById("me_projectPlannedDate_a").innerText = "修改";
        document.getElementById("me_projectPlannedDate_a").classList.add("ahide");
        var projectPlannedDate = document.getElementById("me_projectPlannedDateInput").value;
        var projectId = document.getElementById("me_projectIdInput").value;

        console.log(userName, userId)
        $.ajax({
            url: "/SetProjectPlannedDate",
            dataType: "json",
            data: {
                projectPlannedDate: projectPlannedDate,
                projectId:projectId

            },
            success: function (data) {
                layer.msg('修改成功' , {icon: 1});
            }

        })
    }


})



$("#me_projectFinishDate_a").on("click", function () {
    if (document.getElementById("me_projectFinishDate_a").innerText == "修改") {
        document.getElementById("me_projectFinishDateInput").disabled = "";
        document.getElementById("me_projectFinishDate_a").innerText = "完成";
        document.getElementById("me_projectFinishDate_a").classList.remove("ahide");
    } else {
        document.getElementById("me_projectFinishDateInput").disabled = "disabled";
        document.getElementById("me_projectFinishDate_a").innerText = "修改";
        document.getElementById("me_projectFinishDate_a").classList.add("ahide");
        var projectFinishDate = document.getElementById("me_projectFinishDateInput").value;
        var projectId = document.getElementById("me_projectIdInput").value;

        console.log(userName, userId)
        $.ajax({
            url: "/SetProjectFinishDate",
            dataType: "json",
            data: {
                projectFinishDate: projectFinishDate,
                projectId:projectId

            },
            success: function (data) {
                layer.msg('修改成功' , {icon: 1});
            }

        })
    }


})



$("#me_projectFund_a").on("click", function () {
    if (document.getElementById("me_projectFund_a").innerText == "修改") {
        document.getElementById("me_projectFundInput").disabled = "";
        document.getElementById("me_projectFund_a").innerText = "完成";
        document.getElementById("me_projectFund_a").classList.remove("ahide");
    } else {
        document.getElementById("me_projectFundInput").disabled = "disabled";
        document.getElementById("me_projectFund_a").innerText = "修改";
        document.getElementById("me_projectFund_a").classList.add("ahide");
        var projectFund = document.getElementById("me_projectFundInput").value;
        var projectId = document.getElementById("me_projectIdInput").value;

        console.log(userName, userId)
        $.ajax({
            url: "/SetProjectFund",
            dataType: "json",
            data: {
                projectFund: projectFund,
                projectId:projectId

            },
            success: function (data) {
                layer.msg('修改成功' , {icon: 1});
            }

        })
    }


})


$("#me_projectSourceType_a").on("click", function () {
    if (document.getElementById("me_projectSourceType_a").innerText == "修改") {
        document.getElementById("me_projectSourceTypeSelect").classList.remove("hide");
        document.getElementById("me_projectSourceTypeInput").classList.add("hide");
        document.getElementById("me_projectSourceType_a").innerText = "完成";
        document.getElementById("me_projectSourceType_a").classList.remove("ahide");
    } else {
        document.getElementById("me_projectSourceTypeSelect").classList.add("hide");
        document.getElementById("me_projectSourceTypeInput").classList.remove("hide");
        document.getElementById("me_projectSourceType_a").innerText = "修改";
        document.getElementById("me_projectSourceType_a").classList.add("ahide");
        var projectSourceType = $("#me_projectSourceTypeSelect option:selected").val();
        var projectSourceTypeName = $("#me_projectSourceTypeSelect option:selected").text();
        document.getElementById("me_projectSourceTypeInput").innerText = projectSourceTypeName;
        var projectId = document.getElementById("me_projectIdInput").value;
        console.log(projectSourceType,projectSourceTypeName, projectId)

        $.ajax({
            url: "/SetProjectSourceType",
            dataType: "json",
            data: {
                projectSourceType: projectSourceType,
                projectId:projectId
            },
            success: function (data) {
                layer.msg('修改成功' , {icon: 1});
            }

        })

    }
})


$("#me_projectResearchType_a").on("click", function () {
    if (document.getElementById("me_projectResearchType_a").innerText == "修改") {
        document.getElementById("me_projectResearchTypeSelect").classList.remove("hide");
        document.getElementById("me_projectResearchTypeInput").classList.add("hide");
        document.getElementById("me_projectResearchType_a").innerText = "完成";
        document.getElementById("me_projectResearchType_a").classList.remove("ahide");
    } else {
        document.getElementById("me_projectResearchTypeSelect").classList.add("hide");
        document.getElementById("me_projectResearchTypeInput").classList.remove("hide");
        document.getElementById("me_projectResearchType_a").innerText = "修改";
        document.getElementById("me_projectResearchType_a").classList.add("ahide");
        var projectResearchType = $("#me_projectResearchTypeSelect option:selected").val();
        var projectResearchTypeName = $("#me_projectResearchTypeSelect option:selected").text();
        document.getElementById("me_projectResearchTypeInput").innerText = projectResearchTypeName;
        var projectId = document.getElementById("me_projectIdInput").value;
        console.log(projectResearchType,projectResearchTypeName, projectId)

        $.ajax({
            url: "/SetProjectResearchType",
            dataType: "json",
            data: {
                projectResearchType: projectResearchType,
                projectId:projectId
            },
            success: function (data) {
                layer.msg('修改成功' , {icon: 1});
            }

        })

    }
})

$("#addMemberBtn").on("click", function () {
    $('#addMemberModal').modal('show');

})


function addAuthorModal(){
    $('#addMemberModal').modal('show');

}

function searchAddMember(){
    var userId = document.getElementById("modal_addUser_userId").value;
    $.ajax({
        url:"/SearchMember",
        data:{
            userId:userId
        },
        success: function(data){
            if(data != null){
                $('#searchMemberList').html(data);
            }
        }
    });
}

function addMember(obj){
    var mydiv = obj.parentNode;
    var userName= mydiv.childNodes.item(7).innerText;
    var html ="<span> "+userName+"<i  class=\"iDelete\" onclick=\"deleteMemberSpan(this)\">x</i></span>"
    $("#addModalAddUser").before(html)
   /* var old = document.getElementById("ma_authorSpan").innerText;
    var newSpan = old+" "+userName;
    document.getElementById("ma_authorSpan").innerText = newSpan;
*/
   console.log(userName)

//传参
//projectid

}

function  deleteMemberSpan(obj) {
    var myspan = obj.parentNode;
    var mydiv = myspan.parentNode;
    mydiv.removeChild(myspan);

}

function deleteRow(obj){

    var tbody = obj.parentNode;
    layer.confirm('确定删除吗？',{ btn: ['确定','再想想']},function(){
        layer.msg('删除成功', {icon: 1});
        tbody.removeChild(obj);});




}
