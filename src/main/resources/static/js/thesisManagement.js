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

    $('#searcMoreRow').css('display', 'none');

    $('#searchMoreBtn').click(function () {
        if ($('#searchBtn').is(':visible') == false) {
            $('#searchBtn').css('display', 'block');
            $('#projectNameInput').css('display', 'block');
            $('#searcMoreRow').css('display', 'none');

        } else {
            $('#searchBtn').css('display', 'none');
            $('#projectNameInput').css('display', 'none');
            $('#searcMoreRow').css('display', 'block');

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

    $('input[type=radio][name=status]').change(function () {
        if (this.value == '0') {
            document.getElementById("typeDiv").classList.remove("hide");
        } else if (this.value == '1') {
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
                    var operateHtml = '<span> ' + row.author1.userName + '</span>';
                    return operateHtml;
                }
            }, {
                title: "作者2",
                field: "author2",
                formatter: function (value, row, index) {
                    var operateHtml = '<span> ' + row.author2.userName + '</span>';
                    return operateHtml;
                }
            }, {
                title: "作者3",
                field: "author3",
                formatter: function (value, row, index) {
                    var operateHtml = '<span> ' + row.author3.userName + '</span>';
                    return operateHtml;
                }
            }, {
                title: "刊名",
                field: "journal"
            }, {
                title: "期、卷",
                field: "volume",

            }, {
                title: "论文状态",
                field: "status"
            }, {
                title: "审核状态",
                field: "userStatus",
                formatter: function (value, row, index) {
                    var operateHtml;
                    if (row.status == "NORMAL") {
                        operateHtml = '<span> 正常</span>';
                    } else {
                        if (row.status == "REVIEWING") {
                            operateHtml = '<span class=" btn_blue" onclick="agreeModal(\'' + row.thesisId + '\')"> 升级审核</span>';

                        } else {
                            operateHtml = '<span> 删除</span>';
                        }
                    }

                    return operateHtml;
                }
            }, {
                title: "操作",
                field: "editfunction",
                formatter: function (value, row, index) {

                    var operateHtml = '<span  class=" btn_blue"  ' +
                        ' onclick="editModal(\'' + JSON.stringify(row).replace(/"/g, '&quot;')
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
        modal.find('.modal-body #me_thesisIdInput').val(data.thesisId);
        modal.find('.modal-body #me_thesisTitleInput').val(data.thesisTitle);
        modal.find('.modal-body #me_author1Input').val(data.author1.userName);
        modal.find('.modal-body #me_author1idInput').val(data.author1.userId);
        modal.find('.modal-body #me_author2Input').val(data.author2.userName);
        modal.find('.modal-body #me_author2idInput').val(data.author2.userId);
        modal.find('.modal-body #me_author3Input').val(data.author3.userName);
        modal.find('.modal-body #me_author3idInput').val(data.author3.userId);
        modal.find('.modal-body #me_journalInput').val(data.journal);
        modal.find('.modal-body #me_volumeInput').val(data.volume);
        modal.find('.modal-body #me_urlInput').val(data.url);
        modal.find('.modal-body #me_pagesInput').val(data.pages);
        modal.find('.modal-body #me_privacyInput').text(data.privacy);


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
                ids.push(this.thesisId);// cid为获得到的整条数据中的一列
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
        url: "/RemoveThesis",
        data: "ids=" + ids,
        type: "post",
        success: function (data) {
            var isSuccess = data.split(" ")[0];
            console.log(isSuccess)
            if (isSuccess == "true") {
                layer.msg('成功', {
                    icon: 1
                });
                $('#table_list').bootstrapTable(('refresh')); // 很重要的一步，刷新url
            } else {
                layer.msg('失败', {
                    icon: 2
                });
            }

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

    var thesisInfo = new Object();
    /* var authorId $('#ma_authorSpan').find("span").eq(0).text();
      console.log()*/


    thesisInfo.thesisTitle = document.getElementById("ma_thesisTitleInput").value;
    thesisInfo.author1id = document.getElementById("ma_author1idInput").value;
    thesisInfo.author2id = document.getElementById("ma_author2idInput").value;
    thesisInfo.author3id = document.getElementById("ma_author3idInput").value;

    thesisInfo.journal = document.getElementById("ma_journalInput").value;

    thesisInfo.volume = document.getElementById("ma_volumeInput").value;
    thesisInfo.url = document.getElementById("ma_urlInput").value;
    thesisInfo.pages = document.getElementById("ma_pagesInput").value;

    thesisInfo.privacy = $('#ma_privacySelect option:selected').val();


    var thesisInfos = JSON.stringify(thesisInfo)
    $.ajax({
        url: "/AddThesisM",
        contentType: "application/json",
        data: thesisInfos,
        type: "post",

        success: function (data) {
            var isSuccess = data.split(" ")[0];
            console.log(isSuccess)
            if (isSuccess == "true") {
                layer.msg('成功', {
                    icon: 1
                });
                $('#addProjectModal').modal('hide'); //隐藏modal
                $('.modal-backdrop').remove(); //去掉遮罩层
            } else {
                layer.msg('失败', {
                    icon: 2
                });
            }
        },
        error: function (data) {
            layer.msg('操作失败', {
                icon: 2
            });
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

$("#me_thesisTitle_a").on("click", function () {
    if (document.getElementById("me_thesisTitle_a").innerText == "修改") {
        document.getElementById("me_thesisTitleInput").disabled = "";
        document.getElementById("me_thesisTitle_a").innerText = "完成";
        document.getElementById("me_thesisTitle_a").classList.remove("ahide");
    } else {
        document.getElementById("me_thesisTitleInput").disabled = "disabled";
        document.getElementById("me_thesisTitle_a").innerText = "修改";
        document.getElementById("me_thesisTitle_a").classList.add("ahide");
        var thesisTitle = document.getElementById("me_thesisTitleInput").value;
        var thesisId = document.getElementById("me_thesisIdInput").value;

        $.ajax({
            url: "/SetThesisTitle",
            dataType: "json",
            data: {
                thesisTitle: thesisTitle,
                thesisId: thesisId

            },
            success: function (data) {
                layer.msg('修改成功', {icon: 1});
            }

        })
    }


})

$("#me_author1_a").on("click", function () {
    document.getElementById("authorType").value = "e1";
    console.log();
    $('#addMemberModal').modal('show');
    /*$.ajax({
        url: "/SetAuthor1",
        dataType: "json",
        data: {
            userId: userId,
            thesisId:thesisId

        },
        success: function (data) {
            layer.msg('修改成功' , {icon: 1});
        }

    })*/


})


$("#me_author2_a").on("click", function () {
    document.getElementById("authorType").value = "e2";
    console.log();
    $('#addMemberModal').modal('show');
    /*$.ajax({
        url: "/SetAuthor1",
        dataType: "json",
        data: {
            userId: userId,
            thesisId:thesisId

        },
        success: function (data) {
            layer.msg('修改成功' , {icon: 1});
        }

    })*/


})

$("#me_author3_a").on("click", function () {
    document.getElementById("authorType").value = "e3";
    console.log();
    $('#addMemberModal').modal('show');
    /*$.ajax({
        url: "/SetAuthor1",
        dataType: "json",
        data: {
            userId: userId,
            thesisId:thesisId

        },
        success: function (data) {
            layer.msg('修改成功' , {icon: 1});
        }

    })*/


})


$("#me_journal_a").on("click", function () {
    if (document.getElementById("me_journal_a").innerText == "修改") {
        document.getElementById("me_journalInput").disabled = "";
        document.getElementById("me_journal_a").innerText = "完成";
        document.getElementById("me_journal_a").classList.remove("ahide");
    } else {
        document.getElementById("me_journalInput").disabled = "disabled";
        document.getElementById("me_journal_a").innerText = "修改";
        document.getElementById("me_journal_a").classList.add("ahide");
        var journal = document.getElementById("me_journalInput").value;
        var thesisId = document.getElementById("me_thesisIdInput").value;


        $.ajax({
            url: "/SetJournal",
            dataType: "json",
            data: {
                journal: journal,
                thesisId: thesisId

            },
            success: function (data) {
                layer.msg('修改成功', {icon: 1});
            }

        })
    }


})


$("#me_volume_a").on("click", function () {
    if (document.getElementById("me_volume_a").innerText == "修改") {
        document.getElementById("me_volumeInput").disabled = "";
        document.getElementById("me_volume_a").innerText = "完成";
        document.getElementById("me_volume_a").classList.remove("ahide");
    } else {
        document.getElementById("me_volumeInput").disabled = "disabled";
        document.getElementById("me_volume_a").innerText = "修改";
        document.getElementById("me_volume_a").classList.add("ahide");
        var volume = document.getElementById("me_volumeInput").value;
        var thesisId = document.getElementById("me_thesisIdInput").value;
        $.ajax({
            url: "/SetVolume",
            dataType: "json",
            data: {
                volume: volume,
                thesisId: thesisId

            },
            success: function (data) {
                layer.msg('修改成功', {icon: 1});
            }

        })
    }


})


$("#me_url_a").on("click", function () {
    if (document.getElementById("me_url_a").innerText == "修改") {
        document.getElementById("me_urlInput").disabled = "";
        document.getElementById("me_url_a").innerText = "完成";
        document.getElementById("me_url_a").classList.remove("ahide");
    } else {
        document.getElementById("me_urlInput").disabled = "disabled";
        document.getElementById("me_url_a").innerText = "修改";
        document.getElementById("me_url_a").classList.add("ahide");
        var url = document.getElementById("me_urlInput").value;
        var thesisId = document.getElementById("me_thesisIdInput").value;


        $.ajax({
            url: "/SetUrl",
            dataType: "json",
            data: {
                url: url,
                thesisId: thesisId

            },
            success: function (data) {
                layer.msg('修改成功', {icon: 1});
            }

        })
    }


})


$("#me_pages_a").on("click", function () {
    if (document.getElementById("me_pages_a").innerText == "修改") {
        document.getElementById("me_pagesInput").disabled = "";
        document.getElementById("me_pages_a").innerText = "完成";
        document.getElementById("me_pages_a").classList.remove("ahide");
    } else {
        document.getElementById("me_pagesInput").disabled = "disabled";
        document.getElementById("me_pages_a").innerText = "修改";
        document.getElementById("me_pages_a").classList.add("ahide");
        var pages = document.getElementById("me_pagesInput").value;
        var thesisId = document.getElementById("me_thesisIdInput").value;


        $.ajax({
            url: "/SetPages",
            dataType: "json",
            data: {
                pages: pages,
                thesisId: thesisId

            },
            success: function (data) {
                layer.msg('修改成功', {icon: 1});
            }

        })
    }


})

$("#me_privacy_a").on("click", function () {
    if (document.getElementById("me_privacy_a").innerText == "修改") {
        document.getElementById("me_privacySelect").classList.remove("hide");
        document.getElementById("me_privacyInput").classList.add("hide");
        document.getElementById("me_privacy_a").innerText = "完成";
        document.getElementById("me_privacy_a").classList.remove("ahide");
    } else {
        document.getElementById("me_privacySelect").classList.add("hide");
        document.getElementById("me_privacyInput").classList.remove("hide");
        document.getElementById("me_privacy_a").innerText = "修改";
        document.getElementById("me_privacy_a").classList.add("ahide");
        var privacy = $("#me_privacySelect option:selected").val();
        var privacyName = $("#me_privacySelect option:selected").text();
        document.getElementById("me_privacyInput").innerText = privacyName;
        var thesisId = document.getElementById("me_thesisIdInput").value;

        $.ajax({
            url: "/SetPrivacy",

            data: {
                thesisId: thesisId,
                privacy: privacy
            },
            success: function (data) {
                var isSuccess = data.split(" ")[0];
                console.log(isSuccess)
                if (isSuccess == "true") {
                    layer.msg('修改成功', {
                        icon: 1
                    });
                    $('#table_list').bootstrapTable(('refresh')); // 很重要的一步，刷新url
                } else {
                    layer.msg('修改失败', {
                        icon: 2
                    });
                }
            }
        })
    }
})


$("#addMemberBtn").on("click", function () {
    $('#addMemberModal').modal('show');

})


function addAuthorModal(obj) {
    document.getElementById("authorType").value = obj;
    console.log(obj);
    $('#addMemberModal').modal('show');

}

function searchAddMember() {
    var userId = document.getElementById("modal_addUser_userId").value;
    $.ajax({
        url: "/SearchAuthor",
        data: {
            userId: userId
        },
        success: function (data) {
            if (data != null) {
                $('#searchMemberList').html(data);
            }
        }
    });
}

function addMember(obj) {
    var num = document.getElementById("authorType").value;
    var mydiv = obj.parentNode;
    var userId = mydiv.childNodes.item(3).innerText;
    var userName = mydiv.childNodes.item(7).innerText;
    var thesisId = document.getElementById("me_thesisIdInput").value;

    if (num == "1") {
        document.getElementById("ma_author1Input").value = userName;
        document.getElementById("ma_author1idInput").value = userId;

    } else if (num == "2") {

        document.getElementById("ma_author2Input").value = userName;
        document.getElementById("ma_author2idInput").value = userId;

    } else if (num == "3") {
        document.getElementById("ma_author3Input").value = userName;
        document.getElementById("ma_author3idInput").value = userId;
    } else if (num == "e1") {
        document.getElementById("me_author1Input").value = userName;
        document.getElementById("me_author1idInput").value = userId;
        $.ajax({
            url: "/SetAuthorFirst",

            data: {
                userId: userId,
                thesisId: thesisId

            },
            success: function (data) {
                var isSuccess = data.split(" ")[0];
                console.log(isSuccess)
                if (isSuccess == "true") {
                    layer.msg('修改成功', {
                        icon: 1
                    });
                    $('#addMemberModal').modal('hide');//隐藏modal
                    $('.modal-backdrop').remove();//去掉遮罩层
                    $('#table_list').bootstrapTable(('refresh')); // 很重要的一步，刷新url
                } else {
                    layer.msg('修改失败', {
                        icon: 2
                    });
                }
            },
            error: function (data) {
                layer.msg('操作失败', {icon: 2});
            }

        })
    } else if (num == "e2") {
        document.getElementById("me_author2Input").value = userName;
        document.getElementById("me_author2idInput").value = userId;
        $.ajax({
            url: "/SetAuthorSecond",
            data: {
                userId: userId,
                thesisId: thesisId

            },
            success: function (data) {
                var isSuccess = data.split(" ")[0];
                console.log(isSuccess)
                if (isSuccess == "true") {
                    layer.msg('修改成功', {
                        icon: 1
                    });
                    $('#addMemberModal').modal('hide');//隐藏modal
                    $('.modal-backdrop').remove();//去掉遮罩层
                    $('#table_list').bootstrapTable(('refresh')); // 很重要的一步，刷新url
                } else {
                    layer.msg('修改失败', {
                        icon: 2
                    });
                }
            },
            error: function (data) {
                layer.msg('操作失败', {icon: 2});
            }
        })
    } else if (num == "e3") {
            document.getElementById("me_author3Input").value = userName;
            document.getElementById("me_author3idInput").value = userId;
            $.ajax({
                url: "/SetAuthorThird",

                data: {
                    userId: userId,
                    thesisId: thesisId

                },
                success: function (data) {
                    var isSuccess = data.split(" ")[0];
                    console.log(isSuccess)
                    if (isSuccess == "true") {
                        layer.msg('修改成功', {
                            icon: 1
                        });
                        $('#addMemberModal').modal('hide');//隐藏modal
                        $('.modal-backdrop').remove();//去掉遮罩层
                        $('#table_list').bootstrapTable(('refresh')); // 很重要的一步，刷新url
                    } else {
                        layer.msg('修改失败', {
                            icon: 2
                        });
                    }
                },

                error: function (data) {
                    layer.msg('操作失败', {icon: 2});
                }
            })
            }


        }

        function deleteMemberSpan(obj) {
            var myspan = obj.parentNode;
            var mydiv = myspan.parentNode;
            mydiv.removeChild(myspan);

        }

        function deleteRow(obj) {

            var tbody = obj.parentNode;
            layer.confirm('确定删除吗？', {btn: ['确定', '再想想']}, function () {
                layer.msg('删除成功', {icon: 1});
                tbody.removeChild(obj);
            });


        }


        function deleteAuthor(obj) {
            if (obj == "1") {
                document.getElementById("ma_author1Input").value = "";
                document.getElementById("ma_author1idInput").value = "";

            } else {
                if (obj == "2") {
                    document.getElementById("ma_author2Input").value = "";
                    document.getElementById("ma_author2idInput").value = "";

                } else {
                    document.getElementById("ma_author3Input").value = "";
                    document.getElementById("ma_author3idInput").value = "";

                }
            }
        }


        /**
         *
         * 表单内 升级审核
         * */
        function agreeModal(thesisId) {
            layer.confirm('是否确认通过升级审核？', {
                btn: ['通过', '再考虑一下'] //按钮
            }, function () {
                $.ajax({
                    url: "/AgreeThesisUpdate",
                    data: {
                        thesisId: thesisId
                    },
                    success: function (data) {
                        var isSuccess = data.split(" ")[0];
                        console.log(isSuccess)
                        if (isSuccess == "true") {
                            layer.msg('升级成功', {icon: 1});
                            $('#table_list').bootstrapTable(('refresh'));// 很重要的一步，刷新url

                        } else {
                            layer.msg('升级失败', {icon: 2});
                        }

                    }

                })


            });
        }

