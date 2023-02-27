function $(id){
    return document.getElementById(id)
}
// document.forms[0].registName
function prckRegist(){

    //用户名应为6~16位数组和字母组成
    let unameReg = /[0-9a-zA-Z]{6,16}/;

    let unameTxt = $("unameTxt");
    let uname = unameTxt.value;
    let unameSpan =  $("unameSpan");
    if (!unameReg.test(uname)){
        unameSpan.style.visibility="visible";
        return false;
    }else {
        unameSpan.style.visibility="hidden";
    }

    //密码的长度至少为8位
    let pwdReg = /.{8,}/;

    let pwdText = $("pwdText");
    let pwd = pwdText.value;
    let pwdSpan =  $("pwdSpan");
    if (!pwdReg.test(pwd)){
        pwdSpan.style.visibility="visible";
        return false;
    }else {
        pwdSpan.style.visibility="hidden";
    }
    //密码两次输入不一致
    if ($("pwdText2").value!==pwd){
        $("pwdSpan2").style.visibility="visible";
        return false;
    }else {
        $("pwdSpan2").style.visibility="hidden";
    }
    //请输入正确的邮箱格式
    let emailReg =  /^[a-zA-Z0-9_\.-]+@([a-zA-Z0-9-]+[\.]{1})+[a-zA-Z]+$/;
    let emailText = $("emailText");
    let email = emailText.value;
    let emailSpan =  $("emailSpan");
    if(!emailReg.test(email)){
        emailSpan.style.visibility="visible";
        return false ;
    }else{
        emailSpan.style.visibility="hidden";
    }
    return true;
}

//如果想要发送异步请求，我们需要一个关键的对象 XMLHttpRequest
let xmlHttpRequest ;
function createXMLHttpRequest(){
    if(window.XMLHttpRequest){
        //符合DOM2标准的浏览器 ，xmlHttpRequest的创建方式
        xmlHttpRequest = new XMLHttpRequest() ;
    }else if(window.ActiveXObject){//IE浏览器
        try{
            xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
        }catch (e) {
            xmlHttpRequest = new ActiveXObject("Msxml2.XMLHTTP")
        }
    }
}
function ckUname(uname){
    createXMLHttpRequest();
    let url = "user.do?operate=ckUname&uname="+uname ;
    xmlHttpRequest.open("GET",url,true);
    //设置回调函数
    xmlHttpRequest.onreadystatechange = ckUnameCB ;
    //发送请求
    xmlHttpRequest.send();
}
function ckUnameCB(){
    if(xmlHttpRequest.readyState===4 && xmlHttpRequest.status===200){
        //xmlHttpRequest.responseText 表示 服务器端响应给我的文本内容
        //alert(xmlHttpRequest.responseText);
        let responseText = xmlHttpRequest.responseText ;
        // {'uname':'1'}
        //alert(responseText);
        if(responseText==="{'uname':'1'}"){
            alert("用户名已经被注册！");
        }else{
            alert("用户名可以注册！");
        }
    }
}
