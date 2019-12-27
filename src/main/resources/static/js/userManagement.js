$(function () {


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
        url: "../../FindUserList",
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
        sidePagination: "client", //server
        //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
        //设置为limit可以获取limit, offset, search, sort, order
        //  queryParamsType: "undefined",
        // queryParams: queryParams,
        queryParamsType: "",
        queryParams: getParams,
        //json数据解析
        /*responseHandler: function (res) {
            console.log("res", res)
            return {
                "rows": res.rows,
             /!*   "total": res.total*!/
            };
        },*/
        //数据列
        columns: [
            {
                checkbox: true
            },
            {
                //field: 'Number',//可不加
                title: '序号',//标题  可不加
                formatter: function (value, row, index) {
                    return index + 1;
                }
            }, {
                title: "姓名",
                field: "userName",

            }, {
                title: "职称",
                field: "title",
                formatter: function (value, row, index) {
                    var operateHtml='';
                    var exp = row.title;
                    var tt=title(exp);
                    operateHtml  = '<span> '+tt+'</span>';

                    return operateHtml;
                }
            }, {
                title: "所属学院",
                field: "instituteOpt",
                formatter: function (value, row, index) {
                    var operateHtml='';
                    var exp = row.institute;

                    if (!exp && typeof(exp)!="undefined" && exp!=0)
                    {
                        operateHtml  = '<span> '+"暂无"+'</span>';
                    }else{
                        operateHtml  = '<span> '+exp.instituteName+'</span>';
                    }


                    return operateHtml;
                }
            }, {
                title: "联系方式",
                field: "phone"
            }, {
                title: "邮箱",
                field: "email"
            }, {
                title: "审核状态",
                field: "userStatus",
                formatter: function (value, row, index) {
                    var operateHtml;
                    if (row.userStatus == "NORMAL") {
                        operateHtml = '<span> 正常</span>';
                    } else if(row.userStatus == "REVIEWING"){
                        operateHtml = '<span class=" btn_blue" onclick="agreeModal(\'' + row.userId + '\')"> 升级审核</span>';
                    }else{
                        operateHtml = '<span> 删除</span>';
                    }

                    return operateHtml;
                }
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
        userStatus: $('#user_statusSelect option:selected').val(),//选中的值
        userName: document.getElementById("user_nameInput").value,
        instituteId: $('#user_instituteSelect option:selected').val(),//选中的值


    }
}

/**
 * 左侧列表  切换学院li——筛选表单
 */
function searchInstitute(e) {
    document.getElementById("institudeId").value = $(e).val();
    $('#table_list').bootstrapTable(('refresh'));	// 很重要的一步，刷新url！
    document.getElementById("user_nameInput").value = "";

};


/**
 *
 * 表单内 编辑信息
 * */
function editModal(result) {
    var data = JSON.parse(result);
    var institute = data.institute;

    console.log(data)


    $("#editUserModal").on("show.bs.modal", function () {

        var modal = $(this);  //get modal itself

        modal.find('.modal-body #me_userIdInput').val(data.userId);
        modal.find('.modal-body #me_emailInput').val(data.email);
        modal.find('.modal-body #me_phoneInput').val(data.phone);
        modal.find('.modal-body #me_passwordInput').val(data.password);
        modal.find('.modal-body #me_userNameInput').val(data.userName);
        var exp =data.institute;

        if (!exp && typeof(exp)!="undefined" && exp!=0)
        {
            modal.find('.modal-body #me_instituteNameInput').val("暂无");

        }else{
            modal.find('.modal-body #me_instituteNameInput').val(exp.instituteName);
        }


        modal.find('.modal-body #me_titleInput').val(title(data.title));


    }).modal("show");


}

/**
 *
 * 页面：新增按钮
 * */
$("#addBtn").on("click", function () {
    $("#addUserModal").on("show.bs.modal", function () {

        var modal = $(this);  //get modal itself

        modal.find('.modal-body #ma_userNameInput').val("");
        modal.find('.modal-body #ma_emailInput').val("");
        modal.find('.modal-body #ma_phoneInput').val("");
        modal.find('.modal-body #ma_passwordInput').val("");


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
            data: {
                userId: userId
            },
            success: function (data) {
                var isSuccess = data.split(" ")[0];
                console.log(isSuccess)
                if(isSuccess=="true"){
                    layer.msg('升级成功', {icon: 1});
                    $('#table_list').bootstrapTable(('refresh'));// 很重要的一步，刷新url

                }else{
                    layer.msg('升级失败', {icon: 2});
                }

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
    document.getElementById("user_nameInput").value = "";
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
                ids.push(this.userId);// cid为获得到的整条数据中的一列
            });
            deleteMs(ids)
        }
    });
})


/**
 * 页面 批量删除续 ajax
 */
function deleteMs(ids) {
    console.log("deleteMs",ids)
    $.ajax({
        url: "/RemoveUser",
        data: "ids=" + ids,
        type: "post",
        success: function (data) {

            var isSuccess = data.split(" ")[0];
            console.log(isSuccess)
            if(isSuccess=="true"){
                layer.msg('删除成功', {icon: 1});
                $('#table_list').bootstrapTable(('refresh'));// 很重要的一步，刷新url

            }else{
                layer.msg('删除失败', {icon: 2});
            }


        },
        error: function (data) {
            layer.msg('操作失败', {icon: 2});
        }
    });
}

/**
 *
 * 模态框 添加科研人员——提交
 */

$("#ma_addBtn").on("click", function () {

    var userInfo = new Object();

    userInfo.userId = document.getElementById("ma_userIdInput").value;
    userInfo.userName = document.getElementById("ma_userNameInput").value;
    userInfo.password = document.getElementById("ma_passwordInput").value;
    userInfo.phone = document.getElementById("ma_phoneInput").value;
    userInfo.email = document.getElementById("ma_emailInput").value;

    userInfo.instituteId = $('#ma_instituteNameSelect option:selected').val();
   //var selectedRadio= $('#radioGroup input:radio:checked').val();
    var selectedRadio =$("input[type='radio']:checked").val();

    console.log(selectedRadio)
    if(selectedRadio=="0"){
        console.log(true)
        userInfo.title = $('#ma_titleSelect option:selected').val();
    }else{
        console.log(false)
    }
    var userInfos = JSON.stringify(userInfo)
    $.ajax({
        url: "/AddUser",
        contentType: "application/json",
        data: userInfos,
        type: "post",

        success: function (data) {

            var isSuccess = data.split(" ")[0];
            console.log(isSuccess)
            if(isSuccess=="true"){
                layer.msg('成功', {icon: 1});
                $('#addUserModal').modal('hide');//隐藏modal
                $('.modal-backdrop').remove();//去掉遮罩层
                $('#table_list').bootstrapTable(('refresh'));// 很重要的一步，刷新url
            }else{
                layer.msg('失败', {icon: 2});
            }


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

                console.log(data)
                if(data.split(" ")[0]=="true"){
                    layer.msg('导入成功', {icon: 1});
                    $('#importUserModal').modal('hide');//隐藏modal
                    $('.modal-backdrop').remove();//去掉遮罩层

                }else{
                    layer.msg('导入失败', {icon: 2});
                }


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
 * 模态框  查看编辑科研人员详情 - 修改用户名
 */

$("#me_userName_a").on("click", function () {
    if (document.getElementById("me_userName_a").innerText == "修改") {
        document.getElementById("me_userNameInput").disabled = "";
        document.getElementById("me_userName_a").innerText = "完成";
        document.getElementById("me_userName_a").classList.remove("ahide");
    } else {
        document.getElementById("me_userNameInput").disabled = "disabled";
        document.getElementById("me_userName_a").innerText = "修改";
        document.getElementById("me_userName_a").classList.add("ahide");
        var userName = document.getElementById("me_userNameInput").value;
        var userId = document.getElementById("me_userIdInput").value;
        console.log(userName, userId)
        $.ajax({
            url: "/SetUserName",

            data: {
                userId: userId,
                userName:userName
            },
            success: function (data) {
                var isSuccess = data.split(" ")[0];
                console.log(isSuccess)
                if(isSuccess=="true"){
                    layer.msg('修改成功', {icon: 1});
                    $('#table_list').bootstrapTable(('refresh'));// 很重要的一步，刷新url

                }else{
                    layer.msg('修改失败', {icon: 2});
                }
            },  error: function (data) {
                layer.alert('操作失败', {icon: 2});
            }

        })
    }


})

/**
 * 模态框  查看编辑科研人员详情 - 修改密码
 */
$("#me_password_a").on("click", function () {
    if (document.getElementById("me_password_a").innerText == "修改") {
        document.getElementById("me_passwordInput").disabled = "";
        document.getElementById("me_password_a").innerText = "完成";
        document.getElementById("me_password_a").classList.remove("ahide");
    } else {
        document.getElementById("me_passwordInput").disabled = "disabled";
        document.getElementById("me_password_a").innerText = "修改";
        document.getElementById("me_password_a").classList.add("ahide");
        var password = document.getElementById("me_passwordInput").value;
        var userId = document.getElementById("me_userIdInput").value;
        console.log(password, userId)
        $.ajax({
            url: "/SetPassword",

            data: {
                userId: userId,
                password:password
            },
            success: function (data) {
                var isSuccess = data.split(" ")[0];
                console.log(isSuccess)
                if(isSuccess=="true"){
                    layer.msg('修改成功', {icon: 1});
                    $('#table_list').bootstrapTable(('refresh'));// 很重要的一步，刷新url

                }else{
                    layer.msg('修改失败', {icon: 2});
                }
            },  error: function (data) {
                layer.alert('操作失败', {icon: 2});
            }

        })

    }

})

/**
 * 模态框  查看编辑科研人员详情 - 修改手机号
 */
$("#me_phone_a").on("click", function () {
    if (document.getElementById("me_phone_a").innerText == "修改") {
        document.getElementById("me_phoneInput").disabled = "";
        document.getElementById("me_phone_a").innerText = "完成";
        document.getElementById("me_phone_a").classList.remove("ahide");
    } else {
        document.getElementById("me_phoneInput").disabled = "disabled";
        document.getElementById("me_phone_a").innerText = "修改";
        document.getElementById("me_phone_a").classList.add("ahide");
        var phone = document.getElementById("me_phoneInput").value;
        var userId = document.getElementById("me_userIdInput").value;
        console.log(phone, userId)
        $.ajax({
            url: "/SetPhone",

            data: {
                userId: userId,
                phone:phone
            },
            success: function (data) {
                var isSuccess = data.split(" ")[0];
                console.log(isSuccess)
                if(isSuccess=="true"){
                    layer.msg('修改成功', {icon: 1});
                    $('#table_list').bootstrapTable(('refresh'));// 很重要的一步，刷新url

                }else{
                    layer.msg('修改失败', {icon: 2});
                }
            },  error: function (data) {
                layer.alert('操作失败', {icon: 2});
            }

        })

    }

})

/**
 *模态框  查看编辑科研人员详情 - 修改邮箱
 */
$("#me_email_a").on("click", function () {
    if (document.getElementById("me_email_a").innerText == "修改") {
        document.getElementById("me_emailInput").disabled = "";
        document.getElementById("me_email_a").innerText = "完成";
        document.getElementById("me_email_a").classList.remove("ahide");
    } else {
        document.getElementById("me_emailInput").disabled = "disabled";
        document.getElementById("me_email_a").innerText = "修改";
        document.getElementById("me_email_a").classList.add("ahide");
        var email = document.getElementById("me_emailInput").value;
        var userId = document.getElementById("me_userIdInput").value;
        console.log(email, userId)
        $.ajax({
            url: "/SetEmail",

            data: {
                userId: userId,
                email:email
            },
            success: function (data) {
                var isSuccess = data.split(" ")[0];
                console.log(isSuccess)
                if(isSuccess=="true"){
                    layer.msg('修改成功', {icon: 1});
                    $('#table_list').bootstrapTable(('refresh'));// 很重要的一步，刷新url

                }else{
                    layer.msg('修改失败', {icon: 2});
                }
            },  error: function (data) {
                layer.alert('操作失败', {icon: 2});
            }

        })
    }

})

/**
 *模态框  查看编辑科研人员详情 - 修改职称
 */
$("#me_title_a").on("click", function () {
    if (document.getElementById("me_title_a").innerText == "修改") {
        document.getElementById("me_titleSelect").classList.remove("hide");
        document.getElementById("me_titleInput").classList.add("hide");
        document.getElementById("me_title_a").innerText = "完成";
        document.getElementById("me_title_a").classList.remove("ahide");
    } else {
        document.getElementById("me_titleSelect").classList.add("hide");
        document.getElementById("me_titleInput").classList.remove("hide");
        document.getElementById("me_title_a").innerText = "修改";
        document.getElementById("me_title_a").classList.add("ahide");
        var title = $("#me_titleSelect option:selected").val();
        var titleName = $("#me_titleSelect option:selected").text();
        document.getElementById("me_titleInput").value = titleName;
        var userId = document.getElementById("me_userIdInput").value;
        console.log(title,titleName, userId)

        $.ajax({
            url: "/SetTitle",

            data: {
                userId: userId,
                title:title
            },
            success: function (data) {
               var isSuccess = data.split(" ")[0];
                console.log(isSuccess)
                if(isSuccess=="true"){
                    layer.msg('修改成功', {icon: 1});
                    $('#table_list').bootstrapTable(('refresh'));// 很重要的一步，刷新url

                }else{
                    layer.msg('修改失败', {icon: 2});
                }
            },  error: function (data) {
                layer.alert('操作失败', {icon: 2});
            }

        })

    }

})

/**
 *模态框 查看编辑科研人员详情 - 修改所属学院
 */
$("#me_instituteName_a").on("click", function () {
    if (document.getElementById("me_instituteName_a").innerText == "修改") {
        document.getElementById("institutediv").classList.remove("hide");
        document.getElementById("me_instituteNameInput").classList.add("hide");
        document.getElementById("me_instituteName_a").innerText = "完成";
        document.getElementById("me_instituteName_a").classList.remove("ahide");
    } else {
        document.getElementById("institutediv").classList.add("hide");
        document.getElementById("me_instituteNameInput").classList.remove("hide");
        document.getElementById("me_instituteName_a").innerText = "修改";
        document.getElementById("me_instituteName_a").classList.add("ahide");
        var instituteName = $("#me_instituteNameSelect option:selected").text();
        var instituteId = $("#me_instituteNameSelect option:selected").val();
        document.getElementById("me_instituteNameInput").value = instituteName;
        var userId = document.getElementById("me_userIdInput").value;
        console.log(instituteName, instituteId, userId)
        $.ajax({
            url: "/SetInstituteId",

            data: {
                userId: userId,
                instituteId:instituteId
            },
            success: function (data) {
               var isSuccess = data.split(" ")[0];
                console.log(isSuccess)
                if(isSuccess=="true"){
                    layer.msg('修改成功', {icon: 1});
                    $('#table_list').bootstrapTable(('refresh'));// 很重要的一步，刷新url

                }else{
                    layer.msg('修改失败', {icon: 2});
                }
            },  error: function (data) {
                layer.alert('操作失败', {icon: 2});
            }

        })

    }


})


function title(tt) {
    if(tt=="LECTURER"){
        return  "讲师"
    } else if(tt=="ASSOCIATE_PROFESSOR") {
        return "副教授"
    }else if(tt=="PROFESSOR") {
        return  "教授"
    }else if(tt=="ASSOCIATE_RESEARCHER") {
        return "副研究员"
    }else if(tt=="RESEARCHER") {
        return "研究员"
    }else {
        return "暂无"
    }
}